package network.quant.util;

import network.quant.api.DLT;

public class DltSequenceResponse {

    private Number sequence;
    private DLT dlt;

    public DltSequenceResponse(Number sequence, DLT dlt) {
        this.sequence = sequence;
        this.dlt = dlt;
    }

    public Number getSequence() {
        return sequence;
    }

    public DLT getDlt() {
        return dlt;
    }

}
