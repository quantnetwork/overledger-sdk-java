package network.quant.bitcoin.model;

import network.quant.api.DltTransactionUtxoResponse;
import network.quant.bitcoin.model.associatedEnums.TransactionBitcoinSubTypeOptions;

/**
 * Definition of DLT Bitcoin transaction response
 * This extends the basic DLT transaction utxo response definition
 */
public interface TransactionBitcoinResponse extends DltTransactionUtxoResponse {

    /**
     * a redefinition of the TransactionRequest object, to add more Bitcoin specific information
     * @return TransactionBitcoinSubTypeOptions containing Bitcoin transactionSubType
     */
    TransactionBitcoinSubTypeOptions getSubType();

    /**
     * Get any additional DLT specific transaction fields,
     * @return BitcoinTransactionExtraFields describing the additionalFields
     */
    BitcoinTransactionExtraFields getExtraFields();


}
