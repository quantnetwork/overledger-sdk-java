package network.quant.util;

public class BalanceRequest {

    String dlt;
    String address;

    public BalanceRequest() {
    }

    public BalanceRequest(String dlt, String address) {
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
