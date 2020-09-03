package network.quant.ethereum;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import network.quant.api.*;
import network.quant.ethereum.exception.*;
import network.quant.ethereum.experimental.ContractArgumentToAbiMainFactory;
import network.quant.ethereum.experimental.dto.*;
import network.quant.util.CommonUtil;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.*;
import org.web3j.crypto.*;
import org.web3j.utils.Numeric;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Ethereum implementation of Account
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class EthereumAccount implements Account {

    private static final BigInteger DEFAULT_MAIN_GAS_LIMIT = BigInteger.valueOf(8000000);
    private static final BigInteger DEFAULT_TEST_GAS_LIMIT = BigInteger.valueOf(4712388);
    private static EthereumAccount I;
    NETWORK network;
    ECKeyPair ecKeyPair;
    BigInteger nonce;
    BigInteger gasLimit;
    Encryptor encryptor;
    Compressor compressor;
    BigInteger chainId;

    private EthereumAccount(NETWORK network) throws Exception {
        this(network, Keys.createEcKeyPair(), BigInteger.ZERO);
    }

    private EthereumAccount(NETWORK network, BigInteger privateKey, BigInteger nonce) {
        this(network, ECKeyPair.create(privateKey), nonce);
    }

    private EthereumAccount(NETWORK network, byte[] privateKey, BigInteger nonce) {
        this(network, new BigInteger(privateKey), nonce);
    }

    private EthereumAccount(NETWORK network, ECKeyPair privateKey, BigInteger nonce) {
        this.network = network;
        this.ecKeyPair = privateKey;
        this.nonce = nonce;
        this.gasLimit = NETWORK.MAIN.equals(this.network) ? DEFAULT_MAIN_GAS_LIMIT : DEFAULT_TEST_GAS_LIMIT;
        if (network.equals(NETWORK.MAIN))
            chainId = BigInteger.valueOf(1l);
        else
            chainId = BigInteger.valueOf(3l);
    }

    private void sign(String toAddress, String message, DltTransactionRequest dltTransaction) {
        if(message!=null && !message.isEmpty()){
            message = Numeric.toHexStringNoPrefix(message.getBytes(StandardCharsets.UTF_8)); // Required encoding to show correclty on explorers
        }
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                this.nonce = null != dltTransaction.getSequence() ? BigInteger.valueOf(dltTransaction.getSequence()) : this.nonce,
                Optional.ofNullable(dltTransaction.getFee()).orElse(EthGasStation.getInstance().calculate(FEE_POLICY.NORMAL)),
                Optional.ofNullable(dltTransaction.getFeeLimit()).orElse(this.gasLimit),
                toAddress,
                dltTransaction.getAmount(),
                message
        );
        byte transactionSignedBytes[] = TransactionEncoder.signMessage(rawTransaction, Credentials.create(this.ecKeyPair));
        SignedTransaction signedTransaction = new SignedTransaction();
        signedTransaction.setTransactions(Collections.singletonList(Numeric.toHexString(transactionSignedBytes)));
        dltTransaction.setSignedTransaction(signedTransaction);
        this.nonce = this.nonce.add(BigInteger.ONE);
    }

    private void deployContract(String contractBinary, String message, DltTransactionRequest dltTransaction) {
        if(message!=null && !message.isEmpty()){
            message = Numeric.toHexStringNoPrefix(message.getBytes(StandardCharsets.UTF_8)); // Required encoding to show correclty on explorers
        }
        RawTransaction rawTransaction = RawTransaction.createContractTransaction(
                this.nonce = null != dltTransaction.getSequence() ? BigInteger.valueOf(dltTransaction.getSequence()) : this.nonce,
                Optional.ofNullable(dltTransaction.getFee()).orElse(EthGasStation.getInstance().calculate(FEE_POLICY.NORMAL)),
                Optional.ofNullable(dltTransaction.getFeeLimit()).orElse(this.gasLimit),
                dltTransaction.getAmount(),
                String.format("0x %s%s", contractBinary, null == message?"":message)
        );
        byte transactionSignedBytes[] = TransactionEncoder.signMessage(rawTransaction, Credentials.create(this.ecKeyPair));
        SignedTransaction signedTransaction = new SignedTransaction();
        signedTransaction.setTransactions(Collections.singletonList(Numeric.toHexString(transactionSignedBytes)));
        dltTransaction.setSignedTransaction(signedTransaction);
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
            byte data[] = message.getBytes(StandardCharsets.UTF_8);
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
                log.error("Unable to read data from given stream", e);
                return;
            }
            String message = DatatypeConverter.printHexBinary(data);
            this.sign(data, toAddress, message, dltTransaction);
        }
    }

    public EthRawTransactionResponse buildTransaction(DltTransaction dltTransaction) {
        String transactionData = null;
        TransactionEthereumRequest ethereumRequest = (TransactionEthereumRequest) dltTransaction;
        SCFunctionType invocationType = ethereumRequest.getFunctionType();
        try {
            if (ethereumRequest.getToAddress() != null && !ethereumRequest.getToAddress().isEmpty()){
                if (invocationType.equals(SCFunctionType.FUNCTION_CALL_WITH_PARAMETERS) ||
                        invocationType.equals(SCFunctionType.FUNCTION_CALL_WITH_NO_PARAMETERS)){
                    List<ContractArgument> inputParamsList = ethereumRequest.getInputValues();
                    if (inputParamsList != null && !inputParamsList.isEmpty()){
                        String functionName = ethereumRequest.getFuncName();
                        List<String> solidityInputTypes = EthereumUtil.getSolidityInputOutputTypes(inputParamsList);
                        List<Object> arguments = EthereumUtil.getValues(inputParamsList);
                        List<String> solidityOutputTypes = (ethereumRequest.getOutputTypes() == null) ? Collections.emptyList() : EthereumUtil.getSolidityInputOutputTypes(ethereumRequest.getOutputTypes());

                        Function function = FunctionEncoder.makeFunction(
                                functionName,
                                solidityInputTypes,
                                arguments,
                                solidityOutputTypes
                        );
                        if (functionName!= null && !functionName.trim().isEmpty()){
                            transactionData = FunctionEncoder.encode(function);
                            log.info("transactionData: " + transactionData);
                        }else {
                            throw new FunctionNameEmptyException("SmartContract function name must be given.");
                        }
                    }else {
                        throw new SmartContractInputParamsException("input parameters must be defined.");
                    }
                }else {
                    throw new SmartContractFunctionTypeException("Invalid functionType. " +
                            "must be functionCallWithNoParameters or functionCallWithParameters");
                }
            }else {
                throw new SmartContractAddressException("the toAddress must be set to a non empty string, " +
                        "equal to the ethereum address of the smart contract");
            }
        }catch (Exception e){
            log.error("exception occurred: " + e.getMessage());
        }
        return new EthRawTransactionResponse(BigInteger.valueOf(ethereumRequest.getSequence()),
                ethereumRequest.getFee(),
                ethereumRequest.getFeeLimit(),
                ethereumRequest.getToAddress(),
                ethereumRequest.getAmount(),
                transactionData);
    }


    public EthRawTransactionResponse buildContract(DltTransaction dltTransaction) {
        String transactionData = null;
        TransactionEthereumRequest ethereumRequest = (TransactionEthereumRequest) dltTransaction;
        SCFunctionType invocationType = ethereumRequest.getFunctionType();
        try {
            if (ethereumRequest.getToAddress() == null || ethereumRequest.getToAddress().isEmpty()){
                if (invocationType.equals(SCFunctionType.CONSTRUCTOR_WITH_PARAMETERS) ||
                        invocationType.equals(SCFunctionType.CONSTRUCTOR_WITH_NO_PARAMETERS)){
                    List<ContractArgument> inputParamsList = ethereumRequest.getInputValues();
                    if (inputParamsList != null && !inputParamsList.isEmpty()){
                        String functionName = ethereumRequest.getFuncName();

                        if (functionName!= null && !functionName.trim().isEmpty()){
                            throw new FunctionNameEmptyException("SmartContract function name must be empty.");
                        }

/*
                        List<Bool> boolList = new ArrayList<>();
                        boolList.add(new Bool(true));
                        boolList.add(new Bool(false));
                        boolList.add(new Bool(true));
                        DynamicArray<Bool> boolArray = new DynamicArray<>(Bool.class, boolList);


                        String encodedConstructor1 = FunctionEncoder.encodeConstructor(Arrays.asList(new Bool(true),
                                new Int256(5),
                                new Uint16(33),
                                new Utf8String("Hello"),
                                new Address("0x650A87cfB9165C9F4Ccc7B971D971f50f753e761"),
                                new Utf8String("Hi_there!"),
                                boolArray));
*/

                        List<Type> encodeInputList = new ArrayList<>();
                        for(ContractArgument contractArgument : inputParamsList) {
                            encodeInputList.add(ContractArgumentToAbiMainFactory.convertContractArgument(contractArgument));
                        }
                        String encodedConstructor = FunctionEncoder.encodeConstructor(encodeInputList);
                        transactionData = encodedConstructor;
                        log.info("encodedConstructor before appending with code = " + transactionData);



                    }else {
                        throw new SmartContractInputParamsException("Input parameters must be defined.");
                    }
                }else {
                    throw new SmartContractFunctionTypeException("Invalid functionType, must be CONSTRUCTOR_WITH_PARAMETERS or CONSTRUCTOR_WITH_NO_PARAMETERS");
                }
            }else {
                throw new SmartContractAddressException("The toAddress must be set to an empty string");
            }
        }catch (Exception e){
            log.error("exception occurred: " + e.getMessage());
        }
        log.info("code + data = " + ethereumRequest.getCode() + transactionData);


        return new EthRawTransactionResponse(BigInteger.valueOf(ethereumRequest.getSequence()),
                ethereumRequest.getFee(),
                ethereumRequest.getFeeLimit(),
                "",
                ethereumRequest.getAmount(),
                ethereumRequest.getCode() + transactionData);
    }


    @Override
    public DltTransaction buildSmartContractQuery(DltTransaction dltTransaction) {
        TransactionEthereumRequest request = (TransactionEthereumRequest) dltTransaction;
        List<PreparedContractArgument> inputs = EthereumUtil.computeSCQueryInputValuesList(request.getInputValues());
        List<PreparedContractArgument> outputs = EthereumUtil.computeSCQueryOutputTypesList(request.getOutputTypes());

        return ContractQueryRequestDto.builder()
                .fromAddress(request.getFromAddress())
                .contractAddress(request.getToAddress())
                .funcName(request.getFuncName())
                .inputValues(inputs)
                .outputTypes(outputs)
                .build();
    }

    @Override
    public void addUtxo(String transactionHash, long outpoint, long valueInSatoshi, int blockHeight, String address) {
        throw new UnsupportedOperationException();
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

    @Override
    public void invokeContract(DltTransaction dltTransaction) {
        this.sign(dltTransaction);
    }

    @Override
    public void createSmartContract(DltTransaction dltTransaction) {
        this.signContract(dltTransaction);
    }

    public void sign(DltTransaction dltTransaction){
        DltTransactionRequest request = (DltTransactionRequest) dltTransaction;
        EthRawTransactionResponse buildTransactionResponse = this.buildTransaction(dltTransaction);

        byte transactionSignedBytes[] = TransactionEncoder.signMessage(buildTransactionResponse
                , this.getChainId().longValue()
                , Credentials.create(this.ecKeyPair)
        );
        log.info("transactionSigned: " + Numeric.toHexString(transactionSignedBytes));
        SignedTransaction signedTransaction = new SignedTransaction();
        signedTransaction.setTransactions(Collections.singletonList(Numeric.toHexString(transactionSignedBytes)));
        request.setSignedTransaction(signedTransaction);
    }

    public void signContract(DltTransaction dltTransaction){
        DltTransactionRequest request = (DltTransactionRequest) dltTransaction;
        EthRawTransactionResponse buildTransactionResponse = this.buildContract(dltTransaction);

        log.info("before signing = " + buildTransactionResponse);

        byte transactionSignedBytes[] = TransactionEncoder.signMessage(buildTransactionResponse
                , this.getChainId().longValue()
                , Credentials.create(this.ecKeyPair)
        );
        log.info("transactionSigned: " + Numeric.toHexString(transactionSignedBytes));
        SignedTransaction signedTransaction = new SignedTransaction();
        signedTransaction.setTransactions(Collections.singletonList(Numeric.toHexString(transactionSignedBytes)));
        request.setSignedTransaction(signedTransaction);
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
