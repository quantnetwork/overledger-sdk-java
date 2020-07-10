package network.quant;

import lombok.extern.slf4j.Slf4j;
import network.quant.api.OverledgerSDK;
import network.quant.api.OverledgerTransaction;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.util.StatusRequest;
import network.quant.util.StatusResponse;

import java.time.Instant;
import java.util.UUID;

@Slf4j
public class SDKTrackingFunction {
    public static final String TEST_1 = "test1";

    public static void main(String[] args) {

        DefaultOverledgerSDK.setDefaultLocation("./src/main/resources/context.properties");
        OverledgerSDK sdk = DefaultOverledgerSDK.newInstance();

        UUID goodUUID = UUID.fromString("43451e46-b2ac-11e9-a2a3-2a2ae2dbcce4");

        StatusRequest statusRequest = StatusRequest.builder()
                .mappId(TEST_1)
                .overledgerTransactionId(goodUUID)
                .callbackUrl(OverledgerContext.CALLBACK_URL)
                .timestamp(Instant.now())
                .build();
        try {
            OverledgerTransaction result = sdk.readTransaction(statusRequest.getOverledgerTransactionId());
            System.out.printf("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sub_status = sdk.subscribeStatusUpdate(statusRequest);
        String unSub_status = sdk.unsubscribeStatusUpdate(statusRequest);
        log.info("sub_status: " + sub_status);
        log.info("unSub_status: " + unSub_status);

    }
}
