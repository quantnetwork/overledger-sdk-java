package network.quant.ethereum.exception;

/**
 *  This exception is thrown when the Ethereum array type is not supported.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class ArrayTypeNotSupportedException extends RuntimeException{
    private String message;

    public ArrayTypeNotSupportedException(String body) {
        this.message = body;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
