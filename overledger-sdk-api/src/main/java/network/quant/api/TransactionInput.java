package network.quant.api;

/**
 * An generic object used to describe an input to a UTXO transaction
 */
public interface TransactionInput {

    /**
     * the previous transaction that contains the output you want to use
     * @return String containing the linked transaction
     */
    String getLinkedTx();

    /**
     * the index (in the linked previous transaction) of the output you want to use
     * @return String containing the linked transaction index
     */
    String getLinkedIndex();

    /**
     * who can spend the linked output
     * @return String containing the input's assigned address
     */
    String getFromAddress();

    /**
     * the amount that is contained in the referenced output in the lowest unit for the respective DLT.
     * Set to 0 if not appropriate (i.e. no value is being exchanged)
     * @return Integer containing the input's amount
     */
    Integer getAmount();

    /**
     * the particular asset being moved by this transaction.
     * Do not use if the native asset is being moved (e.g. BTC for Bitcoin)
     * @return Object containing the input's asset (if there is one)
     */
    Object getAsset();

    /**
     * is there a particular enforcement on the spending of this input?
     * Do not use if only the sender's signature is required
     * @return SmartContract containing the input's smart contract (if there is one)
     */
    SmartContract getSmartContract();

}
