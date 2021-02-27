package network.quant.exception;

/**
 *  This exception is thrown when the transaction data is over the expected size.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class DataOverSizeException extends Exception {

    public DataOverSizeException() {
        super("Given data is over sized");
    }

}
