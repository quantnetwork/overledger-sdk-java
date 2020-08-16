package network.quant.ethereum;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.exception.FailToParseFunctionParameterException;
import network.quant.ethereum.experimental.dto.ContractArgument;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.abi.datatypes.Type;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EthereumUtil {

    public static List<Type> getTypes(List<ContractArgument> inputs) {
        return inputs.stream().map(contractArgument -> {
            String typeAsString = contractArgument.getType().name().toLowerCase();
            Class<? extends Type> typeClass = AbiTypes.getType(typeAsString);
            try {
                Type type;
                if (typeAsString.indexOf("bool") > -1) {
                    log.debug("Contract service type is bool");
                    type = typeClass
                            .getConstructor(Boolean.class)
                            .newInstance(Boolean.valueOf(contractArgument.getValue()));
                } else if (typeAsString.indexOf("int") > -1) {
                    log.debug("Contract service type is int");
                    type = typeClass
                            .getConstructor(BigInteger.class)
                            .newInstance(new BigInteger(contractArgument.getValue()));
                } else if (typeAsString.indexOf("byte") > -1) {
                    log.debug("Contract service type is byte");
                    type = typeClass
                            .getConstructor(byte[].class)
                            .newInstance(Numeric.hexStringToByteArray(contractArgument.getValue()));
                } else {
                    log.debug("Contract service type is string");
                    type = typeClass
                            .getConstructor(String.class)
                            .newInstance(contractArgument.getValue());
                }
                return type;
            } catch (Exception e) {
                log.error("Failed to parse contract service.");
                throw new FailToParseFunctionParameterException(e);
            }
        }).collect(Collectors.toUnmodifiableList());
    }

    public static List<TypeReference<?>> getTypeReferences(List<ContractArgument> outputs) {
        return outputs.stream().map(contractArgument ->
                TypeReference.create(AbiTypes.getType(contractArgument.getType().name().toLowerCase())))
                .collect(Collectors.toUnmodifiableList());
    }
}
