package network.quant.exception;

/**
 *  This exception is thrown when no message could be found in the transaction.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class DataNotFoundException extends Exception {

    public DataNotFoundException() {
        super("No Quant data can be found");
    }

}
