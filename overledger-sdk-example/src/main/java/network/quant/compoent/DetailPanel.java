package network.quant.compoent;

import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.mvp.presenter.ContentPresenter;
import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import javax.swing.*;
import java.awt.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DetailPanel extends BaseComponent {

    private static final Color TEXT = new Color(38, 31, 99);
    ContentPresenter contentPresenter;
    JLabel title;
    BikeCard card;
    PaymentComponent paymentComponent;
    InsuranceComponent insuranceComponent;
    RoadTaxComponent roadTaxComponent;
    ResultComponent resultComponent;
    Button okButton = new Button("PURCHASE", Button.TYPE.OK);
    Button cancelButton = new Button("CANCEL", Button.TYPE.CANCEL);

    public DetailPanel(Dimension dimension, ContentPresenter contentPresenter) {
        super(dimension);
        this.contentPresenter = contentPresenter;

        int componentIndex = 0;

        this.title = new JLabel("Motorcycle Shop");
        this.title.setSize(dimension.width - 20, 64);
        this.title.setLocation(10, 10);
        this.title.setFont(UITools.getFont(Font.PLAIN, 48));
        this.title.setForeground(TEXT);
        this.add(this.title, componentIndex++);

        this.resultComponent = new ResultComponent(new Dimension(dimension.width - 40, dimension.height - 200));
        this.resultComponent.setLocation(20, 100);
        this.resultComponent.setVisible(false);
        this.add(this.resultComponent, componentIndex++);

        this.paymentComponent = new PaymentComponent(new Dimension(dimension.width - 40 - 300 - 10, 250));
        this.paymentComponent.setLocation(330, 100);
        this.add(this.paymentComponent, componentIndex++);

        this.insuranceComponent = new InsuranceComponent(new Dimension(dimension.width - 40 - 300 - 10 - 300, 180));
        this.insuranceComponent.setLocation(330, 360);
        this.add(this.insuranceComponent, componentIndex++);

        this.roadTaxComponent = new RoadTaxComponent(new Dimension(290, 180));
        this.roadTaxComponent.setLocation(dimension.width - 310, 360);
        this.add(this.roadTaxComponent, componentIndex++);

        this.okButton.setSize(150, 48);
        this.okButton.setLocation(dimension.width - 150, dimension.height - 50);
        this.okButton.addActionListener((event) -> {
            this.contentPresenter.onPurchase(
                    this.paymentComponent.payment.getText(),
                    this.paymentComponent.reg.getText(),
                    this.paymentComponent.contactFile,
                    this.insuranceComponent.policy.getText(),
                    this.insuranceComponent.premium.getText(),
                    this.roadTaxComponent.annual.getText()
            );
        });
        this.add(this.okButton, componentIndex++);

        this.cancelButton.setSize(150, 48);
        this.cancelButton.setLocation(dimension.width - 320, dimension.height - 50);
        this.cancelButton.addActionListener((event) -> {
            this.contentPresenter.onGotoMain();
        });
        this.add(this.cancelButton, componentIndex++);
    }

    public void display(int index) {
        this.okButton.setVisible(true);
        this.resultComponent.setVisible(false);
        int componentIndex = 3;
        if (index == 0) {
            this.title.setText("XSR900");

            this.card = new BikeCard(
                    Toolkit.getDefaultToolkit().getImage(
                            Thread.currentThread().getContextClassLoader().getResource("bikes/XSR900.jpg")
                    ),
                    "XSR900", "1.6 BTC"
            );
            this.card.setLocation(20, 100);
            this.add(this.card, componentIndex++);
        } else if (index == 1) {
            this.title.setText("XSR700");

            this.card = new BikeCard(
                    Toolkit.getDefaultToolkit().getImage(
                            Thread.currentThread().getContextClassLoader().getResource("bikes/XSR700.jpg")
                    ),
                    "XSR700", "1.2 BTC"
            );
            this.card.setLocation(20, 100);
            this.add(this.card, componentIndex++);
        } else if (index == 2) {
            this.title.setText("Z900RS");

            this.card = new BikeCard(
                    Toolkit.getDefaultToolkit().getImage(
                            Thread.currentThread().getContextClassLoader().getResource("bikes/Z900RS.jpg")
                    ),
                    "Z900RS", "2 BTC"
            );
            this.card.setLocation(20, 100);
            this.add(this.card, componentIndex++);
        }
    }

    public void showTransaction(OverledgerTransactionResponse transaction) {
        this.okButton.setVisible(false);
        this.resultComponent.setVisible(true);
        this.resultComponent.txnId.setText(transaction.getOverledgerTransactionId().toString());
        transaction.getDltData().stream().forEach(dltTransaction -> {
            if ("bitcoin".equals(dltTransaction.getDlt())) {
                this.resultComponent.payment.setText(dltTransaction.getTransactionHash());
                this.resultComponent.paymentResult.setText(dltTransaction.getStatus().getStatus().name());
            } else if ("ethereum".equals(dltTransaction.getDlt())) {
                this.resultComponent.reg.setText(dltTransaction.getTransactionHash());
                this.resultComponent.regResult.setText(dltTransaction.getStatus().getStatus().name());
            } else if ("ripple".equals(dltTransaction.getDlt())) {
                this.resultComponent.tax.setText(dltTransaction.getTransactionHash());
                this.resultComponent.taxResult.setText(dltTransaction.getStatus().getStatus().name());
            }
        });
    }

}
