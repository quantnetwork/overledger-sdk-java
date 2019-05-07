package network.quant.examples;

import network.quant.OverledgerContext;
import network.quant.api.DLT;
import network.quant.api.NETWORK;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.bitcoin.experimental.BitcoinFaucetHelper;
import network.quant.ethereum.EthereumAccount;
import network.quant.ethereum.experimental.EthereumFaucetHelper;
import network.quant.exception.ClientResponseException;
import network.quant.iwomm.Util;
import network.quant.ripple.RippleAccount;
import network.quant.ripple.experimental.RippleFaucetHelper;
import network.quant.util.BalanceRequest;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
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

    public static void main(String args[]) {

        util.setupLogger();
        util.checkOverledgerSDK();
        util.loadDemoPropertiesFromContext(Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties"));

        try {

            System.out.println("-------- Create new Bitcoin, Ethereum & Ripple accounts");
            createAccounts();

            System.out.println("-------- Fund newly create Bitcoin, Ethereum & Ripple accounts");
            fundAccounts();


            System.out.println("**** Waiting for 3 minutes. Making sure funding transactions are confirmed on chain.");
            TimeUnit.MINUTES.sleep(1);

            System.out.println("-------- Check balance of account if they have sufficient fund");
            checkBalanceOfAccounts();



          /*  System.out.println(util.overledgerSDK.readTransactions("network.quant.softwarelicensechecke").getTransactions().size());


            //Read Transactions using mappId
            util.overledgerSDK.readTransactions("network.quant.softwarelicensechecke").getTransactions().forEach(transaction -> {
                System.out.println(transaction.getMappId());
                transaction.getDltData().forEach(dltData -> {
                    System.out.println(dltData.getDlt());
                });
            });
*/
            System.out.println(util.overledgerSDK.readTransaction("bitcoin","81e42231d7b72b384da84358608a47fed4b610cc95a0727a183b85f98dee87fb"));




        }catch(Exception ex) {

            ClientResponseException ee = (ClientResponseException) ex;
            System.out.println(ee.getResponseBody());
            ex.getMessage();
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
        System.out.println("Bitcoin account funded, please allow at least 10 minutes before you check the balance.");
        EthereumFaucetHelper.getInstance(OverledgerContext.FAUCET_ETH).fundAccount(senderEthereumAccount);
        System.out.println("Ethereum account funded, please allow at least 5 minutes before you check the balance.");
        RippleFaucetHelper.getInstance(OverledgerContext.FAUCET_XRP).fundAccount(senderRippleAccount, BigDecimal.valueOf(20L));
        System.out.println("Ripple account funded, please allow at least 5 minutes before you check the balance.");

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

}
