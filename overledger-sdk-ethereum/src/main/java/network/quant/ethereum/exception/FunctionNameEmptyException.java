package network.quant.ethereum.exception;

/**
 *  This exception is thrown when the Ethereum function name is empty.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class FunctionNameEmptyException extends RuntimeException{
    private String message;

    public FunctionNameEmptyException(String body) {
        this.message = body;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
