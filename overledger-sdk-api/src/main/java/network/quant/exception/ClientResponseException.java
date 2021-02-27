package network.quant.exception;

/**
 *  This exception is thrown when an returning a 4xx or 5xx status.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class ClientResponseException extends Exception {

    private String responseBody;

    public ClientResponseException(String body) {
        this.responseBody = body;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

}
