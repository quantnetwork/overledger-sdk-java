package network.quant.exception;

/**
 *  This exception is thrown when the redirection failed.
 *
 *  @author     Quant network
 *  @see        <a href="http://quant.network">http://quant.network</a>
 */
public class RedirectException extends RuntimeException {

    private String url;

    public RedirectException(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

}
