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
public class ContractArgumentUIntArrayToAbiUIntArrayFactory {
    @Nullable
    public static Type convertUIntArray(ContractArgument contractArgument) {
        
        //I am using this customInt extending from Int, so that i can pass the bitesize, otherwise I will need to repeat using Int8, Int16, etc till Int whatever
        List<CustomUInt> values = produceListForInput(contractArgument, contractArgument.getSelectedIntegerLength());
        //now just need to detect the different Array lengths
        return generateArray(contractArgument, values);

    }

    @NotNull
    public static List<CustomUInt> produceListForInput(ContractArgument contractArgument, EthereumUintIntOptions ethereumUintIntOptions) {
        
        return Stream.of(contractArgument.getValue().replace("{","").replace("}","").split(","))
                        .map(s -> new CustomUInt(Integer.parseInt(ethereumUintIntOptions.label), new BigInteger(s)))
                        .collect(Collectors.toList());
    }

    @NotNull
    public static Type generateArray(ContractArgument contractArgument, List <CustomUInt> values) {

        /**
         * I am going to assume if selectedArrayLength is null, then its dynamic array
         */
        if(contractArgument.getSelectedArrayLength() == null) {
            DynamicArray<CustomUInt> result = new DynamicArray<>(CustomUInt.class, values);
            return result;
        }


        if(contractArgument.getSelectedArrayLength() == 1) {
            StaticArray1 <CustomUInt> result = new StaticArray1<>( CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 2) {
            StaticArray2 <CustomUInt> result = new StaticArray2<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 3) {
            StaticArray3 <CustomUInt> result = new StaticArray3<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 4) {
            StaticArray4 <CustomUInt> result = new StaticArray4<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 5) {
            StaticArray5 <CustomUInt> result = new StaticArray5<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 6) {
            StaticArray6  <CustomUInt> result = new StaticArray6<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 7) {
            StaticArray7  <CustomUInt> result = new StaticArray7<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 8) {
            StaticArray8  <CustomUInt> result = new StaticArray8<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 9) {
            StaticArray9  <CustomUInt> result = new StaticArray9<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 10) {
            StaticArray10  <CustomUInt> result = new StaticArray10<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 11) {
            StaticArray11  <CustomUInt> result = new StaticArray11<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 12) {
            StaticArray12  <CustomUInt> result = new StaticArray12<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 13) {
            StaticArray13  <CustomUInt> result = new StaticArray13<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 14) {
            StaticArray14  <CustomUInt> result = new StaticArray14<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 15) {
            StaticArray15  <CustomUInt> result = new StaticArray15<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 16) {
            StaticArray16  <CustomUInt> result = new StaticArray16<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 17) {
            StaticArray17  <CustomUInt> result = new StaticArray17<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 18) {
            StaticArray18  <CustomUInt> result = new StaticArray18<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 19) {
            StaticArray19  <CustomUInt> result = new StaticArray19<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 20) {
            StaticArray20  <CustomUInt> result = new StaticArray20<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 21) {
            StaticArray21  <CustomUInt> result = new StaticArray21<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 22) {
            StaticArray22  <CustomUInt> result = new StaticArray22<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 23) {
            StaticArray23  <CustomUInt> result = new StaticArray23<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 24) {
            StaticArray24  <CustomUInt> result = new StaticArray24<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 25) {
            StaticArray25  <CustomUInt> result = new StaticArray25<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 26) {
            StaticArray26  <CustomUInt> result = new StaticArray26<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 27) {
            StaticArray27  <CustomUInt> result = new StaticArray27<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 28) {
            StaticArray28  <CustomUInt> result = new StaticArray28<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 29) {
            StaticArray29  <CustomUInt> result = new StaticArray29<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 30) {
            StaticArray30  <CustomUInt> result = new StaticArray30<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 31) {
            StaticArray31  <CustomUInt> result = new StaticArray31<>(  CustomUInt.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 32) {
            StaticArray32  <CustomUInt> result = new StaticArray32<>(  CustomUInt.class, values);
            return result;
        }

        else {
            log.error("Unsupported array length, please check!" + contractArgument.getSelectedArrayLength());
            return null;
        }
    }

}