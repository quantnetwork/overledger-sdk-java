package network.quant.bitcoin;

import network.quant.api.NETWORK;

import network.quant.bitcoin.exception.BitcoinDataNotMatchingLengthException;
import network.quant.bitcoin.exception.BitcoinInvalidAddressException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class BitcoinUtilsTest {

    private String testData;

    @Before
    public void setup() {
        this.testData = "This is a very long iwomm string, it's used for testing Bitcoin data to address parsing";
    }

    @Test
    public void testBitcoinUtils() throws BitcoinDataNotMatchingLengthException, BitcoinInvalidAddressException {
        List<String> testAddressList = BitcoinUtils.getAddress(NETWORK.TEST, this.testData.getBytes());
        Assert.assertNotNull(testAddressList);
        String verifyData = new String(BitcoinUtils.getData(testAddressList, this.testData.getBytes().length));
        Assert.assertNotNull(verifyData);
        Assert.assertEquals(this.testData, verifyData);
    }

}
