package network.quant.ethereum.experimental;

import org.web3j.abi.datatypes.Int;

import java.math.BigInteger;

public class CustomInt extends Int {

    public CustomInt(int bitSize, BigInteger value) {
        super(bitSize, value);
    }
}
