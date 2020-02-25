package network.quant.bitcoin.model;

/**
 * Definition of DLT Ethereum transaction extra fields
 */
public interface BitcoinTransactionExtraFields {

    /**
     * The fee to pay for this transaction to enter the Bitcoin blockchain.
     * It is denoted in Satoshis.
     * @return String containing the fee price
     */
    String getFeePrice();

}
