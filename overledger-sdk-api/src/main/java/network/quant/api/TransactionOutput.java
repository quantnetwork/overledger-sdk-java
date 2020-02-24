package network.quant.api;

/**
 * An generic object used to describe an output to a UTXO transaction
 */
public interface TransactionOutput {

    /**
     * who can spend this output
     * @return String containing the input's assigned address
     */
    String getToAddress();

    /**
     * the amount that is to be sent in this output in the lowest unit for the respective DLT
     * @return Integer containing the output's amount
     */
    Integer getAmount();

    /**
     * the particular asset being moved by this transaction.
     * Do not use if the native asset is being moved (e.g. BTC for Bitcoin)
     * @return Object containing the output's asset (if there is one)
     */
    Object getAsset();

    /**
     * is there a particular enforcement on the spending of this output?
     * Do not use if only the sender's signature is required
     * @return SmartContract containing the output's smart contract (if there is one)
     */
    Object getSmartContract();

}
