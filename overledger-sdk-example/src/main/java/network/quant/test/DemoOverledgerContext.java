package network.quant.test;

import network.quant.OverledgerContext;

import java.util.Properties;

public class DemoOverledgerContext extends OverledgerContext {

    private static final String INSURER_ETH_PRIVATE_K  = "insurer.eth.privatekey";
    private static final String DVLA_XRP_SECURE_K  = "dvla.ripple.securekey";
    private static final String SHOP_BTC_ACCOUNT_ADD  = "shop.btc.address";
    private static final String SHOP_ETH_PRIVATE_K = "shop.eth.privatekey";
    private static final String SHOP_XRP_SECURE_K  = "shop.ripple.securekey";


    public static String INSURER_ETH_PRIVATE_KEY  = "insurer.eth.privatekey";
    public static String DVLA_XRP_SECURE_KEY  = "dvla.ripple.securekey";
    public static String SHOP_BTC_ACCOUNT_ADDRESS  = "shop.btc.address";
    public static String SHOP_ETH_PRIVATE_KEY  = "shop.eth.privatekey";
    public static String SHOP_XRP_SECURE_KEY  = "shop.ripple.securekey";

    public static void loadDemoProperties(Properties properties) {
        INSURER_ETH_PRIVATE_KEY = properties.getProperty(INSURER_ETH_PRIVATE_K);
        DVLA_XRP_SECURE_KEY = properties.getProperty(DVLA_XRP_SECURE_K);
        SHOP_BTC_ACCOUNT_ADDRESS = properties.getProperty(SHOP_BTC_ACCOUNT_ADD);
        SHOP_ETH_PRIVATE_KEY = properties.getProperty(SHOP_ETH_PRIVATE_K);
        SHOP_XRP_SECURE_KEY = properties.getProperty(SHOP_XRP_SECURE_K);
    }

}
