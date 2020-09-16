package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.ContractInputTypeOptions;
import org.jetbrains.annotations.NotNull;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.utils.Numeric;

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
                temp = padHexString(contractArgument);

                return new CustomBytes(Integer.parseInt(contractArgument.getSelectedBytesLength().label), Numeric.hexStringToByteArray(temp.toString()));

            } else {
                byte[] resultArray = padNonHex(contractArgument);

                return new CustomBytes(Integer.parseInt(contractArgument.getSelectedBytesLength().label), resultArray);
            }
        }

        if(contractArgument.getType() == ContractInputTypeOptions.BYTES_B_ARRAY) {
            return ContractArgumentBytesArrayToAbiBytesArrayFactory.convertBytesArray(contractArgument);
        }


        log.error("Unsupported contract argument, please check if the type/length is supported!");
        return null;
    }

    public static TypeReference generateTypeReference(ContractArgument contractArgument){

        if(contractArgument.getType() == ContractInputTypeOptions.ADDRESS_ARRAY) {
            return ContractArgumentAddressArrayToAbiAddressArrayFactory.makeTypeReferenceFromAddressArray(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.INT_ARRAY) {
            return ContractArgumentIntArrayToAbiIntArrayFactory.makeTypeReferenceFromIntArray(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.UINT_ARRAY) {
            return ContractArgumentUIntArrayToAbiUIntArrayFactory.makeTypeReferenceFromUintArray(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.BOOLEAN_ARRAY) {
            return ContractArgumentBoolArrayToAbiBoolArrayFactory.makeTypeReferenceFromBoolArray(contractArgument);
        }

        if(contractArgument.getType() == ContractInputTypeOptions.BYTES_B_ARRAY) {
            return ContractArgumentBytesArrayToAbiBytesArrayFactory.makeTypeReferenceFromByteArray(contractArgument);
        }

        log.error("Unsupported contract argument, please check if the type/length is supported!");
        return null;
    }

    private static byte[] padNonHex(ContractArgument contractArgument) {
        //for example: "hello".getBytes would give as byte[5], but if we want to pass BYTE32, we would need byte[32] with the first 5 elements being from .getBytes one
        //we should create a byte array with 32 elements and zeros as values, then put the 5 from above into this array
        byte[] inputArray = contractArgument.getValue().getBytes();
        int totalDigitsRequired = Integer.parseInt(contractArgument.getSelectedBytesLength().label); // e.g. if we want BYTE32
        int howManyMoreDoWeNeed = totalDigitsRequired - inputArray.length;

        byte[] resultArray = new byte[totalDigitsRequired];
        for(int a = 0; a < howManyMoreDoWeNeed; a++) {
            resultArray[a] = 0;
        }
        for(int a = 0; a < inputArray.length; a++) {
            resultArray[a] = inputArray[a];
        }
        return resultArray;
    }

    @NotNull
    private static StringBuffer padHexString(ContractArgument contractArgument) {
        StringBuffer temp;//if starts with 0x, pad the zeros first to make up the length to be the what the selected length on contract argument
        int numberOfCharactersInValue = contractArgument.getValue().substring(2).length(); //don't count the 0x
        int totalDigitsRequired = Integer.parseInt(contractArgument.getSelectedBytesLength().label) * 2; // e.g. if we want BYTE32, we want total digit to be 64
        int howManyMoreDoWeNeed = totalDigitsRequired - numberOfCharactersInValue;
        temp = new StringBuffer(totalDigitsRequired);
        temp.append(contractArgument.getValue());
        for(int a = 0; a < howManyMoreDoWeNeed; a++) {
            temp.append("0");
        }
        return temp;
    }

}
