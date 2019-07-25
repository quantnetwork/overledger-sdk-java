package network.quant.mvp.impl;

import network.quant.api.OverledgerTransaction;
import network.quant.api.OverledgerTransactions;
import network.quant.compoent.*;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.mvp.presenter.ContentPresenter;
import network.quant.mvp.view.ContentView;
import network.quant.mvp.view.View;
import network.quant.sdk.OverledgerSDKHelper;
import lombok.extern.slf4j.Slf4j;
import network.quant.util.Page;
import network.quant.util.PageParams;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ContentPresenterImpl implements ContentPresenter {

    private ContentView contentView;
    private OverledgerSDKHelper overledgerSDKHelper;

    private ContentPresenterImpl(ContentView contentView, OverledgerSDKHelper overledgerSDKHelper) {
        this.contentView = contentView;
        this.contentView.setPresenter(this);
        this.overledgerSDKHelper = overledgerSDKHelper;
    }

    @Override
    public View asView() {
        return this.contentView;
    }

    @Override
    public void changeTo(ANCHOR anchor) {
        this.contentView.changeTo(anchor);
        this.overledgerSDKHelper.reaload(anchor);
    }

    @Override
    public void loadSettingsPropertiesFromFile(File file) {
        this.overledgerSDKHelper.loadOverledgerContextFromFile(file);
    }

    @Override
    public void loadSettings(
            String bpiKey,
            String mappId,
            String writeTransactions,
            String readTransactionsByMappId,
            String readTransactionsByMappIdPage,
            String readTransactionsByTransactionId,
            String readTransactionsByTransactionHash,
            String searchTransaction,
            String searchAddress,
            String searchBlocks,
            String balances) {
        SettingsPanel settingsPanel = this.contentView.getCurrentViewAsSettingsPanel();
        if (null != settingsPanel) {
            settingsPanel.bpiKeyField.setText(bpiKey);
            settingsPanel.mappIdField.setText(mappId);

            settingsPanel.writeField.setText(writeTransactions);
            settingsPanel.readByMappIdField.setText(readTransactionsByMappId);
            settingsPanel.readByMappIdPageField.setText(readTransactionsByMappIdPage);
            settingsPanel.readByIdField.setText(readTransactionsByTransactionId);
            settingsPanel.readByHashField.setText(readTransactionsByTransactionHash);
            settingsPanel.searchTransactionField.setText(searchTransaction);
            settingsPanel.searchAddressField.setText(searchAddress);
            settingsPanel.searchBlocksField.setText(searchBlocks);
            settingsPanel.balancesField.setText(balances);
        }
    }

    @Override
    public void loadWallet(String type, String secretKey, String address) {
        WalletPanel walletPanel = this.contentView.getCurrentViewAsWalletPanel();
        if (null != walletPanel) {
            if (type.equals(WalletComponent.TYPE.bitcoin.name())) {
                walletPanel.bitcoinWallet.secretKey.setText(secretKey);
                walletPanel.bitcoinWallet.publicAddress.setText(address);
            } else if (type.equals(WalletComponent.TYPE.ethereum.name())) {
                walletPanel.ethereumWallet.secretKey.setText(secretKey);
                walletPanel.ethereumWallet.publicAddress.setText(address);
            } else if (type.equals(WalletComponent.TYPE.ripple.name())) {
                walletPanel.rippleWallet.secretKey.setText(secretKey);
                walletPanel.rippleWallet.publicAddress.setText(address);
            }
        }
    }

    @Override
    public void loadWallet(String type, int amount) {
        JOptionPane.showMessageDialog(this.contentView.asComponent(), String.format("%d %s is funded", amount, type));
    }

    @Override
    public void loadOrders(OverledgerTransactions overledgerTransactionsResponse, PageParams page) {
        OrderPanel orderPanel = this.contentView.getCurrentViewAsOrderPanel();
        if (null != orderPanel) {
            orderPanel.loadList(overledgerTransactionsResponse, page);
        }
    }

    @Override
    public void loadOrders(OverledgerTransactionResponse[] writeOverledgerTransactionResponses) {
        OrderPanel orderPanel = this.contentView.getCurrentViewAsOrderPanel();
        if (null != orderPanel) {
            orderPanel.loadArray(writeOverledgerTransactionResponses);
        }
    }

    @Override
    public void onLoadOrders(PageParams page) {
        this.overledgerSDKHelper.loadOrder(page);
    }

    @Override
    public void onGotoDetails(int index) {
        this.contentView.changeTo(ANCHOR.DETAILS);
        this.contentView.showDetails(index);
    }

    @Override
    public void onPurchase(String payment, String reg, File contactFile, String policyId, String premium, String annual) {
        try {
            this.overledgerSDKHelper.purchase(payment, reg, contactFile, policyId, premium, annual);
        } catch (FileNotFoundException e) {
            log.error(e.toString(), e);
        }
    }

    @Override
    public void purchaseFailed(String message) {
        JOptionPane.showMessageDialog(this.contentView.asComponent(), message);
    }

    @Override
    public void purchaseSuccess(OverledgerTransaction transaction) {
        DetailPanel detailPanel = this.contentView.getCurrentViewAsDetailPanel();
        if (null != detailPanel) {
            detailPanel.showTransaction((OverledgerTransactionResponse) transaction);
        }
        JOptionPane.showMessageDialog(this.contentView.asComponent(), "Transaction accepted: " + transaction.getOverledgerTransactionId());
    }

    @Override
    public void purchaseSuccess(UUID overledgerTransactionId) {
        JOptionPane.showMessageDialog(this.contentView.asComponent(), "Transaction accepted: " + overledgerTransactionId);
    }

    @Override
    public void onGotoMainWithUpdate(String bpiKey, String mappId, String writeTransactions, String readTransactionsByMappId, String readTransactionsByTransactionId, String readTransactionsByTransactionHash) {
        this.contentView.changeTo(ANCHOR.WELCOME);
        this.overledgerSDKHelper.loadOverledgerContextFromFile(
                bpiKey, mappId, writeTransactions, readTransactionsByMappId, readTransactionsByTransactionId, readTransactionsByTransactionHash
        );
    }

    @Override
    public void onGotoMain() {
        this.contentView.changeTo(ANCHOR.WELCOME);
    }

    @Override
    public void generate(String type) {
        try {
            this.overledgerSDKHelper.generate(type);
        } catch (Exception e) {
            log.error("Fail to generate account", e);
        }
    }

    @Override
    public void receive(String type) {
        this.overledgerSDKHelper.receive(type);
    }

    public static ContentPresenter newInstance(ContentView contentView, OverledgerSDKHelper overledgerSDKHelper) {
        return new ContentPresenterImpl(contentView, overledgerSDKHelper);
    }

}
