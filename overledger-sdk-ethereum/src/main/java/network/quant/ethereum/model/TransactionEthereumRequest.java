package network.quant.ethereum.model;

import network.quant.api.DltTransactionAccountsRequest;
import network.quant.ethereum.model.associatedEnums.TransactionEthereumSubTypeOptions;

/**
 * Definition of DLT Ethereum transaction request
 * This extends the basic DLT transaction account request definition
 */
public interface TransactionEthereumRequest extends DltTransactionAccountsRequest  {

    /**
     * a redefinition of the TransactionRequest object, to add more Ethereum specific information
     * @return TransactionEthereumSubTypeOptions containing Ethereum transactionSubType
     */
    TransactionEthereumSubTypeOptions getSubType();

    /**
     * Get any additional DLT specific transaction fields,
     * @return EthereumTransactionExtraFields describing the additionalFields
     */
    EthereumTransactionExtraFields getExtraFields();

}
