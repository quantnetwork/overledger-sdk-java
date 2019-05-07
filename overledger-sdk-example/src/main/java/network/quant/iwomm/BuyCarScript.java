package network.quant.iwomm;

import network.quant.OverledgerContext;
import network.quant.api.DLT;
import network.quant.api.DltTransactionResponse;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerTransaction;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.bitcoin.experimental.BitcoinFaucetHelper;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.exception.ClientResponseException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class BuyCarScript {

    static Util util = new Util();

    private static final BigDecimal BTC = new BigDecimal("100000000");

    public static void main(String args[])  {

        util.setupLogger();
        util.checkOverledgerSDK();
        util.loadDemoPropertiesFromContext(Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties"));


        //Script 3
        //3-oneOone-buyer.js

        //Cars available

        Car car1 = new Car("camaro-ss", new BigDecimal(0.20));

        util.logger.info("Buyer");
        BitcoinAccount buyerbBitcoinAccount = (BitcoinAccount) BitcoinAccount.getInstance(NETWORK.TEST);

        BitcoinFaucetHelper.getInstance(OverledgerContext.FAUCET_XBT).fundAccount(buyerbBitcoinAccount);


        util.overledgerSDK.addAccount(DLT.bitcoin.name(), buyerbBitcoinAccount);


        // Replace Bitcoin Shop Address
        String shopAddress = DemoOverledgerContext.SHOP_BTC_ACCOUNT_ADDRESS;

        OverledgerTransactionRequest writeOverledgerTransactionRequest = new OverledgerTransactionRequest();
        writeOverledgerTransactionRequest.setMappId(OverledgerContext.MAPP_ID);
        System.out.println(OverledgerContext.MAPP_ID);
        writeOverledgerTransactionRequest.setDltData(new ArrayList<>());

        DltTransactionRequest writeDltTransactionRequestBitcoin = new DltTransactionRequest();
        writeDltTransactionRequestBitcoin.setDlt(DLT.bitcoin.name());
        writeDltTransactionRequestBitcoin.setFromAddress(((BitcoinAccount)buyerbBitcoinAccount).getKey().toAddress(((BitcoinAccount)buyerbBitcoinAccount).getNetworkParameters()).toBase58());
        writeDltTransactionRequestBitcoin.setToAddress(shopAddress);
        writeDltTransactionRequestBitcoin.setAmount(BTC.multiply(car1.getAmount()).toBigInteger());
        writeDltTransactionRequestBitcoin.setMessage("Buying camaro-ss");
        writeOverledgerTransactionRequest.getDltData().add(writeDltTransactionRequestBitcoin);

        util.logger.info("-------------------- Bitcoin Transaction ---------------------");
        util.logger.info(writeDltTransactionRequestBitcoin.toString());

        try {

            OverledgerTransaction tx = util.overledgerSDK.writeTransaction(writeOverledgerTransactionRequest);

            System.out.println("*************");
            System.out.println(util.overledgerSDK.readTransactions(OverledgerContext.MAPP_ID));


            tx.getDltData().forEach(item -> {
                util.logger.info(" ---> Bitcoin Transaction hash : " + ((DltTransactionResponse) item).getTransactionHash());
                util.logger.info(" ---> Bitcoin Transaction current status, it takes approx 10 minutes to confirm : " + ((DltTransactionResponse) item).getStatus().getStatus().toString());
            });


            util.logger.info(" ---> Overledger Transaction hash : " + tx.getOverledgerTransactionId());

        }catch (Exception e){
            ClientResponseException ee = (ClientResponseException) e;
            System.out.println(((ClientResponseException) e).getResponseBody() );
        }

    }

}
