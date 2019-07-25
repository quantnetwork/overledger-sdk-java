package network.quant.sdk;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.quant.OverledgerContext;
import network.quant.api.*;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.bitcoin.experimental.BitcoinFaucetHelper;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.essential.dto.*;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.ethereum.EthereumAccount;
import network.quant.ethereum.experimental.EthereumFaucetHelper;
import network.quant.event.ApplicationDataHandler;
import network.quant.exception.ClientResponseException;
import network.quant.mvp.impl.ANCHOR;
import network.quant.ripple.RippleAccount;
import network.quant.ripple.experimental.RippleFaucetHelper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import network.quant.util.Address;
import network.quant.util.PageParams;
import org.web3j.crypto.Credentials;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Properties;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OverledgerSDKHelper {

    private static final String BITCOIN_RECEIVE_ADDRESS = "mmpzm8ck5a6EayKGJFVtRp9nYCRzTePYSa";
    private static final String ETHEREUM_RECEIVE_ADDRESS = "0x00b680e03e452641aa5368a41b12bd25473640a5";
    private static final String RIPPLE_RECEIVE_ADDRESS = "rNaENuTteLsVMe4GDYFHxUg2i7UHKf1Q3a";
    private static final BigDecimal BTC = new BigDecimal("100000000");
    private static final BigDecimal ETH = new BigDecimal("1000000000000000000");
    private static final BigDecimal XRP = new BigDecimal("1000000");
    private static final String ETH_KEY = "ethereum";
    private static final String XBT_KEY = "bitcoin";
    private static final String XRP_KEY = "ripple";

    private static OverledgerSDKHelper I;
    ApplicationDataHandler applicationDataHandler;
    Properties properties;
    NETWORK network = NETWORK.TEST;
    Account bitcoinAccount;
    Account ethereumAccount;
    Account rippleAccount;
    OverledgerSDK overledgerSDK;

    private OverledgerSDKHelper(ApplicationDataHandler applicationDataHandler) {
        this.applicationDataHandler = applicationDataHandler;
        this.loadContext(Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties"));
    }

    private void loadContext(InputStream stream) {
        if (null != stream) {
            try {
                this.properties = new Properties();
                this.properties.load(stream);
                OverledgerContext.loadContext(this.properties);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("Fail to load context file", e);
            }
        }
    }

    private OverledgerTransaction createOverledgerTransaction(BigDecimal payment, BigDecimal premium, BigDecimal annual, File contactFile, String policyId, String reg) throws FileNotFoundException {
        OverledgerTransactionRequest writeOverledgerTransactionRequest = new OverledgerTransactionRequest();
        writeOverledgerTransactionRequest.setMappId(OverledgerContext.MAPP_ID);
        writeOverledgerTransactionRequest.setDltData(new ArrayList<>());

        DltStreamTransactionRequest writeDltTransactionRequestBitcoin = new DltStreamTransactionRequest();
        writeDltTransactionRequestBitcoin.setDlt(DLT.bitcoin.name());
        writeDltTransactionRequestBitcoin.setFromAddress(((BitcoinAccount)this.bitcoinAccount).getKey().toAddress(((BitcoinAccount)this.bitcoinAccount).getNetworkParameters()).toBase58());
        writeDltTransactionRequestBitcoin.setToAddress(BITCOIN_RECEIVE_ADDRESS);
        writeDltTransactionRequestBitcoin.setAmount(BTC.multiply(payment).toBigInteger());
        writeDltTransactionRequestBitcoin.setInputStream(new FileInputStream(contactFile));
        writeDltTransactionRequestBitcoin.setMessage(contactFile.getName());
        writeOverledgerTransactionRequest.getDltData().add(writeDltTransactionRequestBitcoin);

        DltTransactionRequest writeDltTransactionRequestEthereum = new DltTransactionRequest();
        writeDltTransactionRequestEthereum.setDlt(DLT.ethereum.name());
        writeDltTransactionRequestEthereum.setFromAddress(Credentials.create(((EthereumAccount)this.ethereumAccount).getEcKeyPair()).getAddress());
        writeDltTransactionRequestEthereum.setToAddress(ETHEREUM_RECEIVE_ADDRESS);
        writeDltTransactionRequestEthereum.setAmount(ETH.multiply(premium).toBigInteger());
        writeDltTransactionRequestEthereum.setMessage(policyId);
        writeOverledgerTransactionRequest.getDltData().add(writeDltTransactionRequestEthereum);

        DltTransactionRequest writeDltTransactionRequestRipple = new DltTransactionRequest();
        writeDltTransactionRequestRipple.setDlt(DLT.ripple.name());
        writeDltTransactionRequestRipple.setFromAddress(((RippleAccount)this.rippleAccount).getPublicKey());
        writeDltTransactionRequestRipple.setToAddress(RIPPLE_RECEIVE_ADDRESS);
        writeDltTransactionRequestRipple.setAmount(XRP.multiply(annual).toBigInteger());
        writeDltTransactionRequestRipple.setMessage(reg);
        writeOverledgerTransactionRequest.getDltData().add(writeDltTransactionRequestRipple);

        return writeOverledgerTransactionRequest;
    }

    private void checkOverledgerSDK() {
        if (null == this.overledgerSDK) {
            this.overledgerSDK = DefaultOverledgerSDK.newInstance(this.network);
        }
        this.overledgerSDK.addAccount(DLT.bitcoin.name(), this.bitcoinAccount);
        this.overledgerSDK.addAccount(DLT.ethereum.name(), this.ethereumAccount);
        this.overledgerSDK.addAccount(DLT.ripple.name(), this.rippleAccount);
    }

    public void loadOverledgerContextFromFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            this.properties = new Properties();
            this.properties.load(inputStream);
            OverledgerContext.loadContext(this.properties);
            this.applicationDataHandler.onLoadSetting(
                    OverledgerContext.BPI_KEY, OverledgerContext.MAPP_ID,
                    OverledgerContext.WRITE_TRANSACTIONS,
                    OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID,
                    OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID_BY_PAGE,
                    OverledgerContext.READ_TRANSACTIONS_BY_TRANSACTION_ID,
                    null,
                    OverledgerContext.SEARCH_TRANSACTIONS,
                    OverledgerContext.SEARCH_ADDRESSES,
                    OverledgerContext.SEARCH_CHAIN_BLOCKS,
                    OverledgerContext.BALANCES_CHECK
            );
        } catch (IOException e) {
            log.error("Fail to load context file", e);
        }
    }

    public void loadOverledgerContextFromFile(String bpiKey, String mappId, String writeTransactions, String readTransactionsByMappId, String readTransactionsByTransactionId, String readTransactionsByTransactionHash) {
        OverledgerContext.BPI_KEY = bpiKey;
        OverledgerContext.MAPP_ID = mappId;
        OverledgerContext.WRITE_TRANSACTIONS = writeTransactions;
        OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID = readTransactionsByMappId;
        OverledgerContext.READ_TRANSACTIONS_BY_TRANSACTION_ID = readTransactionsByTransactionId;
    }

    public void reaload(ANCHOR anchor) {
        switch (anchor) {
            case SETTINGS:
                this.applicationDataHandler.onLoadSetting(
                        OverledgerContext.BPI_KEY, OverledgerContext.MAPP_ID,
                        OverledgerContext.WRITE_TRANSACTIONS,
                        OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID,
                        OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID_BY_PAGE,
                        OverledgerContext.READ_TRANSACTIONS_BY_TRANSACTION_ID,
                        null,
                        OverledgerContext.SEARCH_TRANSACTIONS,
                        OverledgerContext.SEARCH_ADDRESSES,
                        OverledgerContext.SEARCH_CHAIN_BLOCKS,
                        OverledgerContext.BALANCES_CHECK
                );
                break;
            case ORDER:
                try {
                    if (null == this.overledgerSDK) {
                        this.overledgerSDK = DefaultOverledgerSDK.newInstance(this.network);
                    }
                    PageParams page = new PageParams(0, 10);
                    OverledgerTransactions result = this.overledgerSDK.readTransactions(OverledgerContext.MAPP_ID, page);
                    this.applicationDataHandler.onLoadOrders(result, page);
                } catch (Exception e) {
                    if (e instanceof ClientResponseException) {
                        ClientResponseException clientResponseException = (ClientResponseException) e;
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            OverledgerTransactionResponse writeOverledgerTransactionResponses[] = objectMapper.readValue(clientResponseException.getResponseBody(), OverledgerTransactionResponse[].class);
                            this.applicationDataHandler.onLoadOrders(writeOverledgerTransactionResponses);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        log.error("Fail to read transactions", e);
                        this.applicationDataHandler.onPurchaseFailed("Fail to read transactions");
                    }
                }
        }
    }

    public void generate(String type) throws Exception {
        switch (type) {
            case ETH_KEY:
                EthereumAccount ethereumAccount = (EthereumAccount)EthereumAccount.getInstance(this.network);
                this.ethereumAccount = ethereumAccount;
                this.applicationDataHandler.onAccountGenerated(
                        type,
                        DatatypeConverter.printHexBinary(ethereumAccount.getEcKeyPair().getPrivateKey().toByteArray()),
                        Credentials.create(ethereumAccount.getEcKeyPair()).getAddress()
                );
                break;
            case XBT_KEY:
                BitcoinAccount bitcoinAccount = (BitcoinAccount)BitcoinAccount.getInstance(this.network);
                this.bitcoinAccount = bitcoinAccount;
                this.applicationDataHandler.onAccountGenerated(
                        type,
                        bitcoinAccount.getKey().getPrivateKeyAsHex(),
                        bitcoinAccount.getKey().toAddress(bitcoinAccount.getNetworkParameters()).toBase58()
                );
                break;
            case XRP_KEY:
                RippleAccount rippleAccount = (RippleAccount)RippleAccount.getInstance(this.network);
                this.rippleAccount = rippleAccount;
                this.applicationDataHandler.onAccountGenerated(
                        type,
                        rippleAccount.getPrivateKeyAsString(),
                        rippleAccount.getPublicKey()
                );
                break;
        }
    }

    public void receive(String type) {
        switch (type) {
            case ETH_KEY:
                if (null != this.ethereumAccount) {
                    EthereumFaucetHelper
                            .getInstance(OverledgerContext.FAUCET_ETH)
                            .fundAccount((EthereumAccount) this.ethereumAccount);
                    this.applicationDataHandler.onAccountReceived(type, 1);
                }
                break;
            case XBT_KEY:
                if (null != this.bitcoinAccount) {
                    BitcoinFaucetHelper
                            .getInstance(OverledgerContext.FAUCET_XBT)
                            .fundAccount((BitcoinAccount) this.bitcoinAccount);
                    this.applicationDataHandler.onAccountReceived(type, 1);
                }
                break;
            case XRP_KEY:
                if (null != this.rippleAccount) {
                    RippleFaucetHelper
                            .getInstance(OverledgerContext.FAUCET_XRP)
                            .fundAccount((RippleAccount) this.rippleAccount, BigDecimal.valueOf(500L));
                    this.applicationDataHandler.onAccountReceived(type, 500);
                }
                break;
        }
    }

    public void purchase(String payment, String reg, File contactFile, String policyId, String premium, String annual) throws FileNotFoundException {
        if (null == this.bitcoinAccount || null == this.ethereumAccount) {
            this.applicationDataHandler.onPurchaseFailed("Account not found");
            return;
        }
        payment = payment.toLowerCase().replaceAll(" ", "").replaceAll("btc", "");
        premium = premium.toLowerCase().replaceAll(" ", "").replaceAll("eth", "");
        annual = annual.toLowerCase().replaceAll(" ", "").replaceAll("xrp", "");
        this.checkOverledgerSDK();
        OverledgerTransaction transaction = this.createOverledgerTransaction(
                new BigDecimal(payment),
                new BigDecimal(premium),
                new BigDecimal(annual),
                contactFile,
                policyId,
                reg
                );

        try {
            OverledgerTransaction overledgerTransaction = this.overledgerSDK.writeTransaction(transaction);
            this.applicationDataHandler.onPurchaseSuccess(overledgerTransaction);
        } catch (Exception e) {
            if (e instanceof ClientResponseException) {
                ClientResponseException clientResponseException = (ClientResponseException) e;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    OverledgerTransactionResponse writeOverledgerTransactionResponse = objectMapper.readValue(clientResponseException.getResponseBody(), OverledgerTransactionResponse.class);
                    this.applicationDataHandler.onPurchaseSuccess(writeOverledgerTransactionResponse.getOverledgerTransactionId());
                } catch (IOException e1) {
                    log.error("Fail to write transaction to DLTs", e);
                }
            } else {
                log.error("Fail to write transaction to DLTs", e);
                this.applicationDataHandler.onPurchaseFailed("Fail to write transaction to DLTs");
            }
        }
    }

    public static OverledgerSDKHelper getInstance(ApplicationDataHandler applicationDataHandler) {
        if (null == I) {
            I = new OverledgerSDKHelper(applicationDataHandler);
        }
        return I;
    }

    public void loadOrder(PageParams page) {
        try {
            if (null == this.overledgerSDK) {
                this.overledgerSDK = DefaultOverledgerSDK.newInstance(this.network);
            }
            OverledgerTransactions result = this.overledgerSDK.readTransactions(OverledgerContext.MAPP_ID, page);
            this.applicationDataHandler.onLoadOrders(result, page);
        } catch (Exception e) {
            log.error("Unable to load page", e);
        }
    }

}
