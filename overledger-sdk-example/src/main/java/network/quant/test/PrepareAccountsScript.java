package network.quant.test;

import network.quant.OverledgerContext;
import network.quant.api.DLT;
import network.quant.api.NETWORK;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.bitcoin.experimental.BitcoinFaucetHelper;
import network.quant.ethereum.EthereumAccount;
import network.quant.ethereum.experimental.EthereumFaucetHelper;
import network.quant.ripple.RippleAccount;
import network.quant.ripple.experimental.RippleFaucetHelper;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;

public class PrepareAccountsScript {

    static Util util = new Util();

    private static final BigDecimal ETH = new BigDecimal("1000000000000000000");
    private static final BigDecimal XRP = new BigDecimal("1000000");

    public static void main(String args[]) throws Exception {

        util.setupLogger();
        util.checkOverledgerSDK();
        //util.loadDemoPropertiesFromContext(Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties"));

        //Script 1
        //1-oneOone-quant
        //Create account and display it

        // ========== INSURANCE & DVLA ACCOUNTS

        EthereumAccount insuranceEthereumAccount = (EthereumAccount) EthereumAccount.getInstance(NETWORK.TEST);
        RippleAccount dvlaRippleAccount = (RippleAccount) RippleAccount.getInstance(NETWORK.TEST);

        util.overledgerSDK.addAccount(DLT.ethereum.name(), insuranceEthereumAccount);
        util.overledgerSDK.addAccount(DLT.ripple.name(), dvlaRippleAccount);

        //Fund the account

        EthereumFaucetHelper.getInstance(OverledgerContext.FAUCET_ETH).fundAccount((EthereumAccount) insuranceEthereumAccount);
        RippleFaucetHelper.getInstance(OverledgerContext.FAUCET_XRP).fundAccount((RippleAccount) dvlaRippleAccount, BigDecimal.valueOf(200L));


        util.logger.info("--> Insurer ETH Account Address: " + Credentials.create(((EthereumAccount) insuranceEthereumAccount).getEcKeyPair()).getAddress());
        util.logger.info("--> Insurance Ethereum: Private Key " + insuranceEthereumAccount.getPrivateKey());


        util.logger.info("--> DVLA Ripple Account Address: " + ((RippleAccount) dvlaRippleAccount).getPublicKey());
        util.logger.info("--> DVLA Ripple Account: Private Key as String " + ((RippleAccount) dvlaRippleAccount).getPrivateKeyAsString());


        // ========== SHOP ACCOUNTS

        BitcoinAccount shopBitcoinAccount = (BitcoinAccount) BitcoinAccount.getInstance(NETWORK.TEST);
        EthereumAccount shopEthereumAccount = (EthereumAccount) EthereumAccount.getInstance(NETWORK.TEST);
        RippleAccount shopRippleAccount = (RippleAccount) RippleAccount.getInstance(NETWORK.TEST);


        util.overledgerSDK.addAccount(DLT.bitcoin.name(), shopBitcoinAccount);
        util.overledgerSDK.addAccount(DLT.ethereum.name(), shopEthereumAccount);
        util.overledgerSDK.addAccount(DLT.ripple.name(), shopRippleAccount);


        //Fund the account

        BitcoinFaucetHelper.getInstance(OverledgerContext.FAUCET_XBT).fundAccount((BitcoinAccount) shopBitcoinAccount);
        EthereumFaucetHelper.getInstance(OverledgerContext.FAUCET_ETH).fundAccount((EthereumAccount) shopEthereumAccount);
        RippleFaucetHelper.getInstance(OverledgerContext.FAUCET_XRP).fundAccount((RippleAccount) shopRippleAccount, BigDecimal.valueOf(200L));

        util.logger.info("Accounts funded....");

        util.logger.info("--> SHOP Bitcoin Account: ADDRESS " + shopBitcoinAccount.getKey().toAddress(shopBitcoinAccount.getNetworkParameters()).toBase58());

        util.logger.info("--> SHOP ETH Account: " + Credentials.create(((EthereumAccount) shopEthereumAccount).getEcKeyPair()).getAddress());
        util.logger.info("--> SHOP Ethereum: Private Key " + shopEthereumAccount.getPrivateKey());

        util.logger.info("--> SHOP Ripple Account: " + ((RippleAccount) shopRippleAccount).getPublicKey());
        util.logger.info("--> SHOP Ripple Account: Private Key as String " + ((RippleAccount) shopRippleAccount).getPrivateKeyAsString());

    }

}
