package network.quant.bitcoin;

import network.quant.api.DATA_TYPE;
import network.quant.api.NETWORK;

import network.quant.bitcoin.exception.BitcoinDataNotMatchingLengthException;
import network.quant.bitcoin.exception.BitcoinInvalidAddressException;
import network.quant.exception.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class BitcoinDataTest {

    private String testData;

    @Before
    public void setup() {
        this.testData = "This is iwomm data for Bitcoin data iwomm";
    }

    @Test
    public void testBitcoinData() throws DataOverSizeException, NetworkNotMatchException, AddressChecksumNotMatchException, UnknownDataException, BitcoinDataNotMatchingLengthException, DataNotFoundException, BitcoinInvalidAddressException {
        BitcoinData testResult = new BitcoinData(NETWORK.TEST, this.testData.getBytes(), DATA_TYPE.TEXT, null, null);
        List<String> addressList = testResult.getAddressList();
        Assert.assertNotNull(addressList);
        BitcoinData verifyResult = new BitcoinData(NETWORK.TEST, addressList, null, null);
        Assert.assertNotNull(verifyResult);
        Assert.assertEquals(this.testData, new String(verifyResult.getData()));
    }

}
