package network.quant.mvp.view;

import network.quant.compoent.DetailPanel;
import network.quant.compoent.OrderPanel;
import network.quant.compoent.SettingsPanel;
import network.quant.compoent.WalletPanel;
import network.quant.mvp.impl.ANCHOR;
import network.quant.mvp.presenter.ContentPresenter;

public interface ContentView extends View {

    void setPresenter(ContentPresenter contentPresenter);

    void changeTo(ANCHOR anchor);

    SettingsPanel getCurrentViewAsSettingsPanel();

    WalletPanel getCurrentViewAsWalletPanel();

    OrderPanel getCurrentViewAsOrderPanel();

    DetailPanel getCurrentViewAsDetailPanel();

    void showDetails(int index);

}
