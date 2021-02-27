package network.quant.bitcoin.exception;

/**
 *  This exception is thrown when a smart contract query error occurs.
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
