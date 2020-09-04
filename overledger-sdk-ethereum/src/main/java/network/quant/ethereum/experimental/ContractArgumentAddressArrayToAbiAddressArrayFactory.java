package network.quant.ethereum.experimental;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.EthereumUintIntOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
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
        }

        else {
            log.error("Unsupported array length, please check!" + contractArgument.getSelectedArrayLength());
            return null;
        }
    }

}