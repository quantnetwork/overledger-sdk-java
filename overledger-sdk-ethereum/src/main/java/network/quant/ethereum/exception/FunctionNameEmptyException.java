package network.quant.ethereum.exception;

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
