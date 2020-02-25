package network.quant.ripple.model;

/**
 * Definition of DLT XRP transaction extra fields
 */
public interface XRPTransactionExtraFields {

    /**
     * The fee to pay for this transaction to enter the XRP ledger.
     * It is denoted in drops where the current minimum allowed is 12.
     * @return Integer containing the fee price
     */
    String getFeePrice();

    /**
     * The maximum ledger version the transaction can be included in
     * @return String containing max ledger version
     */
    String getMaxLedgerVersion();

}
