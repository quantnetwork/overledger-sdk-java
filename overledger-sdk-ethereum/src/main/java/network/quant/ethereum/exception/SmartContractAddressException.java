package network.quant.ethereum.exception;

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
