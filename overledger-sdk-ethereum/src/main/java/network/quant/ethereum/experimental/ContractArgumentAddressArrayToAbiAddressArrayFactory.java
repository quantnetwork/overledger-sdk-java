package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.EthereumUintIntOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class to process array of Address
 * @author R Bahari
 */
@Slf4j
public class ContractArgumentAddressArrayToAbiAddressArrayFactory {
    @Nullable
    public static Type convertAddressArray(ContractArgument contractArgument) {
        
        List<Address> values = produceListForInput(contractArgument, contractArgument.getSelectedIntegerLength());
        //now just need to detect the different Array lengths
        return generateArray(contractArgument, values);

    }
    public static TypeReference makeTypeReferenceFromAddressArray(ContractArgument contractArgument) {
        return generateTypeReference(contractArgument);
    }

    @NotNull
    public static TypeReference generateTypeReference(ContractArgument contractArgument) {
        if(contractArgument.getSelectedArrayLength() == 1) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(1) {};
        } else if(contractArgument.getSelectedArrayLength() == 2) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(2) {};
        } else if(contractArgument.getSelectedArrayLength() == 3) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(3) {};
        } else if(contractArgument.getSelectedArrayLength() == 4) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(4) {};
        } else if(contractArgument.getSelectedArrayLength() == 5) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(5) {};
        } else if(contractArgument.getSelectedArrayLength() == 6) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(6) {};
        } else if(contractArgument.getSelectedArrayLength() == 7) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(7) {};
        } else if(contractArgument.getSelectedArrayLength() == 8) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(8) {};
        } else if(contractArgument.getSelectedArrayLength() == 9) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(9) {};
        } else if(contractArgument.getSelectedArrayLength() == 10) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(10) {};
        } else if(contractArgument.getSelectedArrayLength() == 11) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(11) {};
        } else if(contractArgument.getSelectedArrayLength() == 12) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(12) {};
        } else if(contractArgument.getSelectedArrayLength() == 13) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(13) {};
        } else if(contractArgument.getSelectedArrayLength() == 14) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(14) {};
        } else if(contractArgument.getSelectedArrayLength() == 15) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(15) {};
        } else if(contractArgument.getSelectedArrayLength() == 16) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(16) {};
        } else if(contractArgument.getSelectedArrayLength() == 17) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(17) {};
        } else if(contractArgument.getSelectedArrayLength() == 18) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(18) {};
        } else if(contractArgument.getSelectedArrayLength() == 19) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(19) {};
        } else if(contractArgument.getSelectedArrayLength() == 20) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(20) {};
        } else if(contractArgument.getSelectedArrayLength() == 21) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(21) {};
        } else if(contractArgument.getSelectedArrayLength() == 22) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(22) {};
        } else if(contractArgument.getSelectedArrayLength() == 23) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(23) {};
        } else if(contractArgument.getSelectedArrayLength() == 24) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(24) {};
        } else if(contractArgument.getSelectedArrayLength() == 25) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(25) {};
        } else if(contractArgument.getSelectedArrayLength() == 26) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(26) {};
        } else if(contractArgument.getSelectedArrayLength() == 27) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(27) {};
        } else if(contractArgument.getSelectedArrayLength() == 28) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(28) {};
        } else if(contractArgument.getSelectedArrayLength() == 29) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(29) {};
        } else if(contractArgument.getSelectedArrayLength() == 30) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(30) {};
        } else if(contractArgument.getSelectedArrayLength() == 31) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(31) {};
        } else if(contractArgument.getSelectedArrayLength() == 32) {
            return new TypeReference.StaticArrayTypeReference<StaticArray<Address>>(32) {};
        }

        else {
            log.error("Unsupported array length, please check!" + contractArgument.getSelectedArrayLength());
            return null;
        }
    }


    @NotNull
    public static List<Address> produceListForInput(ContractArgument contractArgument, EthereumUintIntOptions ethereumUintIntOptions) {

        return Stream.of(contractArgument.getValue().replace("{","").replace("}","").split(","))
                        .map(s -> new Address(s))
                        .collect(Collectors.toList());
    }

    @NotNull
    public static Type generateArray(ContractArgument contractArgument, List<Address> values) {

        /**
         * I am going to assume if selectedArrayLength is null, then its dynamic
         */
        if(contractArgument.getSelectedArrayLength() == null) {
            DynamicArray<Address> result = new DynamicArray<>(Address.class, values);
            return result;
        }


        if(contractArgument.getSelectedArrayLength() == 1) {
            StaticArray1<Address> result = new StaticArray1<>(Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 2) {
            StaticArray2<Address> result = new StaticArray2<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 3) {
            StaticArray3<Address> result = new StaticArray3<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 4) {
            StaticArray4<Address> result = new StaticArray4<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 5) {
            StaticArray5<Address> result = new StaticArray5<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 6) {
            StaticArray6 <Address> result = new StaticArray6<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 7) {
            StaticArray7 <Address> result = new StaticArray7<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 8) {
            StaticArray8 <Address> result = new StaticArray8<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 9) {
            StaticArray9 <Address> result = new StaticArray9<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 10) {
            StaticArray10 <Address> result = new StaticArray10<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 11) {
            StaticArray11 <Address> result = new StaticArray11<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 12) {
            StaticArray12 <Address> result = new StaticArray12<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 13) {
            StaticArray13 <Address> result = new StaticArray13<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 14) {
            StaticArray14 <Address> result = new StaticArray14<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 15) {
            StaticArray15 <Address> result = new StaticArray15<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 16) {
            StaticArray16 <Address> result = new StaticArray16<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 17) {
            StaticArray17 <Address> result = new StaticArray17<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 18) {
            StaticArray18 <Address> result = new StaticArray18<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 19) {
            StaticArray19 <Address> result = new StaticArray19<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 20) {
            StaticArray20 <Address> result = new StaticArray20<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 21) {
            StaticArray21 <Address> result = new StaticArray21<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 22) {
            StaticArray22 <Address> result = new StaticArray22<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 23) {
            StaticArray23 <Address> result = new StaticArray23<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 24) {
            StaticArray24 <Address> result = new StaticArray24<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 25) {
            StaticArray25 <Address> result = new StaticArray25<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 26) {
            StaticArray26 <Address> result = new StaticArray26<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 27) {
            StaticArray27 <Address> result = new StaticArray27<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 28) {
            StaticArray28 <Address> result = new StaticArray28<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 29) {
            StaticArray29 <Address> result = new StaticArray29<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 30) {
            StaticArray30 <Address> result = new StaticArray30<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 31) {
            StaticArray31 <Address> result = new StaticArray31<>( Address.class, values);
            return result;
        } else if(contractArgument.getSelectedArrayLength() == 32) {
            StaticArray32 <Address> result = new StaticArray32<>( Address.class, values);
            return result;
        }

        else {
            log.error("Unsupported array length, please check!" + contractArgument.getSelectedArrayLength());
            return null;
        }
    }

}