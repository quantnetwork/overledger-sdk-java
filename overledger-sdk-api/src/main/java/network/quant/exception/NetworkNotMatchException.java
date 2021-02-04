package network.quant.exception;

/**
 *  This exception is thrown when the function call is not matching the network address.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class NetworkNotMatchException extends Exception {

    public NetworkNotMatchException() {
        super("Cannot match the network address");
    }

}
