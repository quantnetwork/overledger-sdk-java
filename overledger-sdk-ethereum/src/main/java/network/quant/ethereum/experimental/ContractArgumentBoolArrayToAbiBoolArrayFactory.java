package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.EthereumUintIntOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to process array of Bool
 * @author R Bahari
 */
@Slf4j
public class ContractArgumentBoolArrayToAbiBoolArrayFactory {
    @Nullable
    public static Type convertBoolArray(ContractArgument contractArgument) {
        
        List<Bool> values = produceListForInput(contractArgument, contractArgument.getSelectedIntegerLength());

        //now just need to detect the different Array lengths
        return generateArray(contractArgument, values);

    }

    @NotNull
    public static List<Bool> produceListForInput(ContractArgument contractArgument, EthereumUintIntOptions ethereumUintIntOptions) {

        Stream.of(contractArgument.getValue().replace("{","").replace("}","").split(",")).forEach(s -> log.info(s));
        return Stream.of(contractArgument.getValue().replace("{","").replace("}","").split(","))
                        .map(s -> {
                            log.debug("current s = " + s.trim());
                            return new Bool(Boolean.parseBoolean(s.trim()));})
                        .collect(Collectors.toList());
    }

    @NotNull
    public static Type generateArray(ContractArgument contractArgument, List<Bool> values) {

        if(contractArgument.getSelectedArrayLength() == null ) {
            /**
             * I am going to assume length is not set, then its a dynamic array
             */
            DynamicArray<Bool> result = new DynamicArray<>( Bool.class, values);
            return result;
        }

        if(contractArgument.getSelectedArrayLength() == 1) {
            StaticArray1<Bool> result = new StaticArray1<>(Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 2) {
            StaticArray2<Bool> result = new StaticArray2<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 3) {
            StaticArray3<Bool> result = new StaticArray3<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 4) {
            StaticArray4<Bool> result = new StaticArray4<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 5) {
            StaticArray5<Bool> result = new StaticArray5<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 6) {
            StaticArray6 <Bool> result = new StaticArray6<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 7) {
            StaticArray7 <Bool> result = new StaticArray7<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 8) {
            StaticArray8 <Bool> result = new StaticArray8<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 9) {
            StaticArray9 <Bool> result = new StaticArray9<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 10) {
            StaticArray10 <Bool> result = new StaticArray10<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 11) {
            StaticArray11 <Bool> result = new StaticArray11<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 12) {
            StaticArray12 <Bool> result = new StaticArray12<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 13) {
            StaticArray13 <Bool> result = new StaticArray13<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 14) {
            StaticArray14 <Bool> result = new StaticArray14<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 15) {
            StaticArray15 <Bool> result = new StaticArray15<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 16) {
            StaticArray16 <Bool> result = new StaticArray16<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 17) {
            StaticArray17 <Bool> result = new StaticArray17<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 18) {
            StaticArray18 <Bool> result = new StaticArray18<>( Bool.class, values);
            return result;
        }

        else {
           log.error("Unsupported length of the array, please check! " + contractArgument.getSelectedArrayLength());
           return null;
        }
    }

}