package network.quant.ethereum.model;

/**
 * Definition of DLT Ethereum transaction extra fields
 */
public interface EthereumTransactionExtraFields {

    /**
     * the price to pay per gas unit (in wei)
     * @return Integer containing computationalUnitPrice
     */
    String getCompUnitPrice();

    /**
     * the maximum amount of gas units that this transaction can use,
     * so maximum transaction price is: (compUnitPrice*compLimit)
     * @return Integer containing computationalLimit
     */
    String getCompLimit();

}
