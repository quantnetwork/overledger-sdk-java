package network.quant.ethereum;

import network.quant.api.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import network.quant.util.CommonUtil;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Optional;

/**
 * Ethereum implementation of Account
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class EthereumAccount implements Account {

    private static final BigInteger DEFAULT_MAIN_GAS_LIMIT = new BigInteger("8000000");
    private static final BigInteger DEFAULT_TEST_GAS_LIMIT = new BigInteger("4712388");
    private static EthereumAccount I;
    NETWORK network;
    ECKeyPair ecKeyPair;
    BigInteger nonce;
    BigInteger gasLimit;
    Encryptor encryptor;
    Compressor compressor;

    private EthereumAccount(NETWORK network) throws Exception {
        this(network, Keys.createEcKeyPair(), BigInteger.ZERO);
    }

    private EthereumAccount(NETWORK network, BigInteger privateKey, BigInteger nonce) {
        this(network, ECKeyPair.create(privateKey), nonce);
    }

    private EthereumAccount(NETWORK network, byte[] privateKey, BigInteger nonce) {
        this(network, ECKeyPair.create(privateKey), nonce);
    }

    private EthereumAccount(NETWORK network, ECKeyPair privateKey, BigInteger nonce) {
        this.network = network;
        this.ecKeyPair = privateKey;
        this.nonce = nonce;
        this.gasLimit = NETWORK.MAIN.equals(this.network) ? DEFAULT_MAIN_GAS_LIMIT : DEFAULT_TEST_GAS_LIMIT;
    }

    private void sign(String toAddress, String message, DltTransactionRequest dltTransaction) {
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                this.nonce = null != dltTransaction.getSequence() ? BigInteger.valueOf(dltTransaction.getSequence()) : this.nonce,
                Optional.ofNullable(dltTransaction.getFee()).orElse(EthGasStation.getInstance().calculate(FEE_POLICY.NORMAL)),
                Optional.ofNullable(dltTransaction.getFeeLimit()).orElse(this.gasLimit),
                toAddress,
                dltTransaction.getAmount(),
                message
        );
        byte transactionSignedBytes[] = TransactionEncoder.signMessage(rawTransaction, Credentials.create(this.ecKeyPair));
        dltTransaction.setSignedTransaction(Numeric.toHexString(transactionSignedBytes));
        this.nonce = this.nonce.add(BigInteger.ONE);
    }

    private void deployContract(String contractBinary, String message, DltTransactionRequest dltTransaction) {
        RawTransaction rawTransaction = RawTransaction.createContractTransaction(
                this.nonce = null != dltTransaction.getSequence() ? BigInteger.valueOf(dltTransaction.getSequence()) : this.nonce,
                Optional.ofNullable(dltTransaction.getFee()).orElse(EthGasStation.getInstance().calculate(FEE_POLICY.NORMAL)),
                Optional.ofNullable(dltTransaction.getFeeLimit()).orElse(this.gasLimit),
                dltTransaction.getAmount(),
                String.format("0x %s%s", contractBinary, null == message?"":message)
        );
        byte transactionSignedBytes[] = TransactionEncoder.signMessage(rawTransaction, Credentials.create(this.ecKeyPair));
        dltTransaction.setSignedTransaction(Numeric.toHexString(transactionSignedBytes));
        this.nonce = this.nonce.add(BigInteger.ONE);
    }

    private void sign(byte data[], String toAddress, String message, DltTransaction dltTransaction) {
        if (null != this.encryptor) {
            data = this.encryptor.encrypt(data);
            message = DatatypeConverter.printHexBinary(data);
        }
        if (null != this.compressor) {
            data = this.compressor.compress(data);
            message = DatatypeConverter.printHexBinary(data);
        }
        this.sign(toAddress, message, (DltTransactionRequest)dltTransaction);
    }

    public NETWORK getNetwork() {
        return this.network;
    }

    @Override
    public Account withNetwork(NETWORK network) {
        this.network = network;
        return this;
    }

    @Override
    public void setPrivateKey(BigInteger key) {
        this.ecKeyPair = ECKeyPair.create(key);
    }

    @Override
    public BigInteger getPrivateKey() {
        return this.ecKeyPair.getPrivateKey();
    }

    @Override
    public void sign(String fromAddress, String toAddress, String message, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            byte data[] = message.getBytes();
            this.sign(data, toAddress, message, dltTransaction);
        }
    }

    @Override
    public void sign(String fromAddress, String toAddress, byte[] data, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            String message = DatatypeConverter.printHexBinary(data);
            this.sign(data, toAddress, message, dltTransaction);
        }
    }

    @Override
    public void sign(String fromAddress, String toAddress, InputStream stream, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            byte data[];
            try {
                data = CommonUtil.getStream(stream);
            } catch (IOException e) {
                log.error("Unalbe to read data from given stream", e);
                return;
            }
            String message = DatatypeConverter.printHexBinary(data);
            this.sign(data, toAddress, message, dltTransaction);
        }
    }

    /**
     * Invoke contract transaction
     * @param contractAddress String containing contract address
     * @param function Function containing the Web3J function
     * @param dltTransaction DltTransaction containing the overledger DLT transaction
     */
    public void invokeContract(String contractAddress, Function function, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            String encodedFunction = FunctionEncoder.encode(function);
            this.sign(contractAddress, encodedFunction, (DltTransactionRequest)dltTransaction);
        }
    }

    /**
     * Deploy contract
     * @param contractBinary String containing contract binary
     * @param encodedConstructor String containing contract constructor
     * @param dltTransaction DltTransaction containing the overledger DLT transaction
     */
    public void deployContract(String contractBinary, String encodedConstructor, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            this.deployContract(contractBinary, encodedConstructor, (DltTransactionRequest)dltTransaction);
        }
    }

    public void queryContract(String contractAddress, Function function) {

    }

    /**
     * Create Ethereum wallet instance with new key
     * @param network NETWORK containing network param
     * @param encryptor Encryptor containing encryptor
     * @param compressor Compressor containing compressor
     * @return Account containing a Ethereum wallet
     */
    public static Account getInstance(NETWORK network, Encryptor encryptor, Compressor compressor) throws Exception {
        I = new EthereumAccount(network);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

    public static Account getInstance(NETWORK network) throws Exception {
        I = new EthereumAccount(network);
        return I;
    }

    /**
     * Get Ethereum wallet instance by given secret key number
     * @param network NETWORK containing network param
     * @param privateKey BigInteger containing the key
     * @param encryptor Encryptor containing encryptor
     * @param compressor Compressor containing compressor
     * @return Account containing a Ethereum wallet
     */
    public static Account getInstance(NETWORK network, BigInteger privateKey, BigInteger nonce, Encryptor encryptor, Compressor compressor) {
        I = new EthereumAccount(network, privateKey, nonce);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

    public static Account getInstance(NETWORK network, BigInteger privateKey, BigInteger nonce) {
        return getInstance(network, privateKey, nonce, null, null);
    }

    /**
     * Get Ethereum wallet instance by given secret key array
     * @param network NETWORK containing network param
     * @param privateKey byte array containing the key in array
     * @param encryptor Encryptor containing encryptor
     * @param compressor Compressor containing compressor
     * @return Account containing a Ethereum wallet
     */
    public static Account getInstance(NETWORK network, byte privateKey[], BigInteger nonce, Encryptor encryptor, Compressor compressor) {
        I = new EthereumAccount(network, privateKey, nonce);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

    public static Account getInstance(NETWORK network, byte privateKey[], BigInteger nonce) {
        return getInstance(network, privateKey, nonce, null, null);
    }

    /**
     * Get Ethereum wallet instance by given secret key object
     * @param network NETWORK containing network param
     * @param privateKey ECKey containing the key
     * @param encryptor Encryptor containing encryptor
     * @param compressor Compressor containing compressor
     * @return Account containing a Ethereum wallet
     */
    public static Account getInstance(NETWORK network, ECKeyPair privateKey, BigInteger nonce, Encryptor encryptor, Compressor compressor) {
        I = new EthereumAccount(network, privateKey, nonce);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

    public static Account getInstance(NETWORK network, ECKeyPair privateKey, BigInteger nonce) {
        return getInstance(network, privateKey, nonce, null, null);
    }

}
