package network.quant;

import io.crypto.overledger.OlHSMKeyManager;
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
import com.amazonaws.encryptionsdk.keyrings.Keyring;
import org.web3j.utils.Numeric;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

final class SDKSendTransactionsHSM {
//  ---------------------------------------------------------
//  -------------- BEGIN VARIABLES TO UPDATE ----------------
//  ---------------------------------------------------------
// "<YOUR MAPID>";// these values should  be set in the context.properties file
// "<ENTER YOUR BPIKEY>";// same as above can be reader from OverledgerContext class

// Paste in your ethereum and ripple private keys.
// For Ethereum you can generate an account using `OverledgerSDK.dlts.ethereum.createAccount` then fund the address at the Ropsten Testnet Faucet.
static final String encryptedPartyAEthereumPrivateKey = "AYADeN5rn9LmN06omjg9Q4d7tI4AhQADABVhd3MtY3J5cHRvLXB1YmxpYy1rZXkAREFocnR5OXlPT3R4ZHlzalcrMy9qQmd4ak0rYUJnZDZnajNqVVVyRWdoWGlMTUxRWE1SWUpHNC9UaVplR3ZadmN4Zz09AApvdmVybGVkZ2VyAAdqYXZhU0RLAAd2ZXJzaW9uAAZhbHBoYTQAAQAHYXdzLWttcwBLYXJuOmF3czprbXM6ZXUtd2VzdC0yOjM0NDUwNzY5MDU0MzprZXkvYmFjZGM5MzAtOGViNC00ZTM3LWJhMWQtZDUxNjU5NmE4MDkxALgBAgEAeDqFb2CIvt2Jn53pyNe3GvlDD1gbPTTNStRlkmAL0cnOAYx/R14YmYkyBmBwR7om95oAAAB+MHwGCSqGSIb3DQEHBqBvMG0CAQAwaAYJKoZIhvcNAQcBMB4GCWCGSAFlAwQBLjARBAytJNY2UHV017DjD0QCARCAO/p+mfTG4/KRCjYduh5YdNMSE2Dlhwu2hPRxYB0/YvGkludmcZR3Gnto7Gd/aplypWL/byny7dme4EgNAgAAAAAMAAAQAAAAAAAAAAAAAAAAAL+ab8CozHa+fzWqt6ChjxL/////AAAAAQAAAAAAAAAAAAAAAQAAAEDCQ0MS8bRPTEjHLVsGOfRFqCI50JERUBa+AzwWig7kDs0ux2HY8xOmmhEYI3PRU4bFDI/FFqPZUzmaLNTilVOjWpxqNKig7e8DOwmIxyzNAQBnMGUCMQDRii+8zg/fEhbF7PVsxBBor61aY/0CRa/KRDuiBKIPPbnMLKzrI5rbpRxSG439L7ECMFmfTyAvxeUrhktNCs0pq+tSPTypPulbCiKByO6Zco+lz7Mc4vbFZNCCIvMYuilfgw";
static final String partyAEthereumAddress = "0x650A87cfB9165C9F4Ccc7B971D971f50f753e761";
// For Ripple, you can go to the official Ripple Testnet Faucet to get an account already funded.
static final String encryptedPartyARipplePrivateKey = "AYADeJaxasxnS4RdDWa/a9mdAe8AhQADABVhd3MtY3J5cHRvLXB1YmxpYy1rZXkAREF6eno1anVTR1N4eWllNVFaVnVteERvZGU4aXlhRE5lZkFIWFVuOGFnRCsvVVI0T1B4Y29KaExudVVKd3VKSkp6UT09AApvdmVybGVkZ2VyAAdqYXZhU0RLAAd2ZXJzaW9uAAZhbHBoYTQAAQAHYXdzLWttcwBLYXJuOmF3czprbXM6ZXUtd2VzdC0yOjM0NDUwNzY5MDU0MzprZXkvYmFjZGM5MzAtOGViNC00ZTM3LWJhMWQtZDUxNjU5NmE4MDkxALgBAgEAeDqFb2CIvt2Jn53pyNe3GvlDD1gbPTTNStRlkmAL0cnOAYOd96T8ZCiiji6CBbDsXWkAAAB+MHwGCSqGSIb3DQEHBqBvMG0CAQAwaAYJKoZIhvcNAQcBMB4GCWCGSAFlAwQBLjARBAy3+XRwRTQ6cGQkcZwCARCAO3Bx9er+bejil5rcKaSzK4cSJ77ZINjCrf4kX9smT0gcC97pv5kJjr4/fbK92rxcO2uNsvhm8GQSsYF3AgAAAAAMAAAQAAAAAAAAAAAAAAAAAAJT7/TdxGn9g+mz7J+x1P3/////AAAAAQAAAAAAAAAAAAAAAQAAAB1ZaMUJla3o5PTFV3XrCs83tSDU24gZvVlO2YFc7mf6Nn0BLk+Kq+Kzp98x0gkAZzBlAjEApLUrM7+BRgqP/KVLOx8nlRa4+UdO+3FZRE7DVhj/EmsXCfWue63j7qb+oMgmXnSNAjAqg7p9CeqqmDGEMbI0FDA2tjwulMx97CRHVhTjI4tNnIsTJY7QI7SPBFxzh9CRjV8";
static final String partyARippleAddress = "rhTa8RGotyJQAW8sS2tFVVfvcHYXaps9hC";
static final String partyBEthereumAddress = "0x1a90dbb13861a29bFC2e464549D28bE44846Dbe4";
// Keep in mind that for Ripple, the minimum transfer amount is 20XRP (20,000,000 drops), if the address is not yet funded.
static final String partyBRippleAddress = "rHVsZPVPjYJMR3Xa8YH7r7MapS7s5cyqgB";


    public static void main(String[] args) {
        String transactionMessage = "Hello World!";
        Keyring kr = OlHSMKeyManager.HSMCrypto.getKeyRingFromCMK("arn:aws:kms:eu-west-2:344507690543:key/bacdc930-8eb4-4e37-ba1d-d516596a8091");
        String partyAEthereumPrivateKey="", partyARipplePrivateKey=""; // you need access to the correct CMK to decrypt
        OlHSMKeyManager hsmKeyManager = new OlHSMKeyManager(kr);
        try {
            partyARipplePrivateKey =  hsmKeyManager.decryptPrivateKeyHexString(encryptedPartyARipplePrivateKey);
            partyAEthereumPrivateKey = hsmKeyManager.decryptPrivateKeyHexString(encryptedPartyAEthereumPrivateKey);
            System.out.println("Encrypted Key *********************************************");
            System.out.println(encryptedPartyAEthereumPrivateKey);
            System.out.println("Decrypted Key *********************************************");
            System.out.println(partyAEthereumPrivateKey);
            System.out.println(partyARipplePrivateKey);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        Account ethAcnt = EthereumAccount.getInstance(NETWORK.ROPSTEN, new BigInteger(partyAEthereumPrivateKey, 16), BigInteger.ZERO);
        Account rplAcnt = RippleAccount.getInstance(NETWORK.TEST, partyARipplePrivateKey,  BigInteger.ONE);

        try {
            OverledgerSDK sdk  = DefaultOverledgerSDK.newInstance();
            sdk.addAccount("ethereum", ethAcnt);
            sdk.addAccount("ripple", rplAcnt);
            SequenceRequest sequenceRequest = new SequenceRequest(Arrays.asList(
                    new DltSequenceRequest(DLT.ethereum, partyAEthereumAddress),
                    new DltSequenceRequest(DLT.ripple, partyARippleAddress)
            ));
            SequenceResponse sequenceResponse = sdk.getSequence(sequenceRequest);
            Number ethereumSequence = null;
            Number rippleSequence = null;

            for(DltSequenceResponse x:sequenceResponse.getDltData()){
                Number numSequence = x.getSequence();
                System.out.println("The transaction sequence of our " + x.getDlt() + " is: " + numSequence );
                if(x.getDlt()==DLT.ethereum){
                    ethereumSequence = numSequence;
                }else
                    if(x.getDlt()==DLT.ripple){
                        rippleSequence = numSequence;
                    }
            }
            BigInteger ethAmount = BigInteger.valueOf(0); // For this example we are sending nothing
            BigInteger feePrice = BigInteger.valueOf(10000); // Price for each individual gas unit this transaction will consume
            BigInteger feeLimit = BigInteger.valueOf(80000); // The maximum fee that this transaction will use
            // These values need to be packaged inside a DltTransactionRequest from overledger-sdk-essential
            BigInteger xrpAmount = BigInteger.valueOf(1); // For this example we are sending min value of Ripple
            BigInteger xrpFeePrice = BigInteger.valueOf(12); // Minimum feePrice on Ripple is 12 drops.
            //        BigInteger xrpMaxLedgerVersion =
            //        new BigInteger("4294967295"); // This cannot be user set in this alpha version
            //        of the SDK to Integer.Max_VALUE currently in the Java SDK


            DltTransactionRequest ethTransaction = DltTransactionRequest.builder().dlt("ethereum").sequence(ethereumSequence.longValue()).message(transactionMessage).fromAddress(partyAEthereumAddress).toAddress(partyBEthereumAddress).amount(ethAmount).fee(feePrice).feeLimit(feeLimit).build();
            DltTransactionRequest rippleTransaction = DltTransactionRequest.
                    builder().dlt("ripple").sequence(rippleSequence.longValue()).message(transactionMessage).fromAddress(partyARippleAddress).toAddress(partyBRippleAddress)
                    .amount(xrpAmount).fee(xrpFeePrice).build();
            // A transaction can be signed manually as below but writeTransaction signs on your behalf

            //rplAcnt.sign(partyARippleAddress, partyBRippleAddress, "helloworld",rippleTransaction);

            //ethAcnt.sign(partyAEthereumAddress, partyBEthereumAddress, transactionMessage, ethTransaction);

            OverledgerTransactionRequest overledgerTransactionRequest = OverledgerTransactionRequest.builder().
                    mappId(OverledgerContext.MAPP_ID).dltData(Arrays.asList(ethTransaction,rippleTransaction)).build();
            OverledgerTransaction response = sdk.writeTransaction(overledgerTransactionRequest);
            System.out.println(response);
        }
        catch (Exception e){
            System.out.println("Some error happened in the demo, make sure you have the config file in the src/main/resources set and the mappId set!!");
            e.printStackTrace();
               }



    }

}
