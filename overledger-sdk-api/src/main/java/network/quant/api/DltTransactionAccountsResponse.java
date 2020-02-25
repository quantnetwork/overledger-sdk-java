package network.quant.api;

/**
 * Definition of DLT transaction Accounts response
 * This extends the basic DLT transaction response definition
 */
public interface DltTransactionAccountsResponse {

    /**
     * who sent this transaction
     * @return String containing the transaction sender address
     */
    String getFromAddress();

    /**
     * where was this transaction sent to.
     * @return String containing the transaction receiver address
     */
    String getToAddress();

    /**
     * used to order transactions sent from this address.
     * @return Integer containing the transaction sequence
     */
    Integer getSequence();

    /**
     * the amount sent in this transaction in the lowest unit for the respective DLT.
     * Set to 0 if not appropriate (i.e. no value is being exchanged)
     * @return Integer containing the transaction's amount
     */
    Integer getAmount();

    /**
     * the particular asset moved by this transaction.
     * No need to use if you are transferring the native asset of the distributed ledger (e.g. Eth on Ethereum)
     * @return Object containing the transaction's asset (if there is one)
     */
    Object getAsset();

    /**
     * information the embedded smart contract
     * @return Object containing the input's smart contract (if there is one)
     */
    SmartContract getSmartContract();
}
