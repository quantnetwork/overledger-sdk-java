package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.ContractInputTypeOptions;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;

import java.util.Objects;

/**
 * Main factory class to generate the different Abi types used for the creating of Smart Contract
 * The encodeConstructor method requires instantiation of those Type objects and when we pass from the SDK level we want to retain the ContractArgument object
 * and then determine what should be produced afterwards
 * @author R Bahari
 */
@Slf4j
public class ContractArgumentToAbiMainFactory {


    public static Type convertContractArgument(ContractArgument contractArgument) {

        Objects.requireNonNull(contractArgument, "Please supply contractArgument parameter");

        if(contractArgument.getType() == ContractInputTypeOptions.STRING) {
            return new Utf8String(contractArgument.getValue());
        }

        if(contractArgument.getType() == ContractInputTypeOptions.ADDRESS) {
            return new Address(contractArgument.getValue());
        }

        if(contractArgument.getType() == ContractInputTypeOptions.BOOL) {
            return new Bool(Boolean.parseBoolean(contractArgument.getValue()));
        }

        if(contractArgument.getType() == ContractInputTypeOptions.INT) {
            return ContractArgumentIntToAbiIntFactory.convertInt(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.UINT) {
            return ContractArgumentUIntToAbiUIntFactory.convertUInt(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.INT_ARRAY) {
            return ContractArgumentIntArrayToAbiIntArrayFactory.convertIntArray(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.UINT_ARRAY) {
            return ContractArgumentUIntArrayToAbiUIntArrayFactory.convertUIntArray(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.ADDRESS_ARRAY) {
            return ContractArgumentAddressArrayToAbiAddressArrayFactory.convertAddressArray(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.BOOLEAN_ARRAY) {
            return ContractArgumentBoolArrayToAbiBoolArrayFactory.convertBoolArray(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.BYTES) {
            return new CustomBytes(Integer.parseInt(contractArgument.getSelectedBytesLength().label), contractArgument.getValue().getBytes());
        }

        if(contractArgument.getType() == ContractInputTypeOptions.BYTES_B_ARRAY) {
            return ContractArgumentBytesArrayToAbiBytesArrayFactory.convertBytesArray(contractArgument);
        }


        log.error("Unsupported contract argument, please check if the type/length is supported!");
        return null;
    }

}
