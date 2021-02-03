package network.quant.ripple.exception;

/**
 *  This exception is thrown when the Ripple transaction signing failed.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class SignFailedException extends Exception {

    public SignFailedException(String message) {
        super(String.format("Failed to sign the transaction: %s", message));
    }

}
