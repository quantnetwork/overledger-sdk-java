package network.quant.compoent;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import network.quant.utils.UITools;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultComponent extends DetailComponent {

    JLabel txnLabel = new JLabel("Transaction ID");
    JLabel paymentLabel = new JLabel("Payment Reference");
    JLabel regLabel = new JLabel("Reg Reference");
    JLabel taxLabel = new JLabel("Tax Reference");
    public JTextField txnId = new JTextField("N/A");
    public JTextField payment = new JTextField("N/A");
    public JTextField reg = new JTextField("N/A");
    public JTextField tax = new JTextField("N/A");
    public JLabel paymentResult = new JLabel("Unknown");
    public JLabel regResult = new JLabel("Unknown");
    public JLabel taxResult = new JLabel("Unknown");

    public ResultComponent(Dimension dimension) {
        super(dimension);
        this.title.setText("Result");

        int componentIndex = 1;

        this.txnLabel.setSize(200, 32);
        this.txnLabel.setLocation(10, 50);
        this.txnLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.txnLabel.setForeground(TEXT);
        this.txnLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.txnLabel, componentIndex++);

        this.txnId.setSize(450, 32);
        this.txnId.setLocation(260, 50);
        this.txnId.setFont(UITools.getFont(Font.PLAIN, 16));
        this.txnId.setForeground(TEXT);
        this.add(this.txnId, componentIndex++);

        this.paymentLabel.setSize(200, 32);
        this.paymentLabel.setLocation(10, 100);
        this.paymentLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.paymentLabel.setForeground(TEXT);
        this.paymentLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.paymentLabel, componentIndex++);

        this.payment.setSize(450, 32);
        this.payment.setLocation(260, 100);
        this.payment.setFont(UITools.getFont(Font.PLAIN, 16));
        this.payment.setForeground(TEXT);
        this.add(this.payment, componentIndex++);

        this.paymentResult.setSize(200, 32);
        this.paymentResult.setLocation(700, 100);
        this.paymentResult.setFont(UITools.getFont(Font.BOLD, 16));
        this.paymentResult.setForeground(TEXT);
        this.paymentResult.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.paymentResult, componentIndex++);

        this.regLabel.setSize(200, 32);
        this.regLabel.setLocation(10, 150);
        this.regLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.regLabel.setForeground(TEXT);
        this.regLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.regLabel, componentIndex++);

        this.reg.setSize(450, 32);
        this.reg.setLocation(260, 150);
        this.reg.setFont(UITools.getFont(Font.PLAIN, 16));
        this.reg.setForeground(TEXT);
        this.add(this.reg, componentIndex++);

        this.regResult.setSize(200, 32);
        this.regResult.setLocation(700, 150);
        this.regResult.setFont(UITools.getFont(Font.BOLD, 16));
        this.regResult.setForeground(TEXT);
        this.regResult.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.regResult, componentIndex++);

        this.taxLabel.setSize(200, 32);
        this.taxLabel.setLocation(10, 200);
        this.taxLabel.setFont(UITools.getFont(Font.BOLD, 16));
        this.taxLabel.setForeground(TEXT);
        this.taxLabel.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.taxLabel, componentIndex++);

        this.tax.setSize(450, 32);
        this.tax.setLocation(260, 200);
        this.tax.setFont(UITools.getFont(Font.PLAIN, 16));
        this.tax.setForeground(TEXT);
        this.add(this.tax, componentIndex++);

        this.taxResult.setSize(200, 32);
        this.taxResult.setLocation(700, 200);
        this.taxResult.setFont(UITools.getFont(Font.BOLD, 16));
        this.taxResult.setForeground(TEXT);
        this.taxResult.setHorizontalAlignment(JLabel.RIGHT);
        this.add(this.taxResult, componentIndex++);
    }

    @Override
    protected void paintComponent(Graphics2D g2D) {
        super.paintComponent(g2D);
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, 0, this.getSize().width, this.getSize().height);
    }
}
