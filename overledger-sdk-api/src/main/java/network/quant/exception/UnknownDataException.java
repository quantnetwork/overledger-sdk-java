package network.quant.exception;

/**
 *  This exception is thrown when an unknown data exception occurs.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class UnknownDataException extends Exception {

    public UnknownDataException() {
        super("No Quant data found");
    }

}
