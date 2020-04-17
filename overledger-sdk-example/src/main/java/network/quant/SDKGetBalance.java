package network.quant;

import network.quant.api.DLT;
import network.quant.api.OverledgerSDK;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.util.BalanceRequest;
import network.quant.util.BalanceResponse;
import network.quant.util.DltSequenceRequest;
import network.quant.util.SequenceResponse;

import java.util.Arrays;
import java.util.List;

final class SDKGetBalance {
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
            DefaultOverledgerSDK.setDefaultLocation("./src/main/resources/context.properties.");
            // examples how you could set the location of the config before creating new SDK
            OverledgerSDK sdk  = DefaultOverledgerSDK.newInstance();
            BalanceRequest balanceRequest = new BalanceRequest(DLT.ethereum.name(), partyAEthereumAddress);
            BalanceRequest balanceRequest2 = new BalanceRequest(DLT.ripple.name(), partyARippleAddress);
            List<BalanceResponse> balanceResponse = sdk.getBalance(Arrays.asList(balanceRequest, balanceRequest2));


            for(BalanceResponse x:balanceResponse){
                System.out.println("The account balance of our " + x.getDlt() + " is: " + x.getValue()+" "+x.getUnit() );
            }

        }
        catch (Exception e){
            System.out.println("Some error happened in the demo, make sure you have the config file in the src/main/resources set and the mappId set!!");
            e.printStackTrace();
               }



    }

}
