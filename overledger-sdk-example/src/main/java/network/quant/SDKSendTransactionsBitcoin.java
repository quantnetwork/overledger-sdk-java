package network.quant;

import network.quant.api.*;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionRequest;

import network.quant.ripple.RippleAccount;
import network.quant.util.DltSequenceRequest;
import network.quant.util.DltSequenceResponse;
import network.quant.util.SequenceRequest;
import network.quant.util.SequenceResponse;

import java.math.BigInteger;
import java.util.Arrays;

final class SDKSendTransactionsBitcoin {
//  ---------------------------------------------------------
//  -------------- BEGIN VARIABLES TO UPDATE ----------------
//  ---------------------------------------------------------
static final String mappId = "<YOUR MAPID>";// these values should  be set in the context.properties file
static final String bpiKey = "<ENTER YOUR BPIKEY>";// same as above can be reader from OverledgerContext class

// Paste in your ethereum and ripple private keys.
// For Ethereum you can generate an account using `OverledgerSDK.dlts.ethereum.createAccount` then fund the address at the Ropsten Testnet Faucet.
static final String partyABitcoinPrivateKey = "cNPDK4z5wGaaDqVpyMosZGzgh4zYLkZLyYSHW5FT91gmfRWxBM6x";//93LzzpRg87qgivX6HHegkfAdUuHkV3pAoY6DAXySHW6c364WdZ3
static final String partyABitcoinAddress = "n1STFs8YeRHstchYTTJum7Dq2AfBvwtBSk";
static String partyATransId ="60cccfb9d9280e71df87368aa1196d64a6a9cb48f3db5e4f86862fb22eeb93c4";
static long bitcoinLinkedIndex = 0;
static long valueBTCInput = 10000;
static int bHeight = 1729149;
    // For Ripple, you can go to the official Ripple Testnet Faucet to get an account already funded.
static final String partyARipplePrivateKey = "sswERuW1KWEwMXF6VFpRY72PxfC9b";
static final String partyARippleAddress = "rhTa8RGotyJQAW8sS2tFVVfvcHYXaps9hC";

static final String partyBBitcoinAddress = "mkCAqQBWR1gymd8hTLkQMQUKakF9RfZnfw"; //93LzzpRg87qgivX6HHegkfAdUuHkV3pAoY6DAXySHW6c364WdZ3
// Keep in mind that for Ripple, the minimum transfer amount is 20XRP (20,000,000 drops), if the address is not yet funded.
static final String partyBRippleAddress = "rHVsZPVPjYJMR3Xa8YH7r7MapS7s5cyqgB";


    public static void main(String[] args) {
        String transactionMessage = "Hello World Testing No Security!";
        Account btcAcnt = BitcoinAccount.getInstance(NETWORK.TEST, partyABitcoinPrivateKey);
        Account rplAcnt = RippleAccount.getInstance(NETWORK.TEST, partyARipplePrivateKey,  BigInteger.ONE);
        btcAcnt.addUtxo(partyATransId, bitcoinLinkedIndex, valueBTCInput, bHeight, partyABitcoinAddress);

        try {
            OverledgerSDK sdk  = DefaultOverledgerSDK.newInstance();
            sdk.addAccount(DLT.bitcoin.name(), btcAcnt);
            sdk.addAccount(DLT.ripple.name(), rplAcnt);
            SequenceRequest sequenceRequest = new SequenceRequest(Arrays.asList(
                    new DltSequenceRequest(DLT.bitcoin, partyABitcoinAddress),
                    new DltSequenceRequest(DLT.ripple, partyARippleAddress)
            ));
            SequenceResponse sequenceResponse = sdk.getSequence(sequenceRequest);
            Number ethereumSequence = null;
            Number rippleSequence = null;

            for(DltSequenceResponse x:sequenceResponse.getDltData()){
                Number numSequence = x.getSequence();
                System.out.println("The transaction sequence of our " + x.getDlt() + " is: " + numSequence );
                if(x.getDlt()==DLT.bitcoin){
                    ethereumSequence = numSequence;
                }else
                    if(x.getDlt()==DLT.ripple){
                        rippleSequence = numSequence;
                    }
            }
            BigInteger btcAmount = BigInteger.valueOf(200); // For this example we are sending nothing
            BigInteger feePrice = BigInteger.valueOf(1000); // Price for each individual gas unit this transaction will consume
            BigInteger feeLimit = BigInteger.valueOf(8000); // The maximum fee that this transaction will use
            // These values need to be packaged inside a DltTransactionRequest from overledger-sdk-essential
            BigInteger xrpAmount = BigInteger.valueOf(1); // For this example we are sending min value of Ripple
            BigInteger xrpFeePrice = BigInteger.valueOf(12); // Minimum feePrice on Ripple is 12 drops.
            //        BigInteger xrpMaxLedgerVersion =
            //        new BigInteger("4294967295"); // This cannot be user set in this alpha version
            //        of the SDK to Integer.Max_VALUE currently in the Java SDK


            DltTransactionRequest btcTransaction = DltTransactionRequest.builder().dlt(DLT.bitcoin.name()).sequence(ethereumSequence.longValue()).message(transactionMessage).fromAddress(partyABitcoinAddress).toAddress(partyBBitcoinAddress).amount(btcAmount).fee(feePrice).feeLimit(feeLimit).build();
            DltTransactionRequest rippleTransaction = DltTransactionRequest.
                    builder().dlt(DLT.ripple.name()).sequence(rippleSequence.longValue()).message(transactionMessage).fromAddress(partyARippleAddress).toAddress(partyBRippleAddress)
                    .amount(xrpAmount).fee(xrpFeePrice).build();
            // A transaction can be signed manually as below but writeTransaction signs on your behalf

            //rplAcnt.sign(partyARippleAddress, partyBRippleAddress, "helloworld",rippleTransaction);

            //btcAcnt.sign(partyAEthereumAddress, partyBEthereumAddress, transactionMessage, ethTransaction);

            OverledgerTransactionRequest overledgerTransactionRequest = OverledgerTransactionRequest.builder().
                    mappId(OverledgerContext.MAPP_ID).dltData(Arrays.asList(btcTransaction,rippleTransaction)).build();
            OverledgerTransaction response = sdk.writeTransaction(overledgerTransactionRequest);
            System.out.println(response);
        }
        catch (Exception e){
            System.out.println("Some error happened in the demo, make sure you have the config file in the src/main/resources set and the mappId set!!");
            e.printStackTrace();
               }



    }

}
