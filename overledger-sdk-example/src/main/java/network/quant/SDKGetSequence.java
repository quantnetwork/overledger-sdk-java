package network.quant;

import network.quant.api.*;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.util.DltSequenceRequest;
import network.quant.util.DltSequenceResponse;
import network.quant.util.SequenceRequest;
import network.quant.util.SequenceResponse;

import java.util.Arrays;

final class SDKGetSequence {
//  ---------------------------------------------------------
//  -------------- BEGIN VARIABLES TO UPDATE ----------------
//  ---------------------------------------------------------
//static final String mappId = "<YOUR MAPID>";// these values should  be set in the context.properties file
//static final String bpiKey = "<ENTER YOUR BPIKEY>";// same as above can be reader from OverledgerContext class

// Paste in your ethereum and ripple public address. These are valid address for testnet.
static final String partyAEthereumAddress = "0x650A87cfB9165C9F4Ccc7B971D971f50f753e761";
// For Ripple, you can go to the official Ripple Testnet Faucet to get an account already funded.
static final String partyARippleAddress = "rhTa8RGotyJQAW8sS2tFVVfvcHYXaps9hC";




    public static void main(String[] args) {

        try {
            OverledgerSDK sdk  = DefaultOverledgerSDK.newInstance();
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

        }
        catch (Exception e){
            System.out.println("Some error happened in the demo, make sure you have the config file in the src/main/resources set and the mappId set!!");
            e.printStackTrace();
               }



    }

}
