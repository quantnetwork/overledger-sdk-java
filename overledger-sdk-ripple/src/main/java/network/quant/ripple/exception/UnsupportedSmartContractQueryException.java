package network.quant.ripple.exception;

/**
 *  This exception is thrown when the Ripple smart contract query failed.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class UnsupportedSmartContractQueryException extends RuntimeException {
    private String message;

    public UnsupportedSmartContractQueryException(String body) {
        this.message = body;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
