package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.EthereumBytesOptions;
import network.quant.ethereum.experimental.dto.EthereumUintIntOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.StaticArray;
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

    public static final long MAXIMUM_STATIC_ARRAY_SIZE = 33l;

    @Nullable
    public static Type convertBytesArray(ContractArgument contractArgument) {
        
        List<CustomBytes> values = produceListForInput(contractArgument, contractArgument.getSelectedIntegerLength());
        //now just need to detect the different Array lengths
        return generateArray(contractArgument, values);

    }

    public static TypeReference makeTypeReferenceFromByteArray(ContractArgument contractArgument) {
        return generateArrayTypeReference(contractArgument);
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

    public static TypeReference generateArrayTypeReference(ContractArgument contractArgument) {

        if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B1)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes1>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B2)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes2>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B3)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes3>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B4)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes4>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B5)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes5>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B6)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes6>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B7)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes7>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B8)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes8>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B9)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes9>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B10)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes10>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B11)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes11>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B12)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes12>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B13)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes13>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B14)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes14>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B15)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes15>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B16)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes16>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B17)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes17>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B18)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes18>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B19)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes19>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B20)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes20>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B21)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes21>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B22)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes22>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B23)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes23>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B24)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes24>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B25)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes25>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B26)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes26>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B27)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes27>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B28)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes28>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B29)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes29>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B30)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes30>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B31)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes31>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else if (contractArgument.getSelectedBytesLength().equals(EthereumBytesOptions.B32)) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Bytes32>>(contractArgument.getSelectedArrayLength().intValue()) {
            };
        } else {
            log.error("Unsupported array length, please check!" + contractArgument.getSelectedArrayLength());
            return null;
        }
    }

}