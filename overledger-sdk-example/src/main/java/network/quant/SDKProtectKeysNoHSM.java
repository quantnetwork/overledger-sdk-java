package network.quant;

import com.amazonaws.encryptionsdk.keyrings.Keyring;
import io.overledger.OlHSMKeyManager;
import network.quant.api.*;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.ethereum.EthereumAccount;
import network.quant.ripple.RippleAccount;
import network.quant.util.DltSequenceRequest;
import network.quant.util.DltSequenceResponse;
import network.quant.util.SequenceRequest;
import network.quant.util.SequenceResponse;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;

final class SDKProtectKeysNoHSM {
//  ---------------------------------------------------------
//  -------------- BEGIN VARIABLES TO UPDATE ----------------
//  ---------------------------------------------------------

// Paste in your ethereum and ripple private keys.
// For Ethereum you can generate an account using `OverledgerSDK.dlts.ethereum.createAccount` then fund the address at the Ropsten Testnet Faucet.
static final String partyAEthereumPrivateKey = "e352ad01a835ec50ba301ed7ffb305555cbf3b635082af140b3864f8e3e443d3";
static final String partyAEthereumAddress = "0x650A87cfB9165C9F4Ccc7B971D971f50f753e761";
// For Ripple, you can go to the official Ripple Testnet Faucet to get an account already funded.
static final String partyARipplePrivateKey = "sswERuW1KWEwMXF6VFpRY72PxfC9b";
static final String partyARippleAddress = "rhTa8RGotyJQAW8sS2tFVVfvcHYXaps9hC";

static final String partyBEthereumAddress = "0x1a90dbb13861a29bFC2e464549D28bE44846Dbe4";
// Keep in mind that for Ripple, the minimum transfer amount is 20XRP (20,000,000 drops), if the address is not yet funded.
static final String partyBRippleAddress = "rHVsZPVPjYJMR3Xa8YH7r7MapS7s5cyqgB";


    public static void main(String[] args) {

        if (args.length<1) {
            System.out.println("Please put the String format private key as first argument in this App. \n e.g. SDKProtect.. sswERuW2KWEwMXF6VFpRY72PxfC9b ");
        }
//        Account ethAcnt = EthereumAccount.getInstance(NETWORK.ROPSTEN, new BigInteger(partyAEthereumPrivateKey, 16), BigInteger.ZERO);
//        Account rplAcnt = RippleAccount.getInstance(NETWORK.TEST, partyARipplePrivateKey,  BigInteger.ONE);
        byte [] keyBytes = OlHSMKeyManager.HSMCrypto.generateRandomKey();
        System.out.println("Store Secret:"+OlHSMKeyManager.HSMCrypto.getStringFromBytes(keyBytes));

        Keyring kr = OlHSMKeyManager.HSMCrypto.simulateSingleKeyRing(keyBytes);
        String partyAEthereumPrivateKey="", partyARipplePrivateKey=""; // you need access to the correct CMK to decrypt
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
