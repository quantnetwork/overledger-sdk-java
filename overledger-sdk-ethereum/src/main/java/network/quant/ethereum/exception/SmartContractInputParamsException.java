package network.quant.ethereum.exception;

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
