package network.quant.ethereum.experimental.dto;

public enum  EthereumUintIntOptions {
    B8("8"),
    B16("16"),
    B32("32"),
    B40("40"),
    B48("48"),
    B56("56"),
    B64("64"),
    B72("72"),
    B80("80"),
    B88("88"),
    B96("96"),
    B104("104"),
    B112("112"),
    B120("120"),
    B128("128"),
    B136("136"),
    B144("144"),
    B152("152"),
    B160("160"),
    B168("168"),
    B176("176"),
    B184("184"),
    B192("192"),
    B200("200"),
    B208("208"),
    B216("216"),
    B224("224"),
    B232("232"),
    B240("240"),
    B248("248"),
    B256("256");

    public final String label;

    private EthereumUintIntOptions(String label) {
        this.label = label;
    }
}
