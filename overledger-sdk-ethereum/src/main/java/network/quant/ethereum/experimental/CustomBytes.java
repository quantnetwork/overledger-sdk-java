package network.quant.ethereum.experimental;

import org.web3j.abi.datatypes.Bytes;
import org.web3j.abi.datatypes.Int;

import java.math.BigInteger;

public class CustomBytes extends Bytes {

    public CustomBytes(int bitSize, byte[] value) {
        super(bitSize, value);
    }
}
