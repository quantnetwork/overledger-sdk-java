package network.quant.essential.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RsaEncryptorTest {

    private String privateKey;
    private String publicKey;
    private String testData;

    @Before
    public void setup() {
        this.privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALG1Y4c62JyGyl2KCpJkKtUdZroBxmhYDN4ucNJMirczoXfDH/jVocOLPQuAN+SB7PpfFxDkxQta0sACz1ACdDnvg/CJ9BHW/wNX5W2Rn6XAjC7HrAbkKjsbxVkQWsbL//YpwPpR5viikc16yjv8PUB5ld9vDFtSdAkjFNr+/kbDAgMBAAECgYAogLgbhT0Dh/Etmp3IOc5NXnMgZ/EprQ/Zv+n09H8VPE5L9owd8cr5v419Ro72pOo3Ml8WeZ2zddLc8ob/rF+CxyfXATGBJ3CVS9yQbLSo3owegstUF0sYIVYcGkAQwC6mgxnblgm8yAeL3QYeEcEJ0t+c4mNxfRBtGLpoNoogcQJBAOxczTjwRrKWNDn1xTZezKVO/7u0zF1xE7xzGyt/kM9yAx44yVcITkCsg5MsUYSyklnk02HkdhMvjtBoHnvA5lkCQQDAeRM5AxFX1HG86qpXBMRqoBtoRvoKPfiX9dtf9dgl7/zaROkNA5o5guookppPy5vN2NjgF7xhQUDEytmZDSp7AkEAkCzrYdfux/CnWtN3wHqDCjLt+2mFX1oiV8k+wYHGq0M4c67f4Z7HXNveRl+6Od9TNlJGPmhFPd68lpNsRMCdwQJAZX9zo5z6iFZ/pCAbs8fPzGqiCVtkgAIuFfSQuwnp8hecP34XZ8V+jtV6peqpdWrpvqObupDJxn+iqCRQm2VWAQJAaZKmGKfkg8uXaPZHltZ8a4n/U5BHLo6lrMuukdyTaGCYyOzbAr/WiHBVua1KyRk4gdNURXP/pdF8dI8Wp9LVNg==";
        this.publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxtWOHOtichspdigqSZCrVHWa6AcZoWAzeLnDSTIq3M6F3wx/41aHDiz0LgDfkgez6XxcQ5MULWtLAAs9QAnQ574PwifQR1v8DV+VtkZ+lwIwux6wG5Co7G8VZEFrGy//2KcD6Ueb4opHNeso7/D1AeZXfbwxbUnQJIxTa/v5GwwIDAQAB";
        this.testData = "This is iwomm string fro testing RSA encryption";
    }

    @Test
    public void testRsaEncryptor() {
        RsaEncryptor rsaEncryptor = new RsaEncryptor(null, this.publicKey);
        byte[] testResult = rsaEncryptor.encrypt(this.testData.getBytes());
        Assert.assertNotNull(testResult);

        RsaEncryptor rsaDecryptor = new RsaEncryptor(this.privateKey, null);
        String verifyResult = new String(rsaDecryptor.decrypt(testResult));
        Assert.assertNotNull(verifyResult);
        Assert.assertEquals(this.testData, verifyResult);
    }

}
