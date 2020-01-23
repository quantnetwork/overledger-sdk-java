package network.quant;

import network.quant.api.Account;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerSDK;
import network.quant.api.OverledgerTransaction;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.ethereum.EthereumAccount;
import network.quant.mvp.Factory;
import network.quant.mvp.impl.ApplicationFactory;
import network.quant.sdk.OverledgerSDKHelper;

import javax.validation.constraints.AssertTrue;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.Properties;
import java.util.UUID;

final class BasicSDKTestGetTransactions {
    private static final String ETHEREUM_RECEIVE_ADDRESS = "0x82e29E5AA37194710fab02D0194f9a45ff3E961C";
    private static final String RIPPLE_RECEIVE_ADDRESS = "rJG76zwHcYo1zaniE8YNNVE6CAF97a8Ttt";

    //static { Factory.I.config(); }

    public static void main(String[] args) {

        ApplicationFactory a = (ApplicationFactory)ApplicationFactory.getInstance();
        OverledgerSDKHelper sdkh = OverledgerSDKHelper.getInstance(a);
        UUID ovlId = null;

        try {

            OverledgerSDK h  = DefaultOverledgerSDK.newInstance();

            var ovTrans = h.readTransactions("network.quant.software");
            int cnt=0;
            for(OverledgerTransaction t : ovTrans.getTransactions()){
                ++cnt;
                System.out.println(cnt);
                ovlId = t.getOverledgerTransactionId();
                System.out.println(ovlId);

            }

        }
        catch (Exception e){
            System.out.println("Could not read transactions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
        try
        {
            OverledgerSDK h  = DefaultOverledgerSDK.newInstance(NETWORK.TEST);
            var xa  = h.readTransaction(UUID.fromString("35ceadbe-80d3-471a-878f-c8da58e19967"));
            System.out.println(xa);
        }

        catch (Exception e){
            System.out.println("Could not read transaction!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }

    }

}
