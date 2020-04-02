package network.quant.util;

import java.util.List;

public class SequenceRequest {

    List<DLTAndAddress> dltData;

    public SequenceRequest(List<DLTAndAddress> dltData) {
        this.dltData = dltData;
    }

    public List<DLTAndAddress> getDltData() {
        return dltData;
    }

}
