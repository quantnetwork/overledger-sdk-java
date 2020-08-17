package network.quant.ethereum;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.exception.FailToParseFunctionParameterException;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.ContractInputTypeOptions;
import network.quant.ethereum.experimental.dto.EthereumBytesOptions;
import network.quant.ethereum.experimental.dto.EthereumUintIntOptions;
import org.web3j.abi.datatypes.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EthereumUtil {

    public static final String INT_TYPE = "int";
    public static final String UINT_TYPE = "uint";
    public static final String BYTES_TYPE = "bytes";
    public static final String ARRAY_TYPE = "array";
    public static final String BOOL_TYPE = "bool";
    public static final String ADDRESS_TYPE = "address";
    public static final String K_ARRAY_LENGTH = "k";

    public static List<Object> extractValues(String values, Class<? extends Type> clazz) {
        values = values.replace("{", "").replace("}", "");
        return Arrays.stream(values.split(","))
                .map(input -> {
                    if (clazz.equals(Bool.class)) {
                        return Boolean.parseBoolean(input);
                    } else if (clazz.equals(Address.class)) {
                        return new Address(input);
                    } else if (clazz.equals(Int.class)) {
                        return new BigInteger(input);
                    }else if (clazz.equals(Bytes.class)){
                        return new byte[]{Byte.parseByte(input)};
                    }
                    return null;
                }).collect(Collectors.toUnmodifiableList());
    }

    public static List<Object> getValues(List<ContractArgument> inputs) {
        return inputs.stream().map(contractArgument -> {
            ContractInputTypeOptions type = contractArgument.getType();
            String typeAsString = type.label;
            try {
                if (type.name().toLowerCase().contains(ARRAY_TYPE)) {
                    if (typeAsString.contains(BOOL_TYPE))
                        return extractValues(contractArgument.getValue(), Bool.class);
                    else if (typeAsString.contains(ADDRESS_TYPE))
                        return extractValues(contractArgument.getValue(), Address.class);
                    else if (typeAsString.contains(INT_TYPE))
                        return extractValues(contractArgument.getValue(), Int.class);
                    else if (typeAsString.contains(BYTES_TYPE))
                        return extractValues(contractArgument.getValue(), Bytes.class);
                } else {
                    String value = contractArgument.getValue().replace("{", "").replace("}", "");
                    if (typeAsString.contains(BOOL_TYPE))
                        return Boolean.parseBoolean(value);
                    else if (typeAsString.contains(ADDRESS_TYPE))
                        return new Address(value);
                    else if (typeAsString.contains(INT_TYPE))
                        return new BigInteger(value);
                    else if (typeAsString.contains(BYTES_TYPE))
                        return new byte[]{Byte.parseByte(value)};
                }
                return null;
            } catch (Exception e) {
                log.error("exception: " + e);
                throw new FailToParseFunctionParameterException(e);
            }
        }).collect(Collectors.toUnmodifiableList());
    }


    public static List<String> getSolidityInputOutputTypes(List<ContractArgument> inputs) {
        return inputs.stream().map(contractArgument -> {
            String solidityInputType = "";
            ContractInputTypeOptions type = contractArgument.getType();
            String typeAsString = type.label;
            try {

                if (type.label.contains(INT_TYPE) || type.label.contains(UINT_TYPE))
                    solidityInputType = addIntegerLengthToInput(typeAsString, contractArgument.getSelectedIntegerLength());
                else if (typeAsString.contains(BYTES_TYPE))
                    solidityInputType = addByteOptionsToInput(typeAsString, contractArgument.getSelectedBytesLength());
                else
                    solidityInputType = typeAsString;

                if (type.name().toLowerCase().contains(ARRAY_TYPE)) {
                    solidityInputType = applyArrayLengthToInput(solidityInputType, contractArgument.getSelectedArrayLength());
                }
                return solidityInputType;
            } catch (Exception e) {
                log.error("exception: " + e);
                throw new FailToParseFunctionParameterException(e);
            }
        }).collect(Collectors.toUnmodifiableList());
    }

    public static String addIntegerLengthToInput(String intUintSolidityInputType, EthereumUintIntOptions selectedIntegerLength) {
        return intUintSolidityInputType.replace(INT_TYPE, INT_TYPE.concat(selectedIntegerLength.label));
    }

    public static String addByteOptionsToInput(String intUintSolidityInputType, EthereumBytesOptions bytesOptions) {
        return intUintSolidityInputType.replace(BYTES_TYPE, BYTES_TYPE.concat(bytesOptions.label));
    }

    public static String applyArrayLengthToInput(String intUintSolidityInputType, Long selectedArrayLength) {
        return intUintSolidityInputType.replace(K_ARRAY_LENGTH, selectedArrayLength.toString());
    }
}
