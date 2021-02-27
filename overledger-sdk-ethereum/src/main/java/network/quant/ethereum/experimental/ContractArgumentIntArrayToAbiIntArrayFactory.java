package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.EthereumUintIntOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.*;


import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to process the different Int8, Int16, etc with variations of array length
 * @author R Bahari
 */
@Slf4j
public class ContractArgumentIntArrayToAbiIntArrayFactory {
    @Nullable
    public static Type convertIntArray(ContractArgument contractArgument) {

        //I am using this customInt extending from Int, so that i can pass the bitesize, otherwise I will need to repeat using Int8, Int16, etc till Int whatever
        List<CustomInt> values = produceListForInput(contractArgument, contractArgument.getSelectedIntegerLength());
        //now just need to detect the different Array lengths
        return generateArray(contractArgument, values);

    }

    @NotNull
    public static List<CustomInt> produceListForInput(ContractArgument contractArgument, EthereumUintIntOptions ethereumUintIntOptions) {

        return Stream.of(contractArgument.getValue().replace("{","").replace("}","").split(","))
                        .map(s -> new CustomInt(Integer.parseInt(ethereumUintIntOptions.label), new BigInteger(s)))
                        .collect(Collectors.toList());
    }

    @NotNull
    public static Type generateArray(ContractArgument contractArgument, List<CustomInt> values) {

        /**
         * I am going to assume if selectArrayLength is null, then its dynamic
         */
        if(contractArgument.getSelectedArrayLength() == null) {
            DynamicArray<CustomInt> result = new DynamicArray<>(CustomInt.class, values);
            return result;
        }


        if(contractArgument.getSelectedArrayLength() == 1) {
            StaticArray1<CustomInt> result = new StaticArray1<>(CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 2) {
            StaticArray2<CustomInt> result = new StaticArray2<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 3) {
            StaticArray3<CustomInt> result = new StaticArray3<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 4) {
            StaticArray4<CustomInt> result = new StaticArray4<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 5) {
            StaticArray5<CustomInt> result = new StaticArray5<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 6) {
            StaticArray6 <CustomInt> result = new StaticArray6<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 7) {
            StaticArray7 <CustomInt> result = new StaticArray7<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 8) {
            StaticArray8 <CustomInt> result = new StaticArray8<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 9) {
            StaticArray9 <CustomInt> result = new StaticArray9<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 10) {
            StaticArray10 <CustomInt> result = new StaticArray10<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 11) {
            StaticArray11 <CustomInt> result = new StaticArray11<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 12) {
            StaticArray12 <CustomInt> result = new StaticArray12<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 13) {
            StaticArray13 <CustomInt> result = new StaticArray13<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 14) {
            StaticArray14 <CustomInt> result = new StaticArray14<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 15) {
            StaticArray15 <CustomInt> result = new StaticArray15<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 16) {
            StaticArray16 <CustomInt> result = new StaticArray16<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 17) {
            StaticArray17 <CustomInt> result = new StaticArray17<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 18) {
            StaticArray18 <CustomInt> result = new StaticArray18<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 19) {
            StaticArray19 <CustomInt> result = new StaticArray19<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 20) {
            StaticArray20 <CustomInt> result = new StaticArray20<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 21) {
            StaticArray21 <CustomInt> result = new StaticArray21<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 22) {
            StaticArray22 <CustomInt> result = new StaticArray22<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 23) {
            StaticArray23 <CustomInt> result = new StaticArray23<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 24) {
            StaticArray24 <CustomInt> result = new StaticArray24<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 25) {
            StaticArray25 <CustomInt> result = new StaticArray25<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 26) {
            StaticArray26 <CustomInt> result = new StaticArray26<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 27) {
            StaticArray27 <CustomInt> result = new StaticArray27<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 28) {
            StaticArray28 <CustomInt> result = new StaticArray28<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 29) {
            StaticArray29 <CustomInt> result = new StaticArray29<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 30) {
            StaticArray30 <CustomInt> result = new StaticArray30<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 31) {
            StaticArray31 <CustomInt> result = new StaticArray31<>( CustomInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 32) {
            StaticArray32 <CustomInt> result = new StaticArray32<>( CustomInt.class, values);
            return result;
        }

        else {
            log.error("Unsupported array length, please check!" + contractArgument.getSelectedArrayLength());
            return null;
        }
    }

}