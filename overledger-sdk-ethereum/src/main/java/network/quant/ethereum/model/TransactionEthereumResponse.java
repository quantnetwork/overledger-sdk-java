package network.quant.ethereum.model;

import network.quant.ethereum.model.associatedEnums.TransactionEthereumSubTypeOptions;

/**
 * Definition of DLT Ethereum transaction response
 * This extends the basic DLT transaction account response definition
 */
public interface TransactionEthereumResponse {

    /**
     * a redefinition of the TransactionRequest object, to add more Ethereum specific information
     * @return TransactionEthereumSubTypeOptions containing Ethereum transactionSubType
     */
    TransactionEthereumSubTypeOptions getSubType();

    /**
     * Get any additional DLT specific transaction fields,
     * @return Object describing the additionalFields
     */
    EthereumTransactionExtraFields getExtraFields();

}
