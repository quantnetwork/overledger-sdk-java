package network.quant.util;

import java.io.Serializable;

public class StatusResponse<T> implements Serializable {

    private T payload;
    //private MessageHeaders headers;

    public StatusResponse() {
    }

    public StatusResponse(T payload) {
        this.payload = payload;
    }
    /*    public StatusResponse(T payload) {
        this(payload, new MessageHeaders(null));
    }
    public StatusResponse(T payload, Map<String, Object> headers) {
        this(payload, new MessageHeaders(headers));
    }

    public StatusResponse(T payload, MessageHeaders headers) {
        Assert.notNull(payload, "Payload must not be null");
        Assert.notNull(headers, "MessageHeaders must not be null");
        this.payload = payload;
        //this.headers = headers;
    }*/


    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

/*    @Override
    public int hashCode() {
        // Using nullSafeHashCode for proper array hashCode handling
        return (ObjectUtils.nullSafeHashCode(this.payload) * 23  *//*this.headers.hashCode()*//*);
    }*/

/*    @Override
    public boolean equals( Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StatusResponse)) {
            return false;
        }
        StatusResponse<?> otherMsg = (StatusResponse<?>) other;
        // Using nullSafeEquals for proper array equals comparisons
        return (ObjectUtils.nullSafeEquals(this.payload, otherMsg.payload));
    }*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append(" [payload=");
        if (this.payload instanceof byte[]) {
            sb.append("byte[").append(((byte[]) this.payload).length).append("]");
        }
        else {
            sb.append(this.payload);
        }
        //sb.append(", headers=").append(this.headers).append("]");
        return sb.toString();
    }
}
