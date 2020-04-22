package io.overledger;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.amazonaws.encryptionsdk.*;
//import com.amazonaws.encryptionsdk.keyrings.;
import com.amazonaws.encryptionsdk.kms.AwsKmsCmkId;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;


//import com.amazonaws.encryptionsdk.AwsCrypto;
//import com.amazonaws.encryptionsdk.EncryptedDataKey;
//import com.amazonaws.encryptionsdk.;
import com.amazonaws.encryptionsdk.keyrings.Keyring;
import com.amazonaws.encryptionsdk.keyrings.StandardKeyrings;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Map;

/**
 * This examples shows how to configure and use a raw AES keyring.
 * <p>
 * https://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/choose-keyring.html#use-raw-aes-keyring
 * <p>
 * In this example, we use the one-step encrypt and decrypt APIs.
 */



public class OlHSMKeyManager {

    public static class HSMCrypto {
        static final AwsCrypto awsEncryptionSdk = new AwsCrypto();

        public static byte[] generateRandomKey(){
            SecureRandom rnd = new SecureRandom();
            byte[] rawKey = new byte[32]; // 256 bits
            rnd.nextBytes(rawKey);
            return rawKey;
        }

        public static String getStringFromBytes(byte [] key){
            return Base64.getEncoder().withoutPadding().encodeToString(key);
        }
        public static byte[] getBytesFromString(String keyStr){
            return Base64.getDecoder().decode(keyStr);

        }
        public static Keyring simulateSingleKeyRing(String strKey) {
            return simulateSingleKeyRing(getBytesFromString(strKey));
        }

        public static Keyring simulateSingleKeyRing(byte[] rawKey){
            // The private key for decrypting should be be obtained from HSM
            // An example implementation is provided based on AWS KMS / Cloud HSM

            SecretKey key = new SecretKeySpec(rawKey, "AES");

            // Create the keyring that determines how your data keys are protected.
            final Keyring keyring = StandardKeyrings.rawAesBuilder()
                    // The key namespace and key name are defined by you
                    // and are used by the raw AES keyring
                    // to determine whether it should attempt to decrypt
                    // an encrypted data key.
                    //
                    // https://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/choose-keyring.html#use-raw-aes-keyring
                    .keyNamespace("Overledger")
                    .keyName("my AES wrapping key")
                    .wrappingKey(key)
                    .build();
            return keyring;
        }

        public static Keyring getKeyRingFromCMK(String awsCMK){
            // The private key for decrypting should be be obtained from HSM
            // An example implementation is provided based on AWS KMS / Cloud HSM
            final Keyring keyring = StandardKeyrings.awsKms(AwsKmsCmkId.fromString(awsCMK));

            return keyring;
        }

        public static byte[] encrypt(final byte[] sourcePlaintext, Keyring keyring){
            // Encrypt your plaintext data.
            final Map<String, String> encryptionContext = new HashMap<>();
            encryptionContext.put("overledger", "javaSDK");
            encryptionContext.put("version", "alpha4"); // todo get version from library


            final AwsCryptoResult<byte[]> encryptResult = awsEncryptionSdk.encrypt(
                    EncryptRequest.builder()
                            .keyring(keyring)
                            .encryptionContext(encryptionContext)
                            .plaintext(sourcePlaintext).build());
            final byte[] ciphertext = encryptResult.getResult();

            // Demonstrate that the ciphertext and plaintext are different.
            assert !Arrays.equals(ciphertext, sourcePlaintext);
            return ciphertext;
        }

        public static byte[] decrypt(final byte[] encryptedKeyString, Keyring keyring) {
            // Instantiate the AWS Encryption SDK.


            // Prepare your encryption context.
            // https://docs.aws.amazon.com/encryption-sdk/latest/developer-guide/concepts.html#encryption-context

            // Generate an AES key to use with your keyring.
            //
            // In practice, you should get this key from a secure key management system



            // Decrypt your encrypted data using the same keyring you used on encrypt.
            //
            // You do not need to specify the encryption context on decrypt because
            // the header of the encrypted message includes the encryption context.
            final AwsCryptoResult<byte[]> decryptResult = awsEncryptionSdk.decrypt(
                    DecryptRequest.builder()
                            .keyring(keyring)
                            .ciphertext(encryptedKeyString).build());
            final byte[] decrypted = decryptResult.getResult();

            // Demonstrate that the decrypted plaintext is identical to the original plaintext.
            System.out.println(decrypted);

            // Verify that the encryption context used in the decrypt operation includes
            // the encryption context that you specified when encrypting.
            // The AWS Encryption SDK can add pairs, so don't require an exact match.
            //
            // In production, always use a meaningful encryption context.

            if(decryptResult.getEncryptionContext().containsKey("overledger")){
                System.out.println("private key was encrypted with overledger SDK");
            }
            return decrypted;
        }
    }

    private final Keyring keyring;

    public OlHSMKeyManager(Keyring ke){
        keyring = ke;
    }


    public String decryptPrivateKeyHexString(String encKeyStr) throws UnsupportedEncodingException {

        byte [] byts =  Base64.getDecoder().decode(encKeyStr);

        byte [] decrypted = HSMCrypto.decrypt(byts, keyring);
        return new String(decrypted,StandardCharsets.UTF_8);
    }
    public String encryptPrivateKeyHexString(String decKeyStr) throws UnsupportedEncodingException {
     //     encode with padding
     //     String encoded = Base64.getEncoder().encodeToString(someByteArray);

         byte [] byts = decKeyStr.getBytes(StandardCharsets.UTF_8);


        byte [] encrypted = HSMCrypto.encrypt(byts, keyring);
        String encoded = Base64.getEncoder().withoutPadding().encodeToString(encrypted);
        return encoded;

    }

    /**
     * <p>
     * Encrypts and then decrypts a string under a KMS key
     *
     * <p>
     * Arguments:
     * <ol>
     * <li>Key ARN: For help finding the Amazon Resource Name (ARN) of your KMS customer master
     *    key (CMK), see 'Viewing Keys' at http://docs.aws.amazon.com/kms/latest/developerguide/viewing-keys.html
     * <li>String to encrypt
     * </ol>
     */

/*    static boolean testCode(String keyArn, byte[] data){



                // Instantiate the SDK
                final AwsCrypto crypto = new AwsCrypto();

                // Set up the KmsMasterKeyProvider backed by the default credentials
                final KmsMasterKeyProvider prov = KmsMasterKeyProvider.builder().withKeysForEncryption(keyArn).build();

                // Encrypt the data
                //
                // Most encrypted data should have an associated encryption context
                // to protect integrity. This sample uses placeholder values.
                //
                // For more information see:
                // blogs.aws.amazon.com/security/post/Tx2LZ6WBJJANTNW/How-to-Protect-the-Integrity-of-Your-Encrypted-Data-by-Using-AWS-Key-Management
                final Map<String, String> context = Collections.singletonMap("Example", "String");

                final byte[] ciphertext = crypto.encryptData(prov, data, context).getResult();

                // Decrypt the data
                final CryptoResult<byte[], KmsMasterKey> decryptResult = crypto.decryptData(prov, ciphertext);

                // Before returning the plaintext, verify that the customer master key that
                // was used in the encryption operation was the one supplied to the master key provider.
                if (!decryptResult.getMasterKeyIds().get(0).equals(keyArn)) {
                    throw new IllegalStateException("Wrong key ID!");
                }

                // Also, verify that the encryption context in the result contains the
                // encryption context supplied to the encryptString method. Because the
                // SDK can add values to the encryption context, don't require that
                // the entire context matches.
                for (final Map.Entry<String, String> e : context.entrySet()) {
                    if (!e.getValue().equals(decryptResult.getEncryptionContext().get(e.getKey()))) {
                        throw new IllegalStateException("Wrong Encryption Context!");
                    }
                }
                byte[] res = decryptResult.getResult();
        try {
            String plaintxt  = new String(res,"UTF-8" );
            System.out.println("Decrypted: " + plaintxt);
            if (plaintxt.equals(new String(data, "UTF-8"))){
                System.out.println("ALl OK");
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Now we can return the plaintext data
        return false;
    }*/


    public static void main(String[] args) {
        String keyId = "arn:aws:kms:eu-west-2:344507690543:key/bacdc930-8eb4-4e37-ba1d-d516596a8091";

        try {
            testCode(keyId, keyId.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


}
