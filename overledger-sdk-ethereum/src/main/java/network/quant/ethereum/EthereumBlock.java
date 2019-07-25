package network.quant.ethereum;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.quant.api.DLT;
import network.quant.ethereum.model.EthereumBlockData;
import network.quant.util.Block;
import java.io.IOException;

public class EthereumBlock {

    private EthereumBlockData data;

    private EthereumBlock(Object data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.data = objectMapper.readValue(data.toString(), EthereumBlockData.class);
    }

    public EthereumBlockData getData() {
        return this.data;
    }

    public static EthereumBlock getInstance(Block block) {
        if (null != block && DLT.ethereum.equals(block.getDlt())) {
            try {
                return new EthereumBlock(block.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
