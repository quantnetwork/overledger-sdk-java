package network.quant.util;

/**
 * DLT and Address pair.
 */
public class DLTAndAddress {

    String dlt; // - The distributed ledger technology.
    String address; // - The address on the respective dlt network.

    public DLTAndAddress() {
    }

    public DLTAndAddress(String dlt, String address) {
        this.dlt = dlt;
        this.address = address;
    }

    public String getDlt() {
        return dlt;
    }

    public void setDlt(String dlt) {
        this.dlt = dlt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
