package network.quant.essential;

import network.quant.OverledgerContext;
import network.quant.api.*;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.essential.dto.DltTransactionResponse;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.essential.dto.OverledgerTransactionsResponse;
import network.quant.essential.exception.DltNotSupportedException;
import network.quant.essential.exception.EmptyDltException;
import org.assertj.core.api.Assertions;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Collections;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DefaultOverledgerSDKTest {

    @Mock
    AccountManager accountManager;
    @Mock
    Client client;
    private OverledgerSDK overledgerSDK;
    private OverledgerTransactionRequest overledgerTransactionRequest;
    private OverledgerTransactionResponse overledgerTransactionResponse;
    private OverledgerTransactionsResponse overledgerTransactionsResponse;
    private Account bitcoinAccount;
    private UUID transactionId;
    private ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
    private ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
    private ArgumentCaptor<OverledgerTransactionRequest> overledgerTransactionRequestArgumentCaptor = ArgumentCaptor.forClass(OverledgerTransactionRequest.class);

    @Before
    public void setup() {
        this.overledgerSDK = DefaultOverledgerSDK.newInstance(NETWORK.TEST, this.accountManager, this.client);
        this.overledgerTransactionRequest = new OverledgerTransactionRequest();
        this.overledgerTransactionResponse = new OverledgerTransactionResponse();
        this.transactionId = UUID.randomUUID();
        this.bitcoinAccount = new Account() {
            @Override
            public Account withNetwork(NETWORK network) {
                return this;
            }
            @Override
            public void setPrivateKey(BigInteger key) {}
            @Override
            public BigInteger getPrivateKey() {
                return BigInteger.ONE;
            }
            @Override
            public void sign(String fromAddress, String toAddress, String message, DltTransaction dltTransaction) {}
            @Override
            public void sign(String fromAddress, String toAddress, byte[] message, DltTransaction dltTransaction) {}
            @Override
            public void sign(String fromAddress, String toAddress, InputStream message, DltTransaction dltTransaction) {}
        };
        this.overledgerTransactionsResponse = new OverledgerTransactionsResponse();
        this.overledgerTransactionsResponse.setTransactions(Collections.singletonList(this.overledgerTransactionResponse));
        this.overledgerTransactionsResponse.setTotalTransactions(1);
    }

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(this.accountManager, this.accountManager);
    }

    @Test
    public void test001WriteTransaction_Without_DltData_ShouldFail() {
        Assertions
                .assertThatThrownBy(() -> this.overledgerSDK.writeTransaction(this.overledgerTransactionRequest))
                .isInstanceOf(EmptyDltException.class)
                .hasMessage("No DLT found");
    }

    @Test
    public void test002WriteTransaction_With_Unknown_DltData_ShouldFail() {
        DltTransactionRequest dltTransactionRequest = new DltTransactionRequest();
        dltTransactionRequest.setDlt("testDlt");
        this.overledgerTransactionRequest.setDltData(Collections.singletonList(dltTransactionRequest));

        Mockito.when(this.accountManager.getAccount(Mockito.anyString())).thenReturn(null);

        Assertions
                .assertThatThrownBy(() -> this.overledgerSDK.writeTransaction(this.overledgerTransactionRequest))
                .isInstanceOf(DltNotSupportedException.class)
                .hasMessage("DLT type is not supported");

        Mockito.verify(this.accountManager, Mockito.only()).getAccount(this.stringArgumentCaptor.capture());
    }

    @Test
    public void test003WriteTransaction_ShouldSuccess() throws Exception {
        DltTransactionRequest dltTransactionRequest = new DltTransactionRequest();
        dltTransactionRequest.setDlt(DLT.bitcoin.name());
        this.overledgerTransactionRequest.setDltData(Collections.singletonList(dltTransactionRequest));

        Mockito.when(this.accountManager.getAccount(DLT.bitcoin.name())).thenReturn(this.bitcoinAccount).thenReturn(this.bitcoinAccount);
        Mockito.when(this.client.postTransaction(Mockito.any(OverledgerTransactionRequest.class), eq(OverledgerTransactionRequest.class), eq(OverledgerTransactionResponse.class))).thenReturn(this.overledgerTransactionResponse);

        OverledgerTransaction overledgerTransaction = this.overledgerSDK.writeTransaction(this.overledgerTransactionRequest);
        Assert.assertNotNull(overledgerTransaction);
        Assert.assertEquals(this.overledgerTransactionResponse, overledgerTransaction);

        Mockito.verify(this.accountManager, Mockito.times(2)).getAccount(DLT.bitcoin.name());
        Mockito.verify(this.client, Mockito.only()).postTransaction(this.overledgerTransactionRequestArgumentCaptor.capture(), eq(OverledgerTransactionRequest.class), eq(OverledgerTransactionResponse.class));
    }

    @Test
    public void test004ReadTransaction_ShouldSuccess() throws Exception {
        this.overledgerTransactionResponse.setOverledgerTransactionId(this.transactionId);

        Mockito.when(this.client.getTransaction(Mockito.any(UUID.class), eq(OverledgerTransactionResponse.class))).thenReturn(this.overledgerTransactionResponse);

        OverledgerTransaction overledgerTransaction = this.overledgerSDK.readTransaction(this.transactionId);
        Assert.assertNotNull(overledgerTransaction);
        Assert.assertEquals(overledgerTransaction, this.overledgerTransactionResponse);

        Mockito.verify(this.client, Mockito.only()).getTransaction(this.uuidArgumentCaptor.capture(), eq(OverledgerTransactionResponse.class));
    }

    @Test
    public void test005ReadTransaction_ShouldSuccess() throws Exception {
        DltTransactionResponse dltTransactionResponse = new DltTransactionResponse();
        dltTransactionResponse.setDlt(DLT.bitcoin.name());

        this.overledgerTransactionResponse.setOverledgerTransactionId(this.transactionId);
        this.overledgerTransactionResponse.setDltData(Collections.singletonList(dltTransactionResponse));

        Mockito.when(this.client.getTransaction(Mockito.anyString(), Mockito.anyString(), eq(OverledgerTransactionResponse.class))).thenReturn(this.overledgerTransactionResponse);

        OverledgerTransaction overledgerTransaction = this.overledgerSDK.readTransaction(DLT.bitcoin.name(), "TX_HASH");
        Assert.assertNotNull(overledgerTransaction);
        Assert.assertEquals(this.overledgerTransactionResponse, overledgerTransaction);

        Mockito.verify(this.client, Mockito.only()).getTransaction(this.stringArgumentCaptor.capture(), this.stringArgumentCaptor.capture(), eq(OverledgerTransactionResponse.class));
    }

    @Test
    public void test006ReadTransactions_ShouldSuccess() throws Exception {
        DltTransactionResponse dltTransactionResponse = new DltTransactionResponse();
        dltTransactionResponse.setDlt(DLT.bitcoin.name());

        this.overledgerTransactionResponse.setMappId("network.quant.essential");
        this.overledgerTransactionResponse.setOverledgerTransactionId(this.transactionId);
        this.overledgerTransactionResponse.setDltData(Collections.singletonList(dltTransactionResponse));

        Mockito.when(this.client.getTransactions(Mockito.anyString(), eq(OverledgerTransactionsResponse.class))).thenReturn(this.overledgerTransactionsResponse);

        OverledgerTransactions overledgerTransactionList = this.overledgerSDK.readTransactions("network.quant.essential");
        Assert.assertNotNull(overledgerTransactionList);
        Assert.assertEquals(1, overledgerTransactionList.getTotalTransactions());
        Assert.assertEquals(this.overledgerTransactionResponse, overledgerTransactionList.getTransactions().get(0));

        Mockito.verify(this.client, Mockito.only()).getTransactions(this.stringArgumentCaptor.capture(), eq(OverledgerTransactionsResponse.class));
    }

}
