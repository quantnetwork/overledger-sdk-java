package network.quant.essential.exception;

/**
 *  This exception is thrown when the DLT account is not found.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class EmptyAccountException extends Exception {

    public EmptyAccountException() {
        super("DLT account is not found");
    }

}
