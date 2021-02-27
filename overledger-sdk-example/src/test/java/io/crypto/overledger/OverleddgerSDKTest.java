package io.crypto.overledger;

import network.quant.api.Account;
import network.quant.api.DLT;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerSDK;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.ethereum.EthereumAccount;
import network.quant.ethereum.experimental.dto.*;
import network.quant.ripple.RippleAccount;
import network.quant.util.EventSubscribeResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OverleddgerSDKTest {

    @Mock
    private OverledgerSDK overledgerSDK;
    private Account bitcoinAccount;
    private Account ethereumAccount;
    private Account rippleAccount;
    private EventSubscribeRequest fixedArrayEventSubscribeRequest;
    private EventSubscribeRequest transferEventSubscribeRequest;
    private EventSubscribeRequest dynamicArrayEventSubscribeRequest;

    @Before
    public void setup() {
        DefaultOverledgerSDK.setDefaultLocation("../src/main/resources/context.properties");
        this.bitcoinAccount = BitcoinAccount.getInstance(NETWORK.TEST, DatatypeConverter.parseHexBinary("9d154eb68f5bcec61463885e9f32eeeb8f9dc53cfec87810e0e98529e4acdd27"));
        this.ethereumAccount = EthereumAccount.getInstance(NETWORK.TEST, DatatypeConverter.parseHexBinary("0B2BBC62B5544B7703C207D8C4B3866EF247C0F8FCC4A57E9F29BB08CA373E31"), BigInteger.ZERO);
        this.rippleAccount = RippleAccount.getInstance(NETWORK.TEST, "shJSofDUkKCAwFt6TsK4yfCR4JfA9", BigInteger.ONE);
        if (null == this.overledgerSDK) {
            this.overledgerSDK = DefaultOverledgerSDK.newInstance(NETWORK.TEST);
        }
        this.overledgerSDK.addAccount(DLT.bitcoin.name(), this.bitcoinAccount);
        this.overledgerSDK.addAccount(DLT.ethereum.name(), this.ethereumAccount);
        this.overledgerSDK.addAccount(DLT.ripple.name(), this.rippleAccount);


        fixedArrayEventSubscribeRequest = EventSubscribeRequest.builder()
                .dlt("ethereum")
                .requestDetailsList(Arrays.asList(
                        EventSubscribeRequestDetails.builder()
                                .contractAddress("0x8781d54e454377451D9C6928538Db544Caa65CDf")
                                .eventName("arraysFixed")
                                .eventParams(Arrays.asList(
                                        ContractArgument.builder().type(ContractInputTypeOptions.UINT_ARRAY)
                                                .selectedIntegerLength(EthereumUintIntOptions.B256)
                                                .selectedArrayLength(3l).build(),
                                        ContractArgument.builder().type(ContractInputTypeOptions.ADDRESS_ARRAY).selectedArrayLength(2l).build(),
                                        ContractArgument.builder().type(ContractInputTypeOptions.BOOLEAN_ARRAY).selectedArrayLength(4l).build()

                                ))
                                .callBackURL("http://localhost:8086/webhook/dummyDisplay")
                                .build())
                ).build();

        transferEventSubscribeRequest = EventSubscribeRequest.builder()
                .dlt("ethereum")
                .requestDetailsList(Arrays.asList(
                        EventSubscribeRequestDetails.builder()
                                .contractAddress("0x19Bc592A0E1BAb3AFFB1A8746D8454743EE6E838")
                                .eventName("Transfer")
                                .eventParams(Arrays.asList(
                                        ContractArgument.builder().type(ContractInputTypeOptions.ADDRESS).build(),
                                        ContractArgument.builder().type(ContractInputTypeOptions.ADDRESS).build(),
                                        ContractArgument.builder().type(ContractInputTypeOptions.UINT).selectedIntegerLength(EthereumUintIntOptions.B256).build()
                                ))
                                .callBackURL("http://localhost:8086/webhook/dummyDisplay")
                                .build())
                ).build();

        dynamicArrayEventSubscribeRequest = EventSubscribeRequest.builder()
                .dlt("ethereum")
                .requestDetailsList(Arrays.asList(
                        EventSubscribeRequestDetails.builder()
                                .contractAddress("0x8781d54e454377451D9C6928538Db544Caa65CDf")
                                .eventName("arraysDynamic")
                                .eventParams(Arrays.asList(
                                        ContractArgument.builder().type(ContractInputTypeOptions.UINT_ARRAY).selectedIntegerLength(EthereumUintIntOptions.B256).build(),
                                        ContractArgument.builder().type(ContractInputTypeOptions.ADDRESS_ARRAY).build(),
                                        ContractArgument.builder().type(ContractInputTypeOptions.BOOLEAN_ARRAY).build()
                                ))
                                .callBackURL("http://localhost:8086/webhook/dummyDisplay")
                                .build())
                ).build();
    }

    @Test
    public void test001EthSubscribeEvent_FixedArrayEvent_shouldSuccess() throws Exception {
        EventSubscribeResponse expectedEventSubscribeResponse = new EventSubscribeResponse("Subscribed successfully"
                + ", contractAddress: " + fixedArrayEventSubscribeRequest.getRequestDetailsList().get(0).getContractAddress()
                + ", EventName: " + fixedArrayEventSubscribeRequest.getRequestDetailsList().get(0).getEventName()
                + ", callBackURL: " + fixedArrayEventSubscribeRequest.getRequestDetailsList().get(0).getCallBackURL());

        Mockito.when(this.overledgerSDK.eventSubscribe(fixedArrayEventSubscribeRequest)).
                thenReturn(Arrays.asList(expectedEventSubscribeResponse));

        List<EventSubscribeResponse> actualResponse = this.overledgerSDK.eventSubscribe(fixedArrayEventSubscribeRequest);
        Assert.assertNotNull(actualResponse);
        Assert.assertEquals(expectedEventSubscribeResponse.getPayload(), actualResponse.get(0).getPayload());
    }

    @Test
    public void test002EthSubscribeEvent_transferEvent_shouldSuccess() throws Exception {
        EventSubscribeResponse expectedEventSubscribeResponse = new EventSubscribeResponse("Subscribed successfully"
                + ", contractAddress: " + transferEventSubscribeRequest.getRequestDetailsList().get(0).getContractAddress()
                + ", EventName: " + transferEventSubscribeRequest.getRequestDetailsList().get(0).getEventName()
                + ", callBackURL: " + transferEventSubscribeRequest.getRequestDetailsList().get(0).getCallBackURL());

        Mockito.when(this.overledgerSDK.eventSubscribe(transferEventSubscribeRequest)).
                thenReturn(Arrays.asList(expectedEventSubscribeResponse));

        List<EventSubscribeResponse> actualResponse = this.overledgerSDK.eventSubscribe(transferEventSubscribeRequest);
        Assert.assertNotNull(actualResponse);
        Assert.assertEquals(expectedEventSubscribeResponse.getPayload(), actualResponse.get(0).getPayload());
    }

    @Test
    public void test003EthSubscribeEvent_DynamicArrayEvent_shouldSuccess() throws Exception {
        EventSubscribeResponse expectedEventSubscribeResponse = new EventSubscribeResponse("Subscribed successfully"
                + ", contractAddress: " + dynamicArrayEventSubscribeRequest.getRequestDetailsList().get(0).getContractAddress()
                + ", EventName: " + dynamicArrayEventSubscribeRequest.getRequestDetailsList().get(0).getEventName()
                + ", callBackURL: " + dynamicArrayEventSubscribeRequest.getRequestDetailsList().get(0).getCallBackURL());

        Mockito.when(this.overledgerSDK.eventSubscribe(dynamicArrayEventSubscribeRequest)).
                thenReturn(Arrays.asList(expectedEventSubscribeResponse));

        List<EventSubscribeResponse> actualResponse = this.overledgerSDK.eventSubscribe(dynamicArrayEventSubscribeRequest);
        Assert.assertNotNull(actualResponse);
        Assert.assertEquals(expectedEventSubscribeResponse.getPayload(), actualResponse.get(0).getPayload());
    }

}
