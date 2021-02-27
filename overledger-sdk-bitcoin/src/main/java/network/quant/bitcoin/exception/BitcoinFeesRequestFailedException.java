package network.quant.bitcoin.exception;

/**
 *  This exception is thrown when a Bitcoin fee request fails.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class BitcoinFeesRequestFailedException extends Exception {

    public BitcoinFeesRequestFailedException(String message) {
        super(String.format("Unable to retrieve fee from bitcoinfees.earn.com: %s", message));
    }

}
