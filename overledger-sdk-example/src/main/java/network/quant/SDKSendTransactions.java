package network.quant;

import network.quant.api.*;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.ethereum.EthereumAccount;
import network.quant.ripple.RippleAccount;
import network.quant.util.DltSequenceRequest;
import network.quant.util.DltSequenceResponse;
import network.quant.util.SequenceRequest;
import network.quant.util.SequenceResponse;

import java.math.BigInteger;
import java.util.Arrays;

final class SDKSendTransactions {
//  ---------------------------------------------------------
//  -------------- BEGIN VARIABLES TO UPDATE ----------------
//  ---------------------------------------------------------
static final String mappId = "<YOUR MAPID>";// these values should  be set in the context.properties file
static final String bpiKey = "<ENTER YOUR BPIKEY>";// same as above can be reader from OverledgerContext class

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
        String transactionMessage = "Hello World!";
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
