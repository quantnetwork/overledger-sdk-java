package network.quant;

import network.quant.api.DLT;
import network.quant.api.OverledgerSDK;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.util.Block;

import java.math.BigDecimal;

final class SDKSearchBlockNo {
//  ---------------------------------------------------------
//  -------------- BEGIN VARIABLES TO UPDATE ----------------
//  ---------------------------------------------------------

private static final  BigDecimal ethereumBlockNo= BigDecimal.valueOf(7189225); // replace with your own data based on which network you are on
private static final  BigDecimal rippleBlockNo = BigDecimal.valueOf(3577213);// replace with your own value (aka ledgerVersion in ripple)
// here the hash is actually the ripple transaction id not the transactionhash of the block


    public static void main(String[] args) {

        try {
            OverledgerSDK sdk  = DefaultOverledgerSDK.newInstance();

            Block ethResponse = sdk.searchBlock(DLT.ethereum.name(), ethereumBlockNo);
            Block rippleResponse = sdk.searchBlock(DLT.ripple.name(),rippleBlockNo);
            System.out.println("ethereum block searched: "+ethResponse.getData());



            System.out.println("ripple block searched: "+rippleResponse.getData());

        }
        catch (Exception e)
        {
            System.out.println("Some error happened in the demo, make sure you have the config file in the src/main/resources set and the mappId set and the transaction hashes above are valid");
            e.printStackTrace();

        }



    }

}
