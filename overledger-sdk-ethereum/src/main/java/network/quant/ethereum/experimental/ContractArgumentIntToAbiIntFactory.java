package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.EthereumUintIntOptions;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.*;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Class to convert the Int8, Int16 and so on, i am using the CustomInt which extends from Int and pass the biteSize
 * @author R Bahari
 */
@Slf4j
public class ContractArgumentIntToAbiIntFactory {
    @Nullable
    public static Type convertInt(ContractArgument contractArgument) {

        return new CustomInt(Integer.parseInt(contractArgument.getSelectedIntegerLength().label), new BigInteger(contractArgument.getValue()));

    }
}