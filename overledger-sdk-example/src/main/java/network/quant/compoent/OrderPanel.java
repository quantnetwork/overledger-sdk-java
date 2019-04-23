package network.quant.compoent;

import network.quant.api.DLT;
import network.quant.api.OverledgerTransaction;
import network.quant.api.OverledgerTransactions;
import network.quant.essential.dto.DltTransactionResponse;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.mvp.presenter.ContentPresenter;
import network.quant.util.Page;
import network.quant.util.PageParams;
import network.quant.utils.UITools;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderPanel extends BaseComponent {

    @FieldDefaults(level = AccessLevel.PRIVATE)
    private class CellComponent extends BaseComponent {

        OverledgerTransactionResponse value;
        int index;
        boolean isSelected;
        JLabel label;

        public CellComponent(JList<? extends OverledgerTransactionResponse> list, OverledgerTransactionResponse value, int index, boolean isSelected) {
            super(new Dimension(list.getSize().width - 20, 100));
            this.value = value;
            this.index = index;
            this.isSelected = isSelected;
            this.label = new JLabel();
        }

        @Override
        protected void paintComponent(Graphics2D g2D) {
            g2D.setColor(Color.WHITE);
            g2D.fillRoundRect(10, 5, this.getSize().width - 20, 90, 8, 8);

            this.label.setForeground(TEXT);
            this.label.setFont(UITools.getFont(Font.PLAIN, 24));
            this.label.setText(this.value.getOverledgerTransactionId().toString());
            this.label.setSize(this.getSize().width - 40, 32);
            this.label.paint(g2D.create(20, 10, this.getSize().width - 40, 32));

            this.label.setFont(UITools.getFont(Font.PLAIN, 16));
            this.label.setText(this.value.getTimestamp());
            this.label.setHorizontalAlignment(SwingConstants.RIGHT);
            this.label.setSize(this.getSize().width - 40, 32);
            this.label.paint(g2D.create(20, 10, this.getSize().width - 40, 32));

            this.label.setFont(UITools.getFont(Font.PLAIN, 16));
            this.label.setHorizontalAlignment(SwingConstants.LEFT);
            this.label.setSize(300, 32);
            for (int i=0; i<this.value.getDltData().size(); i++) {
                DltTransactionResponse dltTransactionResponse = this.value.getDltData().get(i);
                this.label.setText(dltTransactionResponse.getStatus().getStatus().name());
                this.label.paint(g2D.create(i * 300 + 60, 50, 200, 32));
                g2D.drawImage(DLT_ICONS.get(dltTransactionResponse.getDlt()), i * 300 + 20, 50, this);
            }
        }

    }

    private static final Color TEXT = new Color(38, 31, 99);
    private static Map<String, Image> DLT_ICONS = new HashMap<>();
    ContentPresenter contentPresenter;
    JList<OverledgerTransactionResponse> transactionList = new JList<>();
    JScrollPane transactionPane;
    DefaultListModel<OverledgerTransactionResponse> transactionListModel = new DefaultListModel<>();
    PageParams page;
    JLabel pageLabel = new JLabel();
    network.quant.compoent.Button nextButton = new network.quant.compoent.Button(">", network.quant.compoent.Button.TYPE.OK);
    network.quant.compoent.Button previousButton = new network.quant.compoent.Button("<", Button.TYPE.OK);
    network.quant.compoent.Button okButton = new network.quant.compoent.Button("OK", network.quant.compoent.Button.TYPE.OK);

    static {
        DLT_ICONS.put(DLT.bitcoin.name(), Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource(String.format("%s.png", DLT.bitcoin.name()))
        ).getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        DLT_ICONS.put(DLT.ethereum.name(), Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource(String.format("%s.png", DLT.ethereum.name()))
        ).getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        DLT_ICONS.put(DLT.ripple.name(), Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource(String.format("%s.png", DLT.ripple.name()))
        ).getScaledInstance(32, 32, Image.SCALE_SMOOTH));
    }


    public OrderPanel(Dimension dimension, ContentPresenter contentPresenter) {
        super(dimension);
        this.contentPresenter = contentPresenter;

        int componentIndex = 0;

        JLabel label = new JLabel("Order");
        label.setSize(dimension.width - 20, 64);
        label.setLocation(10, 10);
        label.setFont(UITools.getFont(Font.PLAIN, 48));
        label.setForeground(TEXT);
        this.add(label, componentIndex++);

        this.pageLabel.setSize(200, 64);
        this.pageLabel.setLocation(dimension.width - 260, 10);
        this.pageLabel.setFont(UITools.getFont(Font.PLAIN, 16));
        this.pageLabel.setForeground(TEXT);
        this.pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.pageLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.add(this.pageLabel, componentIndex++);

        this.nextButton.setSize(48, 48);
        this.nextButton.setLocation(dimension.width - 60, 10);
        this.nextButton.addActionListener((event) -> {
            this.page.setOffset(this.page.getOffset() + 1);
            this.contentPresenter.onLoadOrders(this.page);
        });
        this.add(this.nextButton, componentIndex++);

        this.previousButton.setSize(48, 48);
        this.previousButton.setLocation(dimension.width - 320, 10);
        this.previousButton.addActionListener((event) -> {
            this.page.setOffset(this.page.getOffset() - 1);
            this.contentPresenter.onLoadOrders(this.page);
        });
        this.add(this.previousButton, componentIndex++);

        this.transactionList.setOpaque(false);
        this.transactionList.setModel(this.transactionListModel);
        this.transactionList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> new CellComponent(list, value, index, isSelected));
        this.transactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.transactionPane = new JScrollPane(this.transactionList);
        this.transactionPane.setOpaque(false);
        this.transactionPane.getViewport().setOpaque(false);
        this.transactionPane.setBorder(BorderFactory.createEmptyBorder());
        this.transactionPane.setLocation(10, 70);
        this.transactionPane.setSize(new Dimension(dimension.width - 20, dimension.height - 150));
        this.transactionPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.transactionPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.add(this.transactionPane, componentIndex++);

        this.okButton.setSize(150, 48);
        this.okButton.setLocation(dimension.width - 150, dimension.height - 50);
        this.okButton.addActionListener((event) -> {
            this.contentPresenter.onGotoMain();
        });
        this.add(this.okButton, componentIndex++);
    }

    public void loadArray(OverledgerTransactionResponse[] writeOverledgerTransactionResponses) {
        Stream.of(writeOverledgerTransactionResponses).forEach(overledgerTransactionResponse ->
                this.transactionListModel.addElement(overledgerTransactionResponse)
        );
    }

    public void loadList(OverledgerTransactions overledgerTransactionsResponse, PageParams page) {
        this.page = page;
        this.pageLabel.setText(String.format("Page: %d of %d", page.getOffset()/page.getLength() + 1, page.getOffset()%page.getLength()));
        // this.nextButton.setVisible(!page.isLast());
        // this.previousButton.setVisible(!page.isFirst());
        this.transactionListModel.clear();
        overledgerTransactionsResponse.getTransactions().stream().forEach(overledgerTransactionResponse ->
                this.transactionListModel.addElement((OverledgerTransactionResponse)overledgerTransactionResponse)
        );
    }

}
