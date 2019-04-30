package network.quant.essential;

import network.quant.OverledgerContext;
import network.quant.api.*;
import network.quant.api.DltTransactionRequest;
import network.quant.essential.dto.*;
import network.quant.essential.exception.DltNotSupportedException;
import network.quant.essential.exception.EmptyAccountException;
import network.quant.essential.exception.EmptyDltException;
import network.quant.essential.exception.IllegalKeyException;
import lombok.extern.slf4j.Slf4j;
import network.quant.util.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Basic implementation of OverledgerSDK
 */
@Slf4j
public final class DefaultOverledgerSDK implements OverledgerSDK {

    private NETWORK network;
    private AccountManager accountManager;
    private Client client;

    private DefaultOverledgerSDK(NETWORK network) {
        this(network, AccountManager.newInstance(), null);
    }

    private DefaultOverledgerSDK(NETWORK network, AccountManager accountManager, Client client) {
        try {
            OverledgerContext.loadContext(network, Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.initial(network);
        this.accountManager = accountManager;
        this.client = null == client?OverledgerClient.getInstance():client;
    }

    private void throwCauseException(Exception e) throws Exception {
        Throwable exception = e;
        while (null != exception.getCause()) {
            exception = e.getCause();
        }
        throw (Exception) exception;
    }

    private boolean verifySupportAllDLTs(OverledgerTransaction ovlTransaction) {
        return ovlTransaction.getDltData().stream().anyMatch(dltTransaction ->
                Optional.ofNullable(this.accountManager.getAccount(dltTransaction.getDlt())).isPresent());
    }

    private void verifyOverledgerTransaction(OverledgerTransaction ovlTransaction) throws EmptyDltException, DltNotSupportedException {
        if (null == ovlTransaction) {
            throw new NullPointerException();
        }
        if (null == ovlTransaction.getDltData()) {
            throw new EmptyDltException();
        }
        if (!this.verifySupportAllDLTs(ovlTransaction)) {
            throw new DltNotSupportedException();
        }
    }

    public NETWORK getNetwork() {
        return this.network;
    }

    @Override
    public void initial(NETWORK network) {
        this.network = network;
    }

    @Override
    public void addAccount(String dlt, Account account) {
        try {
            this.accountManager.registerAccount(dlt, account.withNetwork(this.network));
        } catch (IllegalKeyException | EmptyAccountException e) {
            log.error(e.toString(), e);
        }
    }

    @Override
    public OverledgerTransaction writeTransaction(OverledgerTransaction ovlTransaction) throws Exception {
        this.verifyOverledgerTransaction(ovlTransaction);
        OverledgerTransaction overledgerTransaction = null;
        try {
            ovlTransaction.getDltData().stream()
                    .map(dltTransaction -> (DltTransactionRequest)dltTransaction)
                    .map(dltTransactionRequest -> {
                        if (dltTransactionRequest instanceof DltStreamTransactionRequest) {
                            this.accountManager.getAccount(dltTransactionRequest.getDlt()).sign(
                                    dltTransactionRequest.getFromAddress(),
                                    dltTransactionRequest.getToAddress(),
                                    ((DltStreamTransactionRequest) dltTransactionRequest).getInputStream(),
                                    dltTransactionRequest
                            );
                        } else if (dltTransactionRequest instanceof DltBytesTransactionRequest) {
                            this.accountManager.getAccount(dltTransactionRequest.getDlt()).sign(
                                    dltTransactionRequest.getFromAddress(),
                                    dltTransactionRequest.getToAddress(),
                                    ((DltBytesTransactionRequest) dltTransactionRequest).getBytes(),
                                    dltTransactionRequest
                            );
                        } else {
                            this.accountManager.getAccount(dltTransactionRequest.getDlt()).sign(
                                    dltTransactionRequest.getFromAddress(),
                                    dltTransactionRequest.getToAddress(),
                                    dltTransactionRequest.getMessage(),
                                    dltTransactionRequest
                            );
                        }
                        return dltTransactionRequest;
                    }).collect(Collectors.toList());
            overledgerTransaction = (OverledgerTransactionResponse)this.client.postTransaction(ovlTransaction, OverledgerTransactionRequest.class, OverledgerTransactionResponse.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    @Override
    public OverledgerTransaction readTransaction(UUID overledgerTransactionID) throws Exception {
        OverledgerTransaction overledgerTransaction = null;
        try {
            overledgerTransaction = (OverledgerTransactionResponse)this.client.getTransaction(overledgerTransactionID, OverledgerTransactionResponse.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    @Override
    public OverledgerTransactions readTransactions(String mappId) throws Exception {
        OverledgerTransactions overledgerTransactions = null;
        try {
            overledgerTransactions = this.client.getTransactions(mappId, OverledgerTransactionsResponse.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransactions;
    }

    @Override
    public OverledgerTransactions readTransactions(String mappId, PageParams pageParams) throws Exception {
        OverledgerTransactions overledgerTransactions = null;
        try {
            overledgerTransactions = this.client.getTransactions(mappId, pageParams, OverledgerTransactionsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            this.throwCauseException(e);
        }
        return overledgerTransactions;
    }

    @Override
    @Deprecated
    public OverledgerTransaction readTransaction(String dlt, String transactionHash) throws Exception {
        OverledgerTransaction overledgerTransaction = null;
        try {
            overledgerTransaction = (OverledgerTransactionResponse)this.client.getTransaction(dlt, transactionHash, OverledgerTransactionResponse.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    @Override
    public Transaction searchTransaction(String transactionHash, Class<Transaction> responseClass) {
        return this.client.searchTransaction(transactionHash, responseClass);
    }

    @Override
    @Deprecated
    public Address searchAddress(String address, Class<Address> responseClass) {
        return this.client.searchAddress(address, responseClass);
    }

    @Override
    public Address searchAddress(String address) {
        return this.client.searchAddress(address, Address.class);
    }

    @Deprecated
    public Block searchBlock(String dlt, String blockhash, Class<Block> responseClass) {
        return this.client.searchBlock(dlt, blockhash, responseClass);
    }

    @Override
    public Block searchBlock(String dlt, String blockhash) {
        return this.client.searchBlock(dlt, blockhash, Block.class);
    }

    @Override
    public Block searchBlock(String dlt, BigDecimal blocknumber) {
        return this.client.searchBlock(dlt, blocknumber.toString(), Block.class);
    }

    @Override
    public List<BalanceResponse> searchBalance(List<BalanceRequest> balanceRequests) {
        return this.client.postBalances(balanceRequests);
    }

    @Override
    public SequenceResponse searchSequence(SequenceRequest sequenceRequest) {
        return this.client.postSequence(sequenceRequest);
    }

    /**
     * Write transaction to BPI layer from byte array
     * @param ovlTransaction OverledgerTransaction containing overledger transaction request
     * @param data byte array containing the data
     * @return Overledger response
     * @throws Exception throw if connection between client and manager is broken
     */
    public OverledgerTransaction writeTransaction(OverledgerTransaction ovlTransaction, byte[] data) throws Exception {
        this.verifyOverledgerTransaction(ovlTransaction);
        OverledgerTransaction overledgerTransaction = null;
        try {
            ovlTransaction.getDltData().stream()
                    .map(dltTransaction -> (DltTransactionRequest)dltTransaction)
                    .map(dltTransactionRequest -> {
                        this.accountManager.getAccount(dltTransactionRequest.getDlt()).sign(
                                dltTransactionRequest.getFromAddress(),
                                dltTransactionRequest.getToAddress(),
                                data,
                                dltTransactionRequest
                        );
                        return dltTransactionRequest;
                    }).collect(Collectors.toList());
            overledgerTransaction = (OverledgerTransactionResponse)this.client.postTransaction(ovlTransaction, OverledgerTransactionRequest.class, OverledgerTransactionResponse.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    /**
     * Write transaction to BPI layer from input stream
     * @param ovlTransaction OverledgerTransaction containing overledger transaction request
     * @param inputStream InputSteam containing data stream
     * @return Overledger response
     * @throws Exception throw if connection between client and manager is broken
     */
    public OverledgerTransaction writeTransaction(OverledgerTransaction ovlTransaction, InputStream inputStream) throws Exception {
        return this.writeTransaction(ovlTransaction, CommonUtil.getStream(inputStream));
    }

    public static DefaultOverledgerSDK newInstance(NETWORK network) {
        return new DefaultOverledgerSDK(network);
    }

    public static DefaultOverledgerSDK newInstance(NETWORK network, AccountManager accountManager, Client client) {
        return new DefaultOverledgerSDK(network, accountManager, client);
    }

    public static DefaultOverledgerSDK newInstance() {
        return new DefaultOverledgerSDK(NETWORK.TEST);
    }

    public static DefaultOverledgerSDK newInstance(AccountManager accountManager, Client client) {
        return new DefaultOverledgerSDK(NETWORK.TEST, accountManager, client);
    }

}
