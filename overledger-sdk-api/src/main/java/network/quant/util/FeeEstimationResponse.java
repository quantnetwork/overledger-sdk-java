package network.quant.util;


public class FeeEstimationResponse {

    String dlt;
    Object data;

    public FeeEstimationResponse() {
        System.out.println("FeeEstimation empty constructor");
    }

    public FeeEstimationResponse(String dlt, Object data) {
        System.out.println("FeeEstimation response created");
        this.dlt = dlt;
        this.data = data;
    }

    public String getDlt() {
        return dlt;
    }

    public void setDlt(String dlt) {
        this.dlt = dlt;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data= data;
    }

}