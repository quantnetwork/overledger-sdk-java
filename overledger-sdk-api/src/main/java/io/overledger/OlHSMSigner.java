package io.overledger;

public class OlHSMSigner {

    String CMK_ARK;

    OlHSMSigner(String CMK){
        CMK_ARK = CMK;

    }

    byte [] sign(byte[] hash ){

        return new byte[2];

    }
}
