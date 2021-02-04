package network.quant.bitcoin.exception;

/**
 *  This exception is thrown when data could not be extracted from a Bitcoin address.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class BitcoinDataNotMatchingLengthException extends Exception {

    public BitcoinDataNotMatchingLengthException() {
        super(String.format("Cannot extract data from address list with given length"));
    }

}
