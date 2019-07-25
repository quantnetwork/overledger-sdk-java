package network.quant.ripple;

import com.ripple.core.coretypes.*;
import com.ripple.core.coretypes.uint.UInt32;
import com.ripple.core.types.known.tx.signed.SignedTransaction;
import com.ripple.crypto.ecdsa.Seed;
import network.quant.api.*;
import network.quant.ripple.model.PaymentWithMemos;
import network.quant.ripple.model.TransactionMemo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import network.quant.util.CommonUtil;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * Ripple implementation of Account
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RippleAccount implements Account {

    private static final BigInteger XRP = BigInteger.valueOf(1000000L);
    private static final BigInteger MINIMUM = BigInteger.valueOf(20000000L);
    private static final BigInteger FEE = BigInteger.valueOf(12000000L);
    private static final int LAST_INDEX = 0x7fffffff;
    private static RippleAccount I;
    Seed seed;
    BigInteger nonce;
    Encryptor encryptor;
    Compressor compressor;
    NETWORK network;

    private RippleAccount(NETWORK network, String secretKey, BigInteger nonce) {
        this.nonce = nonce;
        this.network = network;
        this.seed = Seed.fromBase58(secretKey);
    }

    private RippleAccount(NETWORK network) {
        this.network = network;
        this.nonce = BigInteger.ONE;
        this.seed = Seed.fromPassPhrase(UUID.randomUUID().toString());
    }

    private void setMemo(PaymentWithMemos payment, String message) {
        TransactionMemo transactionMemo = new TransactionMemo();
        transactionMemo.as(TransactionMemo.MemoData, new Blob(message.getBytes()));
        STObject stObject = new STObject();
        stObject.as(PaymentWithMemos.Memo, transactionMemo);
        STArray memos = new STArray();
        memos.add(stObject);
        payment.as(PaymentWithMemos.Memos, memos);
    }

    private void sign(String fromAddress, String toAddress, String message, DltTransactionRequest dltTransaction) {
        PaymentWithMemos payment = new PaymentWithMemos();
        payment.as(AccountID.Account, fromAddress);
        payment.as(AccountID.Destination, toAddress);
        payment.as(Amount.Amount, dltTransaction.getAmount().toString());
        payment.as(UInt32.Sequence, null == dltTransaction.getSequence()?this.nonce.longValue():dltTransaction.getSequence());
        payment.as(Amount.Fee, Optional.ofNullable(dltTransaction.getFee()).orElse(FEE).toString());
        payment.as(UInt32.LastLedgerSequence, LAST_INDEX);
        this.setMemo(payment, message);
        SignedTransaction signedTransaction = payment.sign(this.seed.toString());
        network.quant.api.SignedTransaction signedTxn = new network.quant.api.SignedTransaction();
        signedTxn.setTransactions(Collections.singletonList(signedTransaction.tx_blob));
        dltTransaction.setSignedTransaction(signedTxn);
        this.nonce = this.nonce.add(BigInteger.ONE);
    }

    private void sign(byte data[], String fromAddress, String toAddress, String message, DltTransaction dltTransaction) {
        if (null != this.encryptor) {
            data = this.encryptor.encrypt(data);
            message = DatatypeConverter.printHexBinary(data);
        }
        if (null != this.compressor) {
            data = this.compressor.compress(data);
            message = DatatypeConverter.printHexBinary(data);
        }
        this.sign(fromAddress, toAddress, message, (DltTransactionRequest)dltTransaction);
    }

    public NETWORK getNetwork() {
        return this.network;
    }

    public String getPublicKey() {
        if (null != this.seed) {
            AccountID accountID = AccountID.fromKeyPair(this.seed.keyPair());
            return accountID.address;
        } else {
            return null;
        }
    }

    public String getPrivateKeyAsString() {
        if (null != this.seed) {
            return this.seed.toString();
        } else {
            return null;
        }
    }

    @Override
    public Account withNetwork(NETWORK network) {
        return this;
    }

    @Override
    public void setPrivateKey(BigInteger key) {}

    @Override
    public BigInteger getPrivateKey() {
        return new BigInteger(1, this.seed.toString().getBytes());
    }

    @Override
    public void sign(String fromAddress, String toAddress, String message, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            byte data[] = message.getBytes();
            this.sign(data, fromAddress, toAddress, message, dltTransaction);
        }
    }

    @Override
    public void sign(String fromAddress, String toAddress, byte[] data, DltTransaction dltTransaction) {
        if (dltTransaction instanceof DltTransactionRequest) {
            this.sign(data, fromAddress, toAddress, DatatypeConverter.printHexBinary(data), dltTransaction);
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
            this.sign(data, fromAddress, toAddress, message, dltTransaction);
        }
    }

    public static Account getInstance(NETWORK network, String secretKey, BigInteger nonce, Encryptor encryptor, Compressor compressor) {
        I = new RippleAccount(network, secretKey, nonce);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

    public static Account getInstance(NETWORK network, String secretKey, BigInteger nonce) {
        return getInstance(network, secretKey, nonce, null, null);
    }

    public static Account getInstance(NETWORK network, Encryptor encryptor, Compressor compressor) {
        I = new RippleAccount(network);
        I.encryptor = encryptor;
        I.compressor = compressor;
        return I;
    }

    public static Account getInstance(NETWORK network) {
        I = new RippleAccount(network);
        return I;
    }

}
