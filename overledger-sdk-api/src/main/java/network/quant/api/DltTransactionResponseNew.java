package network.quant.api;

import network.quant.essential.types.associatedEnums.DltNameOptions;
import network.quant.essential.types.associatedEnums.TimeStampUnitOptions;
import network.quant.essential.types.associatedEnums.TransactionStatusOptions;
import network.quant.essential.types.associatedEnums.TransactionTypeOptions;

/**
 * Definition of DLT transaction response
 * This is the basic DLT transaction response definition
 */
public interface DltTransactionResponseNew {

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
     * Get transaction id,
     * @return String with the transaction ID (e.g. hash)
     */
    String getId();

    /**
     * Get transaction timestamp,
     * @return String timestamp
     */
    String getTimestamp();

    /**
     * Get transaction timestamp units,
     * @return TimeStampUnitOptions timestamp
     */
    TimeStampUnitOptions getTimestampUnit();

    /**
     * Get transaction status options,
     * @return TransactionStatusOptions the status
     */
    TransactionStatusOptions getStatus();

    /**
     * Get transaction signatures,
     * @return String[] the signatures
     */
    String[] getSignatures();

    /**
     * Get any additional DLT specific transaction fields,
     * @return Object describing the additionalFields
     */
    Object getExtraFields();

}
