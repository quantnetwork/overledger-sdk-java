package network.quant.ripple.model;

import network.quant.api.DltTransactionAccountsRequest;
import network.quant.ripple.model.associatedEnums.TransactionXRPSubTypeOptions;

/**
 * Definition of DLT XRP transaction request
 * This extends the basic DLT transaction account request definition
 */
public interface TransactionXRPRequest extends DltTransactionAccountsRequest {

    /**
     * a redefinition of the TransactionRequest object, to add more Ethereum specific information
     * @return TransactionEthereumSubTypeOptions containing Ethereum transactionSubType
     */
    TransactionXRPSubTypeOptions getSubType();

    /**
     * Get any additional DLT specific transaction fields,
     * @return XRPTransactionExtraFields describing the additionalFields
     */
    XRPTransactionExtraFields getExtraFields();

}
