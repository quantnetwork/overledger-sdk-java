package network.quant.test;

import lombok.AllArgsConstructor;
import lombok.Value;
import network.quant.OverledgerContext;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerSDK;
import network.quant.essential.DefaultOverledgerSDK;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Util {

    OverledgerSDK overledgerSDK;

     Properties properties = new Properties();

    public  void checkOverledgerSDK() {
        if (null == overledgerSDK) {
            overledgerSDK = DefaultOverledgerSDK.newInstance(NETWORK.TEST);
        }
    }

    public  void loadContext(InputStream stream) {
        if (null != stream) {
            try {
                properties.load(stream);
                OverledgerContext.loadContext(properties);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  Logger logger = Logger.getLogger("OverledgerLogs");
    public  FileHandler fh;

    public  void setupLogger() {
        try {
            fh = new FileHandler("overledger.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info("------ CAR Shop ------");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

@AllArgsConstructor
@Value
class Car {
    String name;
    BigDecimal amount;
}