package network.quant;

import network.quant.api.NETWORK;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Overledger Context contains the BPI key and Mapp ID
 * All Overledger operations required OverledgerContext to be setup first
 */
public class OverledgerContext {

    private static final String TEST_URL_KEY = "overledger.testurl";
    private static final String MAIN_URL_KEY = "overledger.mainurl";
    private static final String WRITE_TRANSACTIONS_KEY = "overledger.write";
    private static final String READ_TRANSACTIONS_BY_TRANSACTION_ID_KEY = "overledger.read.id";
    private static final String READ_TRANSACTIONS_BY_MAPP_ID_KEY = "overledger.read.mappid";
    private static final String READ_TRANSACTIONS_BY_MAPP_BY_PAGE_ID_KEY = "overledger.read.mappid.page";
    private static final String SEARCH_TRANSACTIONS_KEY = "overledger.search.transactions";
    private static final String SEARCH_ADDRESSES_KEY = "overledger.search.addresses";
    private static final String SEARCH_CHAIN_BLOCKS_KEY = "overledger.search.chain.blocks";
    private static final String BALANCES_CHECK_KEY = "overledger.balances";
    private static final String SEQUENCE_CHECK_KEY = "overledger.sequence";
    private static final String BPI_KEY_KEY = "overledger.bpikey";
    private static final String MAPP_KEY = "overledger.mappid";
    private static final String FAUCET_XBT_KEY = "bitcoin.faucet.url";
    private static final String FAUCET_ETH_KEY = "ethereum.faucet.url";
    private static final String FAUCET_XRP_KEY = "ripple.faucet.url";
    private static final String FEE_ESTIMATION_KEY = "overledger.fee.estimation";

    public static String FEE_ESTIMATION;
    public static String WRITE_TRANSACTIONS;
    public static String READ_TRANSACTIONS_BY_TRANSACTION_ID;
    public static String READ_TRANSACTIONS_BY_MAPP_ID;
    public static String READ_TRANSACTIONS_BY_MAPP_ID_BY_PAGE;
    public static String SEARCH_TRANSACTIONS;
    public static String SEARCH_ADDRESSES;
    public static String SEARCH_CHAIN_BLOCKS;
    public static String BALANCES_CHECK;
    public static String SEQUENCE_CHECK;
    public static String FAUCET_XBT;
    public static String FAUCET_ETH;
    public static String FAUCET_XRP;
    public static String BPI_KEY;
    public static String MAPP_ID;

    public static String TEST_URL;
    public static String MAIN_URL;
    private static NETWORK CURRENT_NETWORK = NETWORK.TEST;

    /**
     * Load context from properties Object
     *
     * @param properties Properties containing properties
     */
    public static void loadContext(Properties properties) {
        TEST_URL = properties.getProperty(TEST_URL_KEY);
        MAIN_URL = properties.getProperty(MAIN_URL_KEY);
        String baseUrl = NETWORK.MAIN.equals(CURRENT_NETWORK) ? MAIN_URL : TEST_URL;
        WRITE_TRANSACTIONS = String.format("%s%s", baseUrl, properties.getProperty(WRITE_TRANSACTIONS_KEY));
        ;
        READ_TRANSACTIONS_BY_TRANSACTION_ID = String.format("%s%s", baseUrl, properties.getProperty(READ_TRANSACTIONS_BY_TRANSACTION_ID_KEY));
        READ_TRANSACTIONS_BY_MAPP_ID = String.format("%s%s", baseUrl, properties.getProperty(READ_TRANSACTIONS_BY_MAPP_ID_KEY));
        READ_TRANSACTIONS_BY_MAPP_ID_BY_PAGE = String.format("%s%s", baseUrl, properties.getProperty(READ_TRANSACTIONS_BY_MAPP_BY_PAGE_ID_KEY));
        SEARCH_TRANSACTIONS = String.format("%s%s", baseUrl, properties.getProperty(SEARCH_TRANSACTIONS_KEY));
        SEARCH_ADDRESSES = String.format("%s%s", baseUrl, properties.getProperty(SEARCH_ADDRESSES_KEY));
        SEARCH_CHAIN_BLOCKS = String.format("%s%s", baseUrl, properties.getProperty(SEARCH_CHAIN_BLOCKS_KEY));
        BALANCES_CHECK = String.format("%s%s", baseUrl, properties.getProperty(BALANCES_CHECK_KEY));
        SEQUENCE_CHECK = String.format("%s%s", baseUrl, properties.getProperty(SEQUENCE_CHECK_KEY));
        FAUCET_XBT = String.format("%s%s", baseUrl, properties.getProperty(FAUCET_XBT_KEY));
        FAUCET_ETH = String.format("%s%s", baseUrl, properties.getProperty(FAUCET_ETH_KEY));
        FAUCET_XRP = String.format("%s%s", baseUrl, properties.getProperty(FAUCET_XRP_KEY));
        BPI_KEY = properties.getProperty(BPI_KEY_KEY);
        MAPP_ID = properties.getProperty(MAPP_KEY);

        FEE_ESTIMATION = String.format("%s%s", baseUrl, properties.getProperty(FEE_ESTIMATION_KEY));
    }

    /**
     * Load context from stream
     *
     * @param network
     * @param inputStream InputStream containing the stream of properties
     * @throws IOException when failed to read properties file
     */
    public static void loadContext(NETWORK network, InputStream inputStream) throws IOException {
        CURRENT_NETWORK = network;
        if (null != inputStream) {
            Properties properties = new Properties();
            properties.load(inputStream);
            loadContext(properties);
        }
    }

}
