package network.quant.util;

import network.quant.api.STATUS;

import java.io.Serializable;
import java.time.Instant;

public class Status implements Serializable {

    private STATUS status;
    private String code;
    private String message;
    private Instant timestamp;

    public Status() {
    }

    public Status(STATUS status, String code, String message, Instant timestamp) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) { this.status = status; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public Instant getTimestamp() { return timestamp; }

    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
