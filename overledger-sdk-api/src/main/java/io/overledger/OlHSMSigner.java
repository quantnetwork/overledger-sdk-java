package io.overledger;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.GetPublicKeyRequest;
import com.amazonaws.services.kms.model.SignRequest;
import com.amazonaws.services.kms.model.SigningAlgorithmSpec;

import java.nio.ByteBuffer;

public class OlHSMSigner {

    String CMK_ARK;

    public OlHSMSigner(String CMK){
        CMK_ARK = CMK;

    }

    public byte [] sign(byte[] hash ){
        AWSKMS kmsClient = AWSKMSClientBuilder.standard().withRegion("eu-west-2").build();

        ByteBuffer plaintext = ByteBuffer.wrap(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
        SignRequest req = new SignRequest().withKeyId(CMK_ARK).withMessage(plaintext);
        req.withSigningAlgorithm(SigningAlgorithmSpec.ECDSA_SHA_256);
        //GetPublicKeyRequest reqPubKey = new GetPublicKeyRequest().withKeyId(keyId);
        //GetPublicKeyResult pubKeyRes = kmsClient.getPublicKey(reqPubKey);
        ByteBuffer ciphertext = kmsClient.sign(req).getSignature();
        return ciphertext.array();

    }
}
