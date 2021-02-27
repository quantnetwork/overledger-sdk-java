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
        } else if(contractArgument.getSelectedArrayLength() == 19) {
            StaticArray19 <Bool> result = new StaticArray19<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 20) {
            StaticArray20 <Bool> result = new StaticArray20<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 21) {
            StaticArray21 <Bool> result = new StaticArray21<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 22) {
            StaticArray22 <Bool> result = new StaticArray22<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 23) {
            StaticArray23 <Bool> result = new StaticArray23<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 24) {
            StaticArray24 <Bool> result = new StaticArray24<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 25) {
            StaticArray25 <Bool> result = new StaticArray25<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 26) {
            StaticArray26 <Bool> result = new StaticArray26<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 27) {
            StaticArray27 <Bool> result = new StaticArray27<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 28) {
            StaticArray28 <Bool> result = new StaticArray28<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 29) {
            StaticArray29 <Bool> result = new StaticArray29<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 30) {
            StaticArray30 <Bool> result = new StaticArray30<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 31) {
            StaticArray31 <Bool> result = new StaticArray31<>( Bool.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 32) {
            StaticArray32 <Bool> result = new StaticArray32<>( Bool.class, values);
            return result;
        }

        else {
           log.error("Unsupported length of the array, please check! " + contractArgument.getSelectedArrayLength());
           return null;
        }
    }

}