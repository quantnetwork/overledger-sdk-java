package network.quant.ethereum.exception;

/**
 *  This exception is thrown when the Ethereum smart contract parameters were incorrect.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class SmartContractInputParamsException extends RuntimeException{
    private String message;

    public SmartContractInputParamsException(String body) {
        this.message = body;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
