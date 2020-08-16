package network.quant.ethereum.exception;

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
