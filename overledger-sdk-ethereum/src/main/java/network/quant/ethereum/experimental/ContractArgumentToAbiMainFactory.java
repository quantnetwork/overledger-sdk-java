package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.ContractInputTypeOptions;
import org.web3j.abi.datatypes.*;

import java.util.Objects;

/**
 * Main factory class to generate the different Abi types used for the creating of ETH Smart Contract
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

            //create a string with 000000 up to the selected bytes length, so if its 32, then we will have 32 zeros
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < Integer.parseInt(contractArgument.getSelectedBytesLength().label); i++) {
                sb.append('0');
            }

            StringBuffer temp;
            if(contractArgument.getValue().contains("0x")) { //moving the 0x to the beginning of the newly created String
                temp = new StringBuffer((sb.substring(contractArgument.getValue().length()) + contractArgument.getValue()).replace("0x", "00"));
                temp.replace(0, 2, "0x");
            } else {
                temp = new StringBuffer((sb.substring(contractArgument.getValue().length()) + contractArgument.getValue()));
            }
            return new CustomBytes(10, "68656c6c6f".getBytes());
            //return new CustomBytes(Integer.parseInt(contractArgument.getSelectedBytesLength().label), temp.toString().getBytes());
        }

        if(contractArgument.getType() == ContractInputTypeOptions.BYTES_B_ARRAY) {
            return ContractArgumentBytesArrayToAbiBytesArrayFactory.convertBytesArray(contractArgument);
        }


        log.error("Unsupported contract argument, please check if the type/length is supported!");
        return null;
    }

}
