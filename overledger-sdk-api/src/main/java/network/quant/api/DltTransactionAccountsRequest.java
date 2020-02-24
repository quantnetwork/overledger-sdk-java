package network.quant.api;

/**
 * Definition of DLT transaction Accounts request
 * This extends the basic DLT transaction request definition
 */
public interface DltTransactionAccountsRequest extends DltTransactionRequestNew {

    /**
     * who is sending this transaction
     * @return String containing the transaction sender address
     */
    String getFromAddress();

    /**
     * where is this transaction being sent to.
     * @return String containing the transaction receiver address
     */
    String getToAddress();

    /**
     * used to order transactions sent from this address. You should set the sequence to the next increment value
     * @return Integer containing the transaction sequence
     */
    Integer getSequence();

    /**
     * the amount that is to be sent in this transaction in the lowest unit for the respective DLT.
     * Set to 0 if not appropriate (i.e. no value is being exchanged)
     * @return Integer containing the transaction's amount
     */
    Integer getAmount();

    /**
     * the particular asset being moved by this transaction.
     * No need to use if you are transferring the native asset of the distributed ledger (e.g. Eth on Ethereum)
     * @return Object containing the transaction's asset (if there is one)
     */
    Object getAsset();

    /**
     * information on what smart contract you want to deploy or invoke with this transaction
     * @return Object containing the input's smart contract (if there is one)
     */
    SmartContract getSmartContract();

}
