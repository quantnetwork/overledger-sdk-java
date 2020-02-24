package network.quant.api;

/**
 * Definition of DLT transaction UTXO request
 * This extends the basic DLT transaction request definition
 */
public interface DltTransactionUtxoRequest extends DltTransactionRequestNew {

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
