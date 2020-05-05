package network.quant;

import com.amazonaws.encryptionsdk.keyrings.Keyring;
import network.quant.security.OlHSMKeyManager;
import network.quant.api.*;
import network.quant.essential.DefaultOverledgerSDK;

import java.io.UnsupportedEncodingException;

final class SDKProtectKeysNoHSM {
//  ---------------------------------------------------------
//  -------------- BEGIN VARIABLES TO UPDATE ----------------
//  ---------------------------------------------------------

// Paste in your ethereum and ripple private keys.
// For Ethereum you can generate an account using `OverledgerSDK.dlts.ethereum.createAccount` then fund the address at the Ropsten Testnet Faucet.
static String partyAEthereumPrivateKey = "e352ad01a835ec50ba301ed7ffb305555cbf3b635082af140b3864f8e3e443d3";
static final String partyAEthereumAddress = "0x650A87cfB9165C9F4Ccc7B971D971f50f753e761";
// For Ripple, you can go to the official Ripple Testnet Faucet to get an account already funded.
static String partyARipplePrivateKey = "sswERuW1KWEwMXF6VFpRY72PxfC9b";
static final String partyARippleAddress = "rhTa8RGotyJQAW8sS2tFVVfvcHYXaps9hC";

static final String partyBEthereumAddress = "0x1a90dbb13861a29bFC2e464549D28bE44846Dbe4";
// Keep in mind that for Ripple, the minimum transfer amount is 20XRP (20,000,000 drops), if the address is not yet funded.
static final String partyBRippleAddress = "rHVsZPVPjYJMR3Xa8YH7r7MapS7s5cyqgB";


    public static void main(String[] args) {

        if (args.length<1) {
            System.out.println("Please put the String format private key as first argument in this App. \n e.g. SDKProtect.. sswERuW2KWEwMXF6VFpRY72PxfC9b ");

        }else{
            partyAEthereumPrivateKey = args[1];
        }
        byte [] keyBytes = OlHSMKeyManager.Crypto.generateRandomKey();
        OverledgerSDK sdk  = DefaultOverledgerSDK.newInstance(); // initialize overledger context
        String ovlSecret = OverledgerContext.config.getProperty("overledger.secret","");
        if (!ovlSecret.equals("")) {
            System.out.println("using configured secret");
            keyBytes = OlHSMKeyManager.Crypto.getBytesFromString(ovlSecret);
        }
        System.out.println("Using this Secret:"+OlHSMKeyManager.Crypto.getStringFromBytes(keyBytes));

        Keyring kr = OlHSMKeyManager.Crypto.simulateSingleKeyRing(keyBytes);
        OlHSMKeyManager hsmKeyManager = new OlHSMKeyManager(kr);
        try {
             String encryptedKey = hsmKeyManager.encryptPrivateKeyHexString(partyAEthereumPrivateKey);
             System.out.println("Encrypted Key:" + encryptedKey);
             String decryptedKey = hsmKeyManager.decryptPrivateKeyHexString(encryptedKey);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        }



        System.out.println("Decrypted Key *********************************************");
            System.out.println(partyAEthereumPrivateKey);
    }


}
