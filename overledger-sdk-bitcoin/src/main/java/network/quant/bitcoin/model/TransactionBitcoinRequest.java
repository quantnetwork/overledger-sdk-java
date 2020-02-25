package network.quant.bitcoin.model;

import network.quant.api.DltTransactionUtxoRequest;
import network.quant.bitcoin.model.associatedEnums.TransactionBitcoinSubTypeOptions;

/**
 * Definition of DLT Bitcoin transaction request
 * This extends the basic DLT transaction utxo request definition
 */
public interface TransactionBitcoinRequest extends DltTransactionUtxoRequest {

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
