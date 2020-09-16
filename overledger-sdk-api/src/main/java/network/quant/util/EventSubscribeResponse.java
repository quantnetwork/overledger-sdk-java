package network.quant.util;

import java.io.Serializable;

public class EventSubscribeResponse<T> implements Serializable {
    private T payload;

    public EventSubscribeResponse() {
    }

    public EventSubscribeResponse(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
