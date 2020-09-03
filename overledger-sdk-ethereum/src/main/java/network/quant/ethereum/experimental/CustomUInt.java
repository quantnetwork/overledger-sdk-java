package network.quant.ethereum.experimental;


import org.web3j.abi.datatypes.Int;
import org.web3j.abi.datatypes.Uint;

import java.math.BigInteger;

public class CustomUInt extends Uint {

    public CustomUInt(int bitSize, BigInteger value) {

        super(bitSize, value);
    }
}
