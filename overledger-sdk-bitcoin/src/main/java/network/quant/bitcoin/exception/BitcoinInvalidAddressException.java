package network.quant.bitcoin.exception;

/**
 *  This exception is thrown when a Bitcoin address could not be verified.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class BitcoinInvalidAddressException extends Exception {

    public BitcoinInvalidAddressException(String address) {
        super(String.format("Unable to verify address: %s", address));
    }

}
