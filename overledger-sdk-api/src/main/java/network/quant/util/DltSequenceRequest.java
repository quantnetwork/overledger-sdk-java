package network.quant.util;

import network.quant.api.DLT;

public class DltSequenceRequest {

    private DLT dlt;
    private String address;

    public DltSequenceRequest(DLT dlt, String address) {
        this.dlt = dlt;
        this.address = address;
    }

    public DLT getDlt() {
        return dlt;
    }

    public String getAddress() {
        return address;
    }

}
