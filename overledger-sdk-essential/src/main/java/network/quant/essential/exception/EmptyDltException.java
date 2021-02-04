package network.quant.essential.exception;

/**
 *  This exception is thrown when no DLT was found.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class EmptyDltException extends Exception {

    public EmptyDltException() {
        super("No DLT found");
    }

}
