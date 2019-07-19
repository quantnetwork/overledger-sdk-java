package network.quant.api;

import java.math.BigInteger;

/**
 * Definition of DLT transaction request
 * This is the basic DLT request message definition that BPI layer accepts
 */
public interface DltTransactionRequest extends DltTransaction {

    /**
     * Get sender address
     * @return String containing the address
     */
    String getFromAddress();

    /**
     * Get receiver address
     * @return String containing the address
     */
    String getToAddress();

    /**
     * Get change address (optional)
     * @return String containing the address
     */
    String getChangeAddress();

    /**
     * Get the message that needs to be send or received
     * Depends on DLT, different maximum length restriction might apply
     * Message can be HEX encoded string with DATA_TYPE as BYTE, encoding and decoding is offline process at Account level
     * @return String containing the message
     */
    String getMessage();

    /**
     * Get paying amount (optional: for DLT which has currency support)
     * There is NO unit field/property in DLT message, we are presuming all currency values are in their smallest unit
     * It does NOT support decimal point
     * @return BigInteger containing the amount
     */
    BigInteger getAmount();

    /**
     * Get transaction fee (optional: for DLT which has currency support)
     * There is NO unit field/property in DLT message, we are presuming all currency values are on their smallest unit
     * It does NOT support decimal point
     * @return BigInteger containing the fee value in smallest unit
     */
    BigInteger getFee();

    /**
     * Get fee limit (optional: for DLT which has currency support)
     * There is NO unit field/property in DLT message, we are presuming all currency values are on their smallest unit
     * There is NO unit field/property in DLT message, we are presuming all currency values are on their smallest unit
     * It does NOT support decimal point
     * @return BigInteger containing the fee limit
     */
    BigInteger getFeeLimit();

    /**
     * Get callback URL
     * By supplying the endpoint of callback service, BPI layer is able to callback on this endpoint for transaction feedback
     * @return String containing callback URL
     */
    String getCallbackUrl();

    /**
     * Get pre-siged HEX encoded transaction dump
     * @return String containing signed transaction
     */
    SignedTransaction getSignedTransaction();

    /**
     * Set pre-signed transaction
     * Accepting DLT level raw transaction in HEX encoded format
     * @param signedTransaction String containing signed transaction
     */
    void setSignedTransaction(SignedTransaction signedTransaction);

    /**
     * Get sequence of from address
     * @return Long containing sequence
     */
    Long getSequence();

}
