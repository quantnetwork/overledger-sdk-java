package network.quant.essential.exception;

/**
 *  This exception is thrown when a illegal key is encountered.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class IllegalKeyException extends Exception {

    public IllegalKeyException() {
        super("DLT type is not found");
    }

}
