package network.quant.api;

import network.quant.essential.types.associatedEnums.DltNameOptions;
import network.quant.essential.types.associatedEnums.TransactionTypeOptions;

/**
 * Definition of DLT transaction
 * This is the basic DLT message definition that BPI layer accepts
 */
public interface DltTransaction {

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

}
