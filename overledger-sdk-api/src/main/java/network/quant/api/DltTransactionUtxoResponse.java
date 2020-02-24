package network.quant.api;

/**
 * Definition of DLT transaction UTXO response
 * This extends the basic DLT transaction response definition
 */
public interface DltTransactionUtxoResponse {

    /**
     * the inputs of this transaction
     * @return TransactionInput[] containing transaction's inputs
     */
    TransactionInput[] getTxInput();

    /**
     * the inputs of this transaction
     * @return TransactionOutput[] containing transaction's outputs
     */
    TransactionOutput[] getTxOutputs();

}
