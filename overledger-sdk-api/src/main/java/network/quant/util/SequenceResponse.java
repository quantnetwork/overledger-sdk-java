package network.quant.util;

import java.util.List;

public class SequenceResponse {

    List<DltSequenceResponse> dltData;

    public SequenceResponse(List<DltSequenceResponse> dltData) {
        this.dltData = dltData;
    }

    public List<DltSequenceResponse> getDltData() {
        return dltData;
    }

}
