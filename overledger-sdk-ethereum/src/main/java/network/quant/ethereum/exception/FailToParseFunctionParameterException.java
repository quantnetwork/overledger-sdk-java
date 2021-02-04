package network.quant.ethereum.exception;

/**
 *  This exception is thrown when the Ethereum function parameters could not be parsed.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class FailToParseFunctionParameterException extends RuntimeException {

    private String message;

    public FailToParseFunctionParameterException(Exception e) {
        this.message = e.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
