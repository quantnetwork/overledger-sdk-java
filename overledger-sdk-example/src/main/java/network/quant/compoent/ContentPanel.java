package network.quant.compoent;

import network.quant.mvp.impl.ANCHOR;
import network.quant.mvp.presenter.ContentPresenter;
import network.quant.mvp.view.ContentView;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ContentPanel extends BaseComponent implements ContentView {

    private ContentPresenter contentPresenter;
    private Map<ANCHOR, BaseComponent> viewBag = new HashMap<>();
    private BaseComponent currentView;
    private ANCHOR currentAnchor;

    private ContentPanel() {
        super(new Dimension(1024, 768 - 82));
    }

    private BaseComponent loadView(ANCHOR anchor) {
        if (!this.viewBag.containsKey(anchor)) {
            switch (anchor) {
                case ORDER: this.viewBag.put(anchor, new OrderPanel(new Dimension(this.getSize().width-40, this.getSize().height-40), this.contentPresenter)); break;
                case WALLET: this.viewBag.put(anchor, new WalletPanel(new Dimension(this.getSize().width-40, this.getSize().height-40), this.contentPresenter)); break;
                case SETTINGS: this.viewBag.put(anchor, new SettingsPanel(new Dimension(this.getSize().width-40, this.getSize().height-40), this.contentPresenter)); break;
                case DETAILS: this.viewBag.put(anchor, new DetailPanel(new Dimension(this.getSize().width-40, this.getSize().height-40), this.contentPresenter)); break;
                default: this.viewBag.put(anchor, new WelcomePanel(new Dimension(this.getSize().width-40, this.getSize().height-40), this.contentPresenter)); break;
            }
        }
        return this.viewBag.get(anchor);
    }

    @Override
    public void setPresenter(ContentPresenter contentPresenter) {
        this.contentPresenter = contentPresenter;
        this.changeTo(ANCHOR.WELCOME);
    }

    @Override
    public void changeTo(ANCHOR anchor) {
        if (null != this.currentAnchor && this.currentAnchor.equals(anchor)) {
            return;
        }
        if (null != this.currentView) {
            this.remove(this.currentView);
        }
        this.currentView = this.loadView(anchor);
        if (null != this.currentView) {
            this.add(this.currentView, 0);
            this.currentView.setLocation(20, 20);
        }
        this.currentAnchor = anchor;
        if (null != this.getParent()) {
            this.getParent().getParent().repaint();
        } else {
            this.repaint();
        }
        this.currentView.repaint();
    }

    @Override
    public SettingsPanel getCurrentViewAsSettingsPanel() {
        if (ANCHOR.SETTINGS.equals(this.currentAnchor)) {
            return (SettingsPanel) this.currentView;
        } else {
            return null;
        }
    }

    @Override
    public WalletPanel getCurrentViewAsWalletPanel() {
        if (ANCHOR.WALLET.equals(this.currentAnchor)) {
            return (WalletPanel) this.currentView;
        } else {
            return null;
        }
    }

    @Override
    public OrderPanel getCurrentViewAsOrderPanel() {
        if (ANCHOR.ORDER.equals(this.currentAnchor)) {
            return (OrderPanel) this.currentView;
        } else {
            return null;
        }
    }

    @Override
    public DetailPanel getCurrentViewAsDetailPanel() {
        if (ANCHOR.DETAILS.equals(this.currentAnchor)) {
            return (DetailPanel) this.currentView;
        } else {
            return null;
        }
    }

    @Override
    public void showDetails(int index) {
        if (ANCHOR.DETAILS.equals(this.currentAnchor)) {
            ((DetailPanel) this.currentView).display(index);
        }
    }

    @Override
    public Component asComponent() {
        return this;
    }

    public static ContentView newInstance() {
        return new ContentPanel();
    }

}
