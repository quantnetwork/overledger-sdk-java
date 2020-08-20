package network.quant.ethereum.experimental.dto;

import org.web3j.crypto.RawTransaction;

import java.math.BigInteger;


public class EthRawTransactionResponse extends RawTransaction {

    public EthRawTransactionResponse(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data) {
        super(nonce, gasPrice, gasLimit, to, value, data);
    }
}
