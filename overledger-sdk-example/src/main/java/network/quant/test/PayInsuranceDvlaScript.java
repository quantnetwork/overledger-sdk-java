package network.quant.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.quant.OverledgerContext;
import network.quant.api.DLT;
import network.quant.api.DltTransactionResponse;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerTransaction;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.ethereum.EthereumAccount;
import network.quant.exception.ClientResponseException;
import network.quant.ripple.RippleAccount;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class PayInsuranceDvlaScript {

    static Util util = new Util();

    private static final BigDecimal ETH = new BigDecimal("1000000000000000000");
    private static final BigDecimal XRP = new BigDecimal("1000000");

    public static void main(String args[]) {

        util.setupLogger();
        util.checkOverledgerSDK();
        // util.loadContext(Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties"));

        EthereumAccount insuranceEthereumAccount = (EthereumAccount) EthereumAccount.getInstance(NETWORK.TEST, new BigInteger("88462467353422894037388749663072770027090763518400085939107740349309162205081"), new BigInteger("0"));
        RippleAccount dvlaRippleAccount = (RippleAccount) RippleAccount.getInstance(NETWORK.TEST, "snT4PvFS5YZ4G1CJA6v85pAcrzFSh", new BigInteger("1"));

        EthereumAccount shopEthereumAccount = (EthereumAccount) EthereumAccount.getInstance(NETWORK.TEST, new BigInteger("105858731101090287566057501672735658358282159285543995503009954031739733762457"), new BigInteger("0"));
        RippleAccount shopRippleAccount = (RippleAccount) RippleAccount.getInstance(NETWORK.TEST, "ssNMLMBAhiXyVwnP4gkKE1AzmMP8K", new BigInteger("1"));
        shopRippleAccount.setNonce(BigInteger.ONE);


        //Script 4
        //4-oneOone-shop-process.js
        util.overledgerSDK.addAccount(DLT.ripple.name(), dvlaRippleAccount);
        util.overledgerSDK.addAccount(DLT.ethereum.name(), shopEthereumAccount);

        OverledgerTransactionRequest othersOverledgerTransactionRequest = new OverledgerTransactionRequest();
        othersOverledgerTransactionRequest.setMappId(OverledgerContext.MAPP_ID);
        othersOverledgerTransactionRequest.setDltData(new ArrayList<>());

        DltTransactionRequest writeDltTransactionRequestEthereum = new DltTransactionRequest();
        writeDltTransactionRequestEthereum.setDlt(DLT.ethereum.name());
        writeDltTransactionRequestEthereum.setFromAddress(Credentials.create(((EthereumAccount)shopEthereumAccount).getEcKeyPair()).getAddress());
        writeDltTransactionRequestEthereum.setToAddress(Credentials.create(((EthereumAccount)insuranceEthereumAccount).getEcKeyPair()).getAddress());
        writeDltTransactionRequestEthereum.setAmount(ETH.multiply(new BigDecimal(1)).toBigInteger());
        writeDltTransactionRequestEthereum.setMessage("Bought car from transaction: ");
        writeDltTransactionRequestEthereum.setSequence(0L);
        othersOverledgerTransactionRequest.getDltData().add(writeDltTransactionRequestEthereum);

        DltTransactionRequest writeDltTransactionRequestRipple = new DltTransactionRequest();
        writeDltTransactionRequestRipple.setDlt(DLT.ripple.name());
        writeDltTransactionRequestRipple.setFromAddress(((RippleAccount)shopRippleAccount).getPublicKey());
        writeDltTransactionRequestRipple.setToAddress(((RippleAccount)dvlaRippleAccount).getPublicKey());
        writeDltTransactionRequestRipple.setAmount(XRP.multiply(new BigDecimal(1)).toBigInteger());
        writeDltTransactionRequestRipple.setMessage("Bought car from transaction: ");
        writeDltTransactionRequestRipple.setSequence(1L);
        othersOverledgerTransactionRequest.getDltData().add(writeDltTransactionRequestRipple);

       try {
           ObjectMapper objectMapper = new ObjectMapper();
           System.out.println(objectMapper.writeValueAsString(othersOverledgerTransactionRequest) );
           OverledgerTransaction otherTx = util.overledgerSDK.writeTransaction(othersOverledgerTransactionRequest);

           util.logger.info("-------------------- Submitted Ethereum and Ripple transactions ---------------------");
           util.logger.info(othersOverledgerTransactionRequest.getDltData().toString());

           util.logger.info("-------------------- Status of Ethereum and Ripple transactions ---------------------");

           otherTx.getDltData().forEach(item -> {
               util.logger.info(" ---> Transaction current message (if anything went wrong) : " + ((DltTransactionResponse) item).getStatus().getMessage());
               util.logger.info(" ---> Transaction current status : " + ((DltTransactionResponse) item).getStatus().getStatus().toString());
           });

           util.logger.info("---> Shop paid insurer and dvla, Transactions : " + otherTx);

       }catch(Exception ex) {

           ClientResponseException ee = (ClientResponseException) ex;
           System.out.println(ee.getResponseBody());           ex.getMessage();

           ex.printStackTrace();
       }
    }

}


