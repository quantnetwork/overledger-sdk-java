package network.quant.essential;

import network.quant.api.Account;
import network.quant.api.DLT;
import network.quant.api.DltTransaction;
import network.quant.api.NETWORK;
import network.quant.essential.exception.EmptyAccountException;
import network.quant.essential.exception.IllegalKeyException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.math.BigInteger;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountManagerTest {

    private AccountManager accountManager;
    private DLT dlt;
    private Account account;

    @Before
    public void setup() {
        this.accountManager = AccountManager.newInstance();
        this.dlt = DLT.bitcoin;
        this.account = new Account() {
            @Override
            public Account withNetwork(NETWORK network) {return this;}
            @Override
            public void setPrivateKey(BigInteger key) {}
            @Override
            public BigInteger getPrivateKey() {return BigInteger.ONE;}
            @Override
            public void sign(String fromAddress, String toAddress, String message, DltTransaction dltTransaction) {}
            @Override
            public void sign(String fromAddress, String toAddress, byte[] message, DltTransaction dltTransaction) {}
            @Override
            public void sign(String fromAddress, String toAddress, InputStream message, DltTransaction dltTransaction) {}
            @Override
            public void invokeContract(DltTransaction dltTransaction){}

            @Override
            public void createSmartContract(DltTransaction dltTransaction) {

            }

            @Override
            public DltTransaction buildSmartContractQuery(DltTransaction dltTransaction){return null;}
            @Override
            public void addUtxo(String transactionHash, long outpoint, long valueInSatoshi, int blockHeight, String address) {

            }
        };
    }

    @Test
    public void testRegisterAccount_New_Account_With_Empty_Key_Failed() {
        Assertions
                .assertThatThrownBy(() -> this.accountManager.registerAccount(null, null))
                .isInstanceOf(IllegalKeyException.class)
                .hasMessage("DLT type is not found");
    }

    @Test
    public void testRegisterAccount_New_Account_With_Empty_Account_Failed() {
        Assertions
                .assertThatThrownBy(() -> this.accountManager.registerAccount(DLT.bitcoin.name(), null))
                .isInstanceOf(EmptyAccountException.class)
                .hasMessage("DLT account is not found");
    }

    @Test
    public void testRegisterAccount_New_Account_Success() throws EmptyAccountException, IllegalKeyException {
        this.accountManager.registerAccount(this.dlt.name(), this.account);
        Account account = this.accountManager.getAccount(this.dlt.name());
        assertNotNull(account, "Account should not be null");
        assertEquals(this.account, account);
    }

}
