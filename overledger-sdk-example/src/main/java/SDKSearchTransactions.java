import network.quant.OverledgerContext;
import network.quant.api.*;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.util.Transaction;

final class SDKSearchTransactions {
//  ---------------------------------------------------------
//  -------------- BEGIN VARIABLES TO UPDATE ----------------
//  ---------------------------------------------------------

private static final  String ethereumTransactionHash = "0x9d709c024a0f61d897cbc942c174f94f4d4937fc80a0ef6326a5fb386225c8a5";
private static final  String rippleTransactionHash = "A0A49E56384DDF2846CF5FAE500D2F2CD78533A6201F7A4FE61E1F75521E6215";
// here the hash is actually the ripple transaction id not the transactionhash of the block


    public static void main(String[] args) {

        try {
            OverledgerSDK sdk  = DefaultOverledgerSDK.newInstance();

            Transaction ethResponse = sdk.searchTransaction(ethereumTransactionHash, Transaction.class);
            System.out.println("ethereum transaction searched:"+ethResponse);
            Transaction rippleResponse = sdk.searchTransaction(rippleTransactionHash, Transaction.class);


            System.out.println("ethereum transaction searched:"+rippleResponse);

        }
        catch (Exception e){
            System.out.println("Some error happened in the demo, make sure you have the config file in the src/main/resources set and the mappId set and the transaction hashes above are valid");
            e.printStackTrace();
               }



    }

}
