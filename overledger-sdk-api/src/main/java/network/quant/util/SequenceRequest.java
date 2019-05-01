package network.quant.util;

import java.util.List;

public class SequenceRequest {

    List<DltSequenceRequest> dltData;

    public SequenceRequest(List<DltSequenceRequest> dltData) {
        this.dltData = dltData;
    }

    public List<DltSequenceRequest> getDltData() {
        return dltData;
    }

}
