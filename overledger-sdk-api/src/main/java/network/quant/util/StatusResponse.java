package network.quant.util;


import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class StatusResponse {

    private UUID overledgerTransactionId;
    private String mappId;
    private Instant timestamp;
    private Instant createdAt;
    private Instant updatedAt;
    private Status status;
    private List<Status> statusHistory;
    private List<String> dltList;

    public StatusResponse(){}
    public StatusResponse(UUID overledgerTransactionId, String mappId, Instant timestamp, Instant createdAt, Instant updatedAt, Status status, List<Status> statusHistory, List<String> dltList) {
        this.overledgerTransactionId = overledgerTransactionId;
        this.mappId = mappId;
        this.timestamp = timestamp;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.statusHistory = statusHistory;
        this.dltList = dltList;
    }

    public UUID getOverledgerTransactionId() {
        return overledgerTransactionId;
    }

    public void setOverledgerTransactionId(UUID overledgerTransactionId) {
        this.overledgerTransactionId = overledgerTransactionId;
    }

    public String getMappId() {
        return mappId;
    }

    public void setMappId(String mappId) {
        this.mappId = mappId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Status> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(List<Status> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public List<String> getDltList() {
        return dltList;
    }

    public void setDltList(List<String> dltList) {
        this.dltList = dltList;
    }
}
