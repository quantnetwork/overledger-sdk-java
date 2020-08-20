package network.quant.ethereum.exception;

public class ArrayTypeNotSupportedException extends RuntimeException{
    private String message;

    public ArrayTypeNotSupportedException(String body) {
        this.message = body;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
