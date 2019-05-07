package network.quant.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.quant.OverledgerContext;
import network.quant.api.DLT;
import network.quant.api.DltTransactionResponse;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerTransaction;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.bitcoin.experimental.BitcoinFaucetHelper;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.ethereum.EthereumAccount;
import network.quant.ethereum.experimental.EthereumFaucetHelper;
import network.quant.exception.ClientResponseException;
import network.quant.iwomm.DemoOverledgerContext;
import network.quant.iwomm.Util;
import network.quant.ripple.RippleAccount;
import network.quant.ripple.experimental.RippleFaucetHelper;
import network.quant.util.BalanceRequest;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UsingJavaSdk {

    static Util util = new Util();
    static BitcoinAccount senderBitcoinAccount;
    static EthereumAccount senderEthereumAccount;
    static RippleAccount senderRippleAccount;

    static BitcoinAccount receiverBitcoinAccount;
    static EthereumAccount receiverEthereumAccount;
    static RippleAccount receiverRippleAccount;

    private static final BigDecimal ETH = new BigDecimal("1");
    private static final BigDecimal XRP = new BigDecimal("100");

    public static void main(String args[]) {

        util.setupLogger();
        util.checkOverledgerSDK();
        util.loadDemoPropertiesFromContext(Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties"));

        try {

            System.out.println("-------- Create new Bitcoin, Ethereum & Ripple accounts for senders and receivers");
            createAccounts();

            System.out.println("-------- Fund newly create Bitcoin, Ethereum & Ripple accounts");
            fundAccounts();


            System.out.println("**** Waiting for 1 minutes. Making sure funding transactions are confirmed on chain. ****");
            TimeUnit.MINUTES.sleep(1);

            System.out.println("-------- Check balance of account if they have sufficient fund");
            checkBalanceOfAccounts();


            System.out.println("-------- Make some payments now");
            System.out.println("-- Bitcoin sender account payment to Bitcoin receiver");
            makeBitcoinPayment();
            System.out.println("-- Ethereum sender account payment to Ethereum receiver");
            makeEthereumPayment();
            System.out.println("-- Ripple sender account payment to Ripple receiver");
            makeRipplePayment();

          /*  System.out.println(util.overledgerSDK.readTransactions("network.quant.softwarelicensechecke").getTransactions().size());


            //Read Transactions using mappId
            util.overledgerSDK.readTransactions("network.quant.softwarelicensechecke").getTransactions().forEach(transaction -> {
                System.out.println(transaction.getMappId());
                transaction.getDltData().forEach(dltData -> {
                    System.out.println(dltData.getDlt());
                });
            });
*/




        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createAccounts() throws Exception {

        senderBitcoinAccount = (BitcoinAccount) BitcoinAccount.getInstance(NETWORK.TEST);
        senderEthereumAccount = (EthereumAccount) EthereumAccount.getInstance(NETWORK.TEST);
        senderRippleAccount = (RippleAccount) RippleAccount.getInstance(NETWORK.TEST);

        receiverBitcoinAccount = (BitcoinAccount) BitcoinAccount.getInstance(NETWORK.TEST);
        receiverEthereumAccount = (EthereumAccount) EthereumAccount.getInstance(NETWORK.TEST);
        receiverRippleAccount = (RippleAccount) RippleAccount.getInstance(NETWORK.TEST);


        util.overledgerSDK.addAccount(DLT.bitcoin.name(), senderBitcoinAccount);
        util.overledgerSDK.addAccount(DLT.ethereum.name(), senderEthereumAccount);
        util.overledgerSDK.addAccount(DLT.ripple.name(), senderRippleAccount);

        util.overledgerSDK.addAccount(DLT.bitcoin.name(), receiverBitcoinAccount);
        util.overledgerSDK.addAccount(DLT.ethereum.name(), receiverEthereumAccount);
        util.overledgerSDK.addAccount(DLT.ripple.name(), receiverRippleAccount);


        System.out.println("---- Sender accounts:");
        System.out.println("Sender's Bitcoin Account: " + senderBitcoinAccount.getKey().toAddress(senderBitcoinAccount.getNetworkParameters()).toBase58());
        System.out.println("Sender's ETH Account: " + Credentials.create(senderEthereumAccount.getEcKeyPair()).getAddress());
        System.out.println("Sender's Ethereum: Private Key " + senderEthereumAccount.getPrivateKey());
        System.out.println("Sender's Ripple Account: " + senderRippleAccount.getPublicKey());
        System.out.println("Sender's Ripple Account: Private Key " + senderRippleAccount.getPrivateKeyAsString());

        System.out.println("---- Receiver accounts:");
        System.out.println("Receiver's Bitcoin Account: " + receiverBitcoinAccount.getKey().toAddress(receiverBitcoinAccount.getNetworkParameters()).toBase58());
        System.out.println("Receiver's ETH Account: " + Credentials.create(receiverEthereumAccount.getEcKeyPair()).getAddress());
        System.out.println("Receiver's Ethereum: Private Key " + receiverEthereumAccount.getPrivateKey());
        System.out.println("Receiver's Ripple Account: " + receiverRippleAccount.getPublicKey());
        System.out.println("Receiver's Ripple Account: Private Key " + receiverRippleAccount.getPrivateKeyAsString());
    }

    private static void fundAccounts() {

        //Fund the accounts
        BitcoinFaucetHelper.getInstance(OverledgerContext.FAUCET_XBT).fundAccount(senderBitcoinAccount);
        System.out.println("Bitcoin account funded, please allow at least 3 minutes before you check the balance.");
        EthereumFaucetHelper.getInstance(OverledgerContext.FAUCET_ETH).fundAccount(senderEthereumAccount);
        System.out.println("Ethereum account funded, please allow at least 3 minutes before you check the balance.");
        RippleFaucetHelper.getInstance(OverledgerContext.FAUCET_XRP).fundAccount(senderRippleAccount, BigDecimal.valueOf(20L));
        System.out.println("Ripple account funded, please allow at least 3 minutes before you check the balance.");

    }

    private static void checkBalanceOfAccounts() {
        List<BalanceRequest> balanceRequest = new ArrayList<BalanceRequest>();

        BalanceRequest bitcoinSenderBalance = new BalanceRequest();
        BalanceRequest ethereumSenderBalance = new BalanceRequest();
        BalanceRequest rippleSenderBalance = new BalanceRequest();

        bitcoinSenderBalance.setAddress(senderBitcoinAccount.getKey().toAddress(senderBitcoinAccount.getNetworkParameters()).toBase58());
        bitcoinSenderBalance.setDlt(DLT.bitcoin.name());

        ethereumSenderBalance.setAddress(Credentials.create(senderEthereumAccount.getEcKeyPair()).getAddress());
        ethereumSenderBalance.setDlt(DLT.ethereum.name());

        rippleSenderBalance.setAddress(senderRippleAccount.getPublicKey());
        rippleSenderBalance.setDlt(DLT.ripple.name());

        balanceRequest.add(bitcoinSenderBalance);

        balanceRequest.add(ethereumSenderBalance);

        balanceRequest.add(rippleSenderBalance);

        util.overledgerSDK.searchBalance(balanceRequest).forEach(response -> {
            System.out.println(response.getDlt() + " | " +  response.getValue());
        });
    }

    private static void makeBitcoinPayment() {

        util.overledgerSDK.addAccount(DLT.bitcoin.name(), senderBitcoinAccount);

        OverledgerTransactionRequest writeOverledgerTransactionRequest = new OverledgerTransactionRequest();
        writeOverledgerTransactionRequest.setMappId(OverledgerContext.MAPP_ID);
        writeOverledgerTransactionRequest.setDltData(new ArrayList<>());

        DltTransactionRequest writeDltTransactionRequestBitcoin = new DltTransactionRequest();
        writeDltTransactionRequestBitcoin.setDlt(DLT.bitcoin.name());
        writeDltTransactionRequestBitcoin.setFromAddress(senderBitcoinAccount.getKey().toAddress(senderBitcoinAccount.getNetworkParameters()).toBase58());
        writeDltTransactionRequestBitcoin.setToAddress(receiverBitcoinAccount.getKey().toAddress(receiverBitcoinAccount.getNetworkParameters()).toBase58());
        writeDltTransactionRequestBitcoin.setAmount(new BigDecimal(0.20).toBigInteger());
        writeDltTransactionRequestBitcoin.setMessage("Sender to receiver bitcoin payment.");
        writeOverledgerTransactionRequest.getDltData().add(writeDltTransactionRequestBitcoin);

        System.out.println("Bitcoin Transaction");

        try {

            OverledgerTransaction tx = util.overledgerSDK.writeTransaction(writeOverledgerTransactionRequest);

            tx.getDltData().forEach(item -> {
                System.out.println("Bitcoin Transaction hash : " + ((DltTransactionResponse) item).getTransactionHash());
                System.out.println("Bitcoin Transaction current status, it takes approx 10 minutes to confirm : " + ((DltTransactionResponse) item).getStatus().getStatus().toString());
            });


            System.out.println("Overledger Transaction hash : " + tx.getOverledgerTransactionId());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void makeEthereumPayment() {
        util.overledgerSDK.addAccount(DLT.ethereum.name(), senderEthereumAccount);

        OverledgerTransactionRequest othersOverledgerTransactionRequest = new OverledgerTransactionRequest();
        othersOverledgerTransactionRequest.setMappId(OverledgerContext.MAPP_ID);
        othersOverledgerTransactionRequest.setDltData(new ArrayList<>());

        DltTransactionRequest writeDltTransactionRequestEthereum = new DltTransactionRequest();
        writeDltTransactionRequestEthereum.setDlt(DLT.ethereum.name());
        writeDltTransactionRequestEthereum.setFromAddress(Credentials.create(senderEthereumAccount.getEcKeyPair()).getAddress());
        writeDltTransactionRequestEthereum.setToAddress(Credentials.create(receiverEthereumAccount.getEcKeyPair()).getAddress());
        writeDltTransactionRequestEthereum.setAmount(ETH.multiply(new BigDecimal(1)).toBigInteger());
        writeDltTransactionRequestEthereum.setMessage("Ethereum sender to receiver.");
        writeDltTransactionRequestEthereum.setFee(new BigDecimal(21000).toBigInteger());
        writeDltTransactionRequestEthereum.setFeeLimit(new BigDecimal(2100000).toBigInteger());
        writeDltTransactionRequestEthereum.setSequence(0L);
        othersOverledgerTransactionRequest.getDltData().add(writeDltTransactionRequestEthereum);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            OverledgerTransaction otherTx = util.overledgerSDK.writeTransaction(othersOverledgerTransactionRequest);

            System.out.println("Submitted Ethereum transaction");

            otherTx.getDltData().forEach(item -> {
                System.out.println("Transaction current message (if anything went wrong) : " + ((DltTransactionResponse) item).getStatus().getMessage());
                System.out.println("Transaction current status : " + ((DltTransactionResponse) item).getStatus().getStatus().toString());
            });

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void makeRipplePayment() {
        util.overledgerSDK.addAccount(DLT.ripple.name(), senderRippleAccount);

        OverledgerTransactionRequest othersOverledgerTransactionRequest = new OverledgerTransactionRequest();
        othersOverledgerTransactionRequest.setMappId(OverledgerContext.MAPP_ID);
        othersOverledgerTransactionRequest.setDltData(new ArrayList<>());

        DltTransactionRequest writeDltTransactionRequestRipple = new DltTransactionRequest();
        writeDltTransactionRequestRipple.setDlt(DLT.ripple.name());
        writeDltTransactionRequestRipple.setFromAddress(((RippleAccount)senderRippleAccount).getPublicKey());
        writeDltTransactionRequestRipple.setToAddress(((RippleAccount)receiverRippleAccount).getPublicKey());
        writeDltTransactionRequestRipple.setAmount(XRP.multiply(new BigDecimal(1)).toBigInteger());
        writeDltTransactionRequestRipple.setMessage("Ripple sender to receiver transaction.");
        writeDltTransactionRequestRipple.setSequence(1L);
        writeDltTransactionRequestRipple.setFee(new BigDecimal(50).toBigInteger());
        othersOverledgerTransactionRequest.getDltData().add(writeDltTransactionRequestRipple);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(othersOverledgerTransactionRequest) );
            OverledgerTransaction otherTx = util.overledgerSDK.writeTransaction(othersOverledgerTransactionRequest);

            System.out.println("Ripple transactions");

            otherTx.getDltData().forEach(item -> {
                System.out.println("Ripple transaction current message (if anything went wrong) : " + ((DltTransactionResponse) item).getStatus().getMessage());
                System.out.println("Ripple transaction current status : " + ((DltTransactionResponse) item).getStatus().getStatus().toString());
            });

            System.out.println("Ripple sender to receiver transaction : " + otherTx);

        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
