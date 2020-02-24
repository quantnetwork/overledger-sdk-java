package network.quant.api;

import network.quant.essential.types.associatedEnums.DltNameOptions;
import network.quant.essential.types.associatedEnums.TransactionTypeOptions;

/**
 * Definition of DLT transaction request
 * This is the basic DLT transaction request definition
 */
public interface DltTransactionRequestNew {

    /**
     * Get DLT name, e.g: bitcoin, ethereum, ripple, etc
     * It has to be an acceptable string by BPI layer
     * DLT should be in lower case only
     * @return DltNameOptions containing DLT type
     */
    DltNameOptions getDlt();

    /**
     * Get TransactionType, e.g: utxo or accounts
     * @return TransactionTypeOptions containing transactionType
     */
    TransactionTypeOptions getType();

    /**
     * Get TransactionSubType, e.g: valueTransfer, smartContractDeploy,...
     * @return Object containing transactionSubType (DltSpecific)
     */
    Object getSubType();

    /**
     * Get embedded transaction message,
     * @return String listing the embedded message
     */
    String getMessage();

    /**
     * Get any additional DLT specific transaction fields,
     * @return Object describing the additionalFields
     */
    Object getExtraFields();

}
