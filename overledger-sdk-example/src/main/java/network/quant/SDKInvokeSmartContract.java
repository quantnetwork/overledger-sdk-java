package network.quant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import network.quant.api.*;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.ethereum.EthereumAccount;
import network.quant.ethereum.experimental.dto.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SDKInvokeSmartContract {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        DefaultOverledgerSDK.setDefaultLocation("./src/main/resources/context.properties");
        DefaultOverledgerSDK sdk = DefaultOverledgerSDK.newInstance();

        String partyAEthereumPrivateKey = "e352ad01a835ec50ba301ed7ffb305555cbf3b635082af140b3864f8e3e443d3"; //should have 0x in front
        Account ethAcnt = EthereumAccount.getInstance(NETWORK.ROPSTEN, new BigInteger(partyAEthereumPrivateKey, 16), BigInteger.ZERO);
        sdk.addAccount("ethereum", ethAcnt);

        String smartContractAddress = "0x1BA73B0aE8CfB686f2C6Fa21571018Bca48Ec89d";

        List<ContractArgument> inputValues = new ArrayList<ContractArgument>(){
            {
                add(ContractArgument.builder()
                        .type(ContractInputTypeOptions.BOOLEAN_ARRAY)
                        .selectedArrayLength(3l)
                        .value("{true,true,true}")
                        .build());
            }
        };
        TransactionEthereumRequest ethereumRequest = TransactionEthereumRequest.builder()
                .dlt(DLT.ethereum.name())
                .sequence(312l)
                .toAddress(smartContractAddress)
                .feeLimit(BigInteger.valueOf(4000000l))
                .fee(BigInteger.valueOf(8000000000l))
                .amount(BigInteger.valueOf(0l))
                .funcName("setOVLTestArray")
                .inputValues(inputValues)
                .functionType(SCFunctionType.FUNCTION_CALL_WITH_PARAMETERS)
                .build();


        DltTransaction response =  sdk.buildTransaction(ethereumRequest);
        log.info("response: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
    }
}
