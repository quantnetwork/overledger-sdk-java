package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.EthereumUintIntOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to process array of Bytes
 * @author R Bahari
 */
@Slf4j
public class ContractArgumentBytesArrayToAbiBytesArrayFactory {
    @Nullable
    public static Type convertBytesArray(ContractArgument contractArgument) {
        
        List<CustomBytes> values = produceListForInput(contractArgument, contractArgument.getSelectedIntegerLength());
        //now just need to detect the different Array lengths
        return generateArray(contractArgument, values);

    }

    @NotNull
    public static List<CustomBytes> produceListForInput(ContractArgument contractArgument, EthereumUintIntOptions ethereumUintIntOptions) {

        return Stream.of(contractArgument.getValue().replace("{","").replace("}","").split(","))
                        .map(s -> new CustomBytes(Integer.parseInt(ethereumUintIntOptions.label), s.getBytes()))
                        .collect(Collectors.toList());
    }

    @NotNull
    public static Type generateArray(ContractArgument contractArgument, List<CustomBytes> values) {


        /**
         * I am going to assume if selectedArrayLength is null, then go for dynamic
         */
        if(contractArgument.getSelectedArrayLength() == null) {
            DynamicArray<CustomBytes> result = new DynamicArray<>(CustomBytes.class, values);
            return result;
        }

        if(contractArgument.getSelectedArrayLength() == 1) {
            StaticArray1<CustomBytes> result = new StaticArray1<>(CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 2) {
            StaticArray2<CustomBytes> result = new StaticArray2<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 3) {
            StaticArray3<CustomBytes> result = new StaticArray3<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 4) {
            StaticArray4<CustomBytes> result = new StaticArray4<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 5) {
            StaticArray5<CustomBytes> result = new StaticArray5<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 6) {
            StaticArray6 <CustomBytes> result = new StaticArray6<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 7) {
            StaticArray7 <CustomBytes> result = new StaticArray7<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 8) {
            StaticArray8 <CustomBytes> result = new StaticArray8<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 9) {
            StaticArray9 <CustomBytes> result = new StaticArray9<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 10) {
            StaticArray10 <CustomBytes> result = new StaticArray10<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 11) {
            StaticArray11 <CustomBytes> result = new StaticArray11<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 12) {
            StaticArray12 <CustomBytes> result = new StaticArray12<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 13) {
            StaticArray13 <CustomBytes> result = new StaticArray13<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 14) {
            StaticArray14 <CustomBytes> result = new StaticArray14<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 15) {
            StaticArray15 <CustomBytes> result = new StaticArray15<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 16) {
            StaticArray16 <CustomBytes> result = new StaticArray16<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 17) {
            StaticArray17 <CustomBytes> result = new StaticArray17<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 18) {
            StaticArray18 <CustomBytes> result = new StaticArray18<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 19) {
            StaticArray19 <CustomBytes> result = new StaticArray19<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 20) {
            StaticArray20 <CustomBytes> result = new StaticArray20<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 21) {
            StaticArray21 <CustomBytes> result = new StaticArray21<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 22) {
            StaticArray22 <CustomBytes> result = new StaticArray22<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 23) {
            StaticArray23 <CustomBytes> result = new StaticArray23<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 24) {
            StaticArray24 <CustomBytes> result = new StaticArray24<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 25) {
            StaticArray25 <CustomBytes> result = new StaticArray25<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 26) {
            StaticArray26 <CustomBytes> result = new StaticArray26<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 27) {
            StaticArray27 <CustomBytes> result = new StaticArray27<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 28) {
            StaticArray28 <CustomBytes> result = new StaticArray28<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 29) {
            StaticArray29 <CustomBytes> result = new StaticArray29<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 30) {
            StaticArray30 <CustomBytes> result = new StaticArray30<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 31) {
            StaticArray31 <CustomBytes> result = new StaticArray31<>( CustomBytes.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 32) {
            StaticArray32 <CustomBytes> result = new StaticArray32<>( CustomBytes.class, values);
            return result;
        }

        else {
            log.error("Unsupported array length, please check!" + contractArgument.getSelectedArrayLength());
            return null;
        }
    }

}