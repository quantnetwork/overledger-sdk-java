package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.ContractInputTypeOptions;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;

import java.util.Objects;

@Slf4j
public class ContractArgumentToAbiMainFactory {


    /**
     List<Bool> boolList = new ArrayList<>();
     boolList.add(new Bool(true));
     boolList.add(new Bool(false));
     boolList.add(new Bool(true));
     DynamicArray<Bool> boolArray = new DynamicArray<>(Bool.class, boolList);


     String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new Bool(true),
     new Int256(5),
     new Uint16(33),
     new Utf8String("Hello"),
     new Address("0x650A87cfB9165C9F4Ccc7B971D971f50f753e761"),
     new Utf8String("Hi_there!"),
     boolArray));
     */

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
