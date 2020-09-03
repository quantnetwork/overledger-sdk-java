package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.Type;


import java.math.BigInteger;

/**
 * Class to convert the many different Uint classes using CustomUint, so that i can convert the many variations of Arrays also
 * @author R Bahari
 */
@Slf4j
public class ContractArgumentUIntToAbiUIntFactory {
    @Nullable
    public static Type convertUInt(ContractArgument contractArgument) {

        return new CustomUInt(Integer.parseInt(contractArgument.getSelectedIntegerLength().label), new BigInteger(contractArgument.getValue()));

    }
}