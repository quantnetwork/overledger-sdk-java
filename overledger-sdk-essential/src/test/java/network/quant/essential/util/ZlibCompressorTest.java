package network.quant.essential.util;

import network.quant.api.Compressor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ZlibCompressorTest {

    private String testData;

    @Before
    public void setup() {
        this.testData = "This is iwomm string fro testing Zlib compression";
    }

    @Test
    public void testZlibCompressor() {
        Compressor testCompressor = ZlibCompressor.newInstance();
        byte[] testResult = testCompressor.compress(this.testData.getBytes());
        Assert.assertNotNull(testData);
        String verifyResult = new String(testCompressor.decompress(testResult));
        Assert.assertNotNull(verifyResult);
        Assert.assertEquals(this.testData, verifyResult);
    }

}
