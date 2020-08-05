package network.quant;

import network.quant.api.OverledgerSDK;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.util.FeeEstimationResponse;

final class SDKFeeEstimation {


    public static void main(String[] args) {

        try {
            OverledgerSDK sdk = DefaultOverledgerSDK.newInstance();

            System.out.println("calling for RIPPLE ---> ");
            FeeEstimationResponse feeEstimationResponse = sdk.getFeeEstimation("bitcoin", "15");

            System.out.println("fee estimation response:" + feeEstimationResponse.getDlt() + ", data = " + feeEstimationResponse.getData());

            FeeEstimationResponse feeEstimationResponseForRipple = sdk.getFeeEstimation("ripple", null);
            System.out.println("fee estimation response for ripple :" + feeEstimationResponseForRipple.getDlt() + ", data = " + feeEstimationResponseForRipple.getData());

        } catch (Exception e) {
            System.out.println("Some error happened in the demo, make sure you have the config file in the src/main/resources set and the mappId set and the transaction hashes above are valid");
            e.printStackTrace();

        }


    }

}
