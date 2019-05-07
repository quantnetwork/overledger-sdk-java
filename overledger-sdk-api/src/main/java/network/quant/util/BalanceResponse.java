package network.quant.util;

import java.math.BigDecimal;

public class BalanceResponse {

    String dlt;
    String address;
    String unit;
    BigDecimal value;
    String code;
    String message;

    public BalanceResponse() {
    }

    public BalanceResponse(String dlt, String address, String unit, BigDecimal value) {
        this.dlt = dlt;
        this.address = address;
        this.unit = unit;
        this.value = value;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
