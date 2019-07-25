package network.quant.mvp.impl;

import network.quant.api.OverledgerTransaction;
import network.quant.api.OverledgerTransactions;
import network.quant.compoent.ApplicationFrame;
import network.quant.compoent.ContentPanel;
import network.quant.compoent.HeaderPanel;
import network.quant.compoent.RootPanel;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.event.ApplicationDataHandler;
import network.quant.event.ApplicationExitHandler;
import network.quant.event.ApplicationHistoryEvent;
import network.quant.mvp.Factory;
import network.quant.mvp.presenter.ContentPresenter;
import network.quant.mvp.presenter.HeaderPresenter;
import network.quant.sdk.OverledgerSDKHelper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import network.quant.util.Page;
import network.quant.util.PageParams;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationFactory implements Factory, ApplicationExitHandler, ApplicationDataHandler {

    private static Factory I;

    ApplicationHistoryEvent applicationHistoryEvent;
    ApplicationFrame applicationFrame;
    HeaderPresenter headerPresenter;
    ContentPresenter contentPresenter;

    @Override
    public void config() {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) { }
            this.getApplicationFrame().setVisible(true);
        });
    }

    @Override
    public ApplicationHistoryEvent getHistoryHandler() {
        if (null == this.applicationHistoryEvent) {
            this.applicationHistoryEvent = new ApplicationHistoryEvent(this.getContentPresenter());
        }
        return this.applicationHistoryEvent;
    }

    @Override
    public void onExit() {
        this.applicationFrame.setVisible(false);
        System.exit(0);
    }

    @Override
    public void onLoadSetting(
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
            String balances

    ) {
        this.getContentPresenter().loadSettings(
                bpiKey,
                mappId,
                writeTransactions,
                readTransactionsByMappId,
                readTransactionsByMappIdPage,
                readTransactionsByTransactionId,
                readTransactionsByTransactionHash,
                searchTransaction,
                searchAddress,
                searchBlocks,
                balances
                );
    }

    @Override
    public void onAccountGenerated(String type, String secretKey, String address) {
        this.contentPresenter.loadWallet(type, secretKey, address);
    }

    @Override
    public void onAccountReceived(String type, int amount) {
        this.contentPresenter.loadWallet(type, amount);
    }

    @Override
    public void onPurchaseFailed(String message) {
        this.contentPresenter.purchaseFailed(message);
    }

    @Override
    public void onPurchaseSuccess(OverledgerTransaction transaction) {
        this.contentPresenter.purchaseSuccess(transaction);
    }

    @Override
    public void onPurchaseSuccess(UUID overledgerTransactionId) {
        this.contentPresenter.purchaseSuccess(overledgerTransactionId);
    }

    @Override
    public void onLoadOrders(OverledgerTransactions overledgerTransactionsResponse, PageParams pageParams) {
        this.contentPresenter.loadOrders(overledgerTransactionsResponse, pageParams);
    }

    @Override
    public void onLoadOrders(OverledgerTransactionResponse[] writeOverledgerTransactionResponses) {
        this.contentPresenter.loadOrders(writeOverledgerTransactionResponses);
    }

    private HeaderPresenter getHeaderPresenter() {
        if (null == this.headerPresenter) {
            this.headerPresenter = HeaderPresenterImpl.newInstance(HeaderPanel.newInstance(), this.getHistoryHandler(), this);
        }
        return this.headerPresenter;
    }

    private ContentPresenter getContentPresenter() {
        if (null == this.contentPresenter) {
            this.contentPresenter = ContentPresenterImpl.newInstance(ContentPanel.newInstance(), OverledgerSDKHelper.getInstance(this));
        }
        return this.contentPresenter;
    }

    private RootPanel getRootPanel() {
        return RootPanel.newInstance(this.getHeaderPresenter().asView(), this.getContentPresenter().asView());
    }

    private ApplicationFrame getApplicationFrame() {
        if (null == this.applicationFrame) {
            this.applicationFrame = ApplicationFrame.newInstance(this.getRootPanel());
        }
        return this.applicationFrame;
    }

    public static Factory getInstance() {
        return Optional.ofNullable(I).orElse(I = new ApplicationFactory());
    }

}
