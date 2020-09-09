package network.quant.ethereum.exception;

public class FailToParseFunctionParameterException extends RuntimeException {

    private String message;

    public FailToParseFunctionParameterException(Exception e) {
        this.message = e.getMessage();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
