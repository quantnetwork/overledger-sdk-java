package network.quant.ripple.exception;

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
