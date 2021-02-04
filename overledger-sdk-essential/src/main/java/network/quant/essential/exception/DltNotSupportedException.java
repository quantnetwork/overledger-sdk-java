package network.quant.essential.exception;

/**
 *  This exception is thrown when a DLT is not supported.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class DltNotSupportedException extends Exception {

    public DltNotSupportedException() {
        super("DLT type is not supported");
    }

}
