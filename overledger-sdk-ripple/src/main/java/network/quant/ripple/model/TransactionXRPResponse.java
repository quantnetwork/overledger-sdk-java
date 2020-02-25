package network.quant.ripple.model;

import network.quant.api.DltTransactionAccountsResponse;
import network.quant.ripple.model.associatedEnums.TransactionXRPSubTypeOptions;

/**
 * Definition of DLT XRP transaction response
 * This extends the basic DLT transaction account response definition
 */
public interface TransactionXRPResponse extends DltTransactionAccountsResponse {

    /**
     * a redefinition of the TransactionRequest object, to add more Ethereum specific information
     * @return TransactionEthereumSubTypeOptions containing Ethereum transactionSubType
     */
    TransactionXRPSubTypeOptions getSubType();

    /**
     * Get any additional DLT specific transaction fields,
     * @return Object describing the additionalFields
     */
    XRPTransactionExtraFields getExtraFields();

}
