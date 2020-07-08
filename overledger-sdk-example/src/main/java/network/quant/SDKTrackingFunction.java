package network.quant;

import lombok.extern.slf4j.Slf4j;
import network.quant.api.OverledgerSDK;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.util.Status;
import network.quant.util.StatusResponse;

import java.util.UUID;

@Slf4j
public class SDKTrackingFunction {

    public static void main(String[] args) {
        UUID OVLTrxId = UUID.fromString("173445d7-765e-4a8f-b4fb-96feecba8152");
        UUID nullUUID = null;

        DefaultOverledgerSDK.setDefaultLocation("./src/main/resources/context.properties");
        OverledgerSDK sdk = DefaultOverledgerSDK.newInstance();

        Status status = sdk.getStatusFunction(nullUUID, StatusResponse.class);
        log.info("status: " + status.getStatus() + "\t" + status.getCode() + "\t" + status.getMessage() + "\t" + status.getTimestamp());
    }
}
