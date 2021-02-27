package network.quant.ethereum.exception;

/**
 *  This exception is thrown when the Ethereum contract address could not be reached.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class SmartContractAddressException extends RuntimeException{
    private String message;

    public SmartContractAddressException(String body) {
        this.message = body;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
