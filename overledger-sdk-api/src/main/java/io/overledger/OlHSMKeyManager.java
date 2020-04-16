package io.overledger;
import java.util.Collections;
import java.util.Map;

import com.amazonaws.encryptionsdk.AwsCrypto;
import com.amazonaws.encryptionsdk.CryptoResult;
import com.amazonaws.encryptionsdk.kms.KmsMasterKey;
import com.amazonaws.encryptionsdk.kms.KmsMasterKeyProvider;
public class OlHSMKeyManager {


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

    static void testCode(String keyArn, String data){



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

                final String ciphertext = crypto.encryptString(prov, data, context).getResult();
                System.out.println("Ciphertext: " + ciphertext);

                // Decrypt the data
                final CryptoResult<String, KmsMasterKey> decryptResult = crypto.decryptString(prov, ciphertext);

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

                // Now we can return the plaintext data
                System.out.println("Decrypted: " + decryptResult.getResult());
    }

    public static void main(String[] args) {
        String keyId = "arn:aws:kms:eu-west-2:344507690543:key/bacdc930-8eb4-4e37-ba1d-d516596a8091";

        testCode(keyId, keyId);

    }


}
