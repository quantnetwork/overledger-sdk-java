package network.quant.ethereum;

import lombok.extern.slf4j.Slf4j;
import network.quant.ethereum.experimental.dto.ContractArgument;
import network.quant.ethereum.experimental.dto.ContractInputTypeOptions;
import network.quant.ethereum.experimental.dto.EthereumBytesOptions;
import network.quant.ethereum.experimental.dto.EthereumUintIntOptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.StaticArray2;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class EthereumUtilTest {

    String functionName = "setOVLTestArray";
    List<ContractArgument> inputValues = new ArrayList<ContractArgument>() {
        {
            add(ContractArgument.builder()
                    .type(ContractInputTypeOptions.BOOLEAN_ARRAY)
                    .selectedArrayLength(2l)
                    .value("{false,false}")
                    .build());
            add(ContractArgument.builder()
                    .type(ContractInputTypeOptions.UINT_ARRAY)
                    .selectedIntegerLength(EthereumUintIntOptions.B256)
                    .selectedArrayLength(3l)
                    .value("{12,13,14}")
                    .build());
            add(ContractArgument.builder()
                    .type(ContractInputTypeOptions.INT)
                    .selectedIntegerLength(EthereumUintIntOptions.B256)
                    .value("{20}")
                    .build());
            add(ContractArgument.builder()
                    .type(ContractInputTypeOptions.BYTES)
                    .selectedBytesLength(EthereumBytesOptions.B1)
                    .value("{10}")
                    .build());
        }
    };

    @Before
    public void setup() {
        log.info("setup...");
    }


    @Test
    public void test00_FunctionEncodeWithParams_BOOLEANARRAY_UINTARRAY_INT_BYTES() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<String> solidityInputTypes = EthereumUtil.getSolidityInputOutputTypes(inputValues);
        List<Object> arguments = EthereumUtil.getValues(inputValues);

        String expected = "0x10c4980700000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000c000000000000000000000000000000000000000000000000000000000000000d000000000000000000000000000000000000000000000000000000000000000e00000000000000000000000000000000000000000000000000000000000000140a00000000000000000000000000000000000000000000000000000000000000";
        Function makeFunction = FunctionEncoder.makeFunction(
                functionName,
                solidityInputTypes,
                arguments,
                Collections.emptyList());

        String encodedFunction = FunctionEncoder.encode(makeFunction);
        Assert.assertEquals(encodedFunction, expected);
    }

    @Test
    public void test001_FunctionEncodeWithParams_BOOLEANARRAY_INTARRAY() throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {

        List<String> solidityInputTypes = Arrays.asList("bool[2]", "int256[3]");
        List<Object> arguments = Arrays.asList(Arrays.asList(false, false), Arrays.asList(12, 13, 14));

        Function actualFunction = FunctionEncoder.makeFunction(
                functionName,
                solidityInputTypes,
                arguments,
                Collections.emptyList());

        String expected = "0x51f1e28800000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000c000000000000000000000000000000000000000000000000000000000000000d000000000000000000000000000000000000000000000000000000000000000e";

        String encodedFunction = FunctionEncoder.encode(actualFunction);
        Assert.assertEquals(encodedFunction, expected);
    }

    @Test
    public void test002_FunctionEncodeWithParams(){
        Function testFunc_2 = new Function(
                functionName,
                Arrays.<Type>asList(
                        new StaticArray2(
                                Bool.class,
                                Arrays.asList(Bool.DEFAULT, Bool.DEFAULT)))
                , Collections.emptyList()
        );
        String expected = "0x94cf097000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

        String encodedFunction = FunctionEncoder.encode(testFunc_2);
        Assert.assertEquals(encodedFunction, expected);
    }
}