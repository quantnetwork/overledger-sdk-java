package network.quant;

import network.quant.api.OverledgerSDK;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.util.FeeEstimationResponse;

/**
 * Simple example on getting a new Oauth2 AccessToken using the Refresh Token in the properties file
 */
final class SDKOauthSample {

    public static void main(String[] args) {
        try {
            OverledgerSDK sdk = DefaultOverledgerSDK.newInstance();

            System.out.println("Result after refreshing: " + sdk.refreshAccessToken());

        } catch (Exception e) {
            System.out.println("Some error occurred in the demo, make sure you have the config file in the src/main/resources set and the oauth2 values set in the context.properties");
            e.printStackTrace();
        }
    }
}