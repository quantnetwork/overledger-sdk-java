package network.quant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.ethereum.experimental.dto.*;
import network.quant.util.EventSubscribeResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class SDKSubscribeUnsubscribeEvent {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        DefaultOverledgerSDK.setDefaultLocation("./src/main/resources/context.properties");
        DefaultOverledgerSDK sdk = DefaultOverledgerSDK.newInstance();

        String smartContractAddress = "0x19Bc592A0E1BAb3AFFB1A8746D8454743EE6E838";

        //the order of contract arguments are important
        EventSubscribeRequestDetails eventSubscribeRequestDetails1 = EventSubscribeRequestDetails.builder()
                .contractAddress(smartContractAddress)
                .eventName("Approval")
                .eventParams(Arrays.asList(
                        ContractArgument.builder().type(ContractInputTypeOptions.ADDRESS).build(),
                        ContractArgument.builder().type(ContractInputTypeOptions.ADDRESS).build(),
                        ContractArgument.builder().type(ContractInputTypeOptions.UINT).selectedIntegerLength(EthereumUintIntOptions.B256).build()
                ))
                .callBackURL("http://localhost:8086/webhook/dummyDisplay")
                .build();

        EventSubscribeRequest eventSubscribeRequest = EventSubscribeRequest.builder()
                .requestDetailsList(Arrays.asList(eventSubscribeRequestDetails1))
                .build();

        List<EventSubscribeResponse> subscribeEventResponse = sdk.eventSubscribe(eventSubscribeRequest);
        log.info("event subscribe response: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(subscribeEventResponse));

        List<EventSubscribeResponse> unsubscribeEventResponse = sdk.eventUnsubscribe(eventSubscribeRequest);
        log.info("event unsubscribe response: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(unsubscribeEventResponse));

    }
}
