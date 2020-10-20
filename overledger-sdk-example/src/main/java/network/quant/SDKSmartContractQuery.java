package network.quant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import network.quant.api.Account;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerSDK;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.ethereum.EthereumAccount;
import network.quant.ethereum.experimental.dto.*;
import network.quant.util.ContractQueryResponseDto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class SDKSmartContractQuery {

    static final ObjectMapper mapper = new ObjectMapper();
    static final String dlt = "ethereum";

    public static void main(String[] args) throws JsonProcessingException {
        DefaultOverledgerSDK.setDefaultLocation("./src/main/resources/context.properties");
        OverledgerSDK sdk = DefaultOverledgerSDK.newInstance();

        String partyAEthereumPrivateKey = "e352ad01a835ec50ba301ed7ffb305555cbf3b635082af140b3864f8e3e443d3"; //should have 0x in front
        Account ethAcnt = EthereumAccount.getInstance(NETWORK.ROPSTEN, new BigInteger(partyAEthereumPrivateKey, 16), BigInteger.ZERO);
        sdk.addAccount("ethereum", ethAcnt);

        List<ContractArgument> outputParams = new ArrayList<ContractArgument>() {
            {
                add(ContractArgument.builder()
                        .type(ContractInputTypeOptions.UINT)
                        .selectedIntegerLength(EthereumUintIntOptions.B256)
                        .build()
                );
            }
        };

        TransactionEthereumRequest ethereumRequest_1 = TransactionEthereumRequest.trxEthereumReqBuilder()
                .dlt("ethereum")
                .toAddress("0x1BA73B0aE8CfB686f2C6Fa21571018Bca48Ec89d")
                .fromAddress("0x650A87cfB9165C9F4Ccc7B971D971f50f753e761")
                .funcName("getOVLTestUint")
                .inputValues(Collections.emptyList())
                .outputTypes(outputParams)
                .build();
        ContractQueryResponseDto ethereumSmartContractQueryBuild1 = sdk.smartContractQuery(ethereumRequest_1, dlt);
        log.info("response1: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ethereumSmartContractQueryBuild1));


        List<ContractArgument> inputParams_2 = new ArrayList<ContractArgument>() {
            {
                add(ContractArgument.builder()
                        .type(ContractInputTypeOptions.UINT)
                        .selectedIntegerLength(EthereumUintIntOptions.B256)
                        .value("0")
                        .build()
                );
            }
        };

        List<ContractArgument> outputParams_2 = new ArrayList<ContractArgument>() {
            {
                add(ContractArgument.builder()
                        .type(ContractInputTypeOptions.BOOL)
                        .build()
                );
            }
        };

        TransactionEthereumRequest ethereumRequest_2 = TransactionEthereumRequest.trxEthereumReqBuilder()
                .dlt("ethereum")
                .toAddress("0x1BA73B0aE8CfB686f2C6Fa21571018Bca48Ec89d")
                .fromAddress("0x650A87cfB9165C9F4Ccc7B971D971f50f753e761")
                .funcName("getTestArray")
                .inputValues(inputParams_2)
                .outputTypes(outputParams_2)
                .build();
        ContractQueryResponseDto ethereumSmartContractQueryBuild2 = sdk.smartContractQuery(ethereumRequest_2, dlt);
        log.info("response2: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ethereumSmartContractQueryBuild2));
    }
}
