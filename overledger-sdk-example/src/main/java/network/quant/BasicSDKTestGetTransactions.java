package network.quant;

import network.quant.api.*;
import network.quant.essential.DefaultOverledgerSDK;
import java.util.UUID;

final class BasicSDKTestGetTransactions {

    public static void main(String[] args) {

        UUID ovlId = null;

        try {

            OverledgerSDK h  = DefaultOverledgerSDK.newInstance();

            OverledgerTransactions ovTrans = h.readTransactions(OverledgerContext.MAPP_ID);
            int cnt=0;
            for(OverledgerTransaction t : ovTrans.getTransactions()){
                ++cnt;
                System.out.println(cnt);
                ovlId = t.getOverledgerTransactionId();
                System.out.println(ovlId);

            }

        }
        catch (Exception e){
            System.out.println("Error in getting last 25 transactions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
        try
        {
            OverledgerSDK h  = DefaultOverledgerSDK.newInstance(NETWORK.TEST);
            System.out.println("Getting transaction details for "+ ovlId);
            OverledgerTransaction xa  = h.readTransaction(ovlId);
            System.out.println(xa);
        }

        catch (Exception e){
            System.out.println("Could not read last transaction!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }

    }

}
