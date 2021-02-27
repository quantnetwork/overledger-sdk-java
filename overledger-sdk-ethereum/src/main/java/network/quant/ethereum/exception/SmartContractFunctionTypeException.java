package network.quant.ethereum.exception;

/**
 *  This exception is thrown when the Ethereum smart contract could not be retrieved.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class SmartContractFunctionTypeException extends RuntimeException{
    private String message;

    public SmartContractFunctionTypeException(String body) {
        this.message = body;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
