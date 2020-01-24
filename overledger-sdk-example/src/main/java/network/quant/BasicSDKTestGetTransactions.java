package network.quant;

import network.quant.api.Account;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerSDK;
import network.quant.api.OverledgerTransaction;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.ethereum.EthereumAccount;
import network.quant.mvp.impl.ApplicationFactory;
import java.util.UUID;

final class BasicSDKTestGetTransactions {


    //static { Factory.I.config(); }

    public static void main(String[] args) {

        ApplicationFactory a = (ApplicationFactory)ApplicationFactory.getInstance();
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
            System.out.println("Error in getting last 25 transactions!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
        try
        {
            OverledgerSDK h  = DefaultOverledgerSDK.newInstance(NETWORK.TEST);
            System.out.println("Getting transaction details for "+ ovlId);
            var xa  = h.readTransaction(ovlId);
            System.out.println(xa);
        }

        catch (Exception e){
            System.out.println("Could not read last transaction!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }

    }

}
