package network.quant.util;

import java.io.Serializable;

public class StatusResponse<T> implements Serializable {

    private T payload;

    public StatusResponse() {
    }

    public StatusResponse(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    //this method is used for debugging purpose only.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append(" [payload=");
        if (this.payload instanceof byte[]) {
            sb.append("byte[").append(((byte[]) this.payload).length).append("]");
        } else {
            sb.append(this.payload);
        }
        return sb.toString();
    }
}
