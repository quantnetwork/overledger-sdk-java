package network.quant.bitcoin;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.quant.api.DLT;
import network.quant.bitcoin.model.BitcoinBlockData;
import network.quant.util.Block;

import java.io.IOException;

public class BitcoinBlock {

    private BitcoinBlockData data;

    private BitcoinBlock(Object data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.data = objectMapper.readValue(data.toString(), BitcoinBlockData.class);
    }

    public BitcoinBlockData getData() {
        return this.data;
    }

    public static BitcoinBlock getInstance(Block block) {
        if (null != block && DLT.bitcoin.equals(block.getDlt())) {
            try {
                return new BitcoinBlock(block.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
