package network.quant.ripple.experimental;

import network.quant.OverledgerContext;
import network.quant.api.NETWORK;
import network.quant.exception.ClientResponseException;
import network.quant.exception.RedirectException;
import network.quant.ripple.RippleAccount;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Experimental class for helping developer use Overleder Ripple faucet
 */
public class RippleFaucetHelper {

    private static final String BEARER = "Bearer";
    private static final BigDecimal XRP = new BigDecimal("1000000");
    private static RippleFaucetHelper I;
    private WebClient webClient;
    private String url;

    private RippleFaucetHelper() {
        this.webClient = WebClient.builder()
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        String.format("%s %s:%s", BEARER, OverledgerContext.MAPP_ID, OverledgerContext.BPI_KEY)
                )
                .build();
    }

    private void redirectPost(RippleAccount rippleAccount, String uri, BigInteger nonce) {
        this.webClient
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse
                        .bodyToMono(ByteArrayResource.class)
                        .map(ByteArrayResource::getByteArray)
                        .map(String::new)
                        .map(ClientResponseException::new)
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> clientResponse
                        .bodyToMono(ByteArrayResource.class)
                        .map(ByteArrayResource::getByteArray)
                        .map(String::new)
                        .map(ClientResponseException::new)
                )
                .bodyToMono(String.class)
                .doOnSuccess(faucetResponse -> rippleAccount.setNonce(null != nonce?nonce:BigInteger.ONE))
                .doOnError(RuntimeException::new)
                .block();
    }

    /**
     * Fund Ripple address with given amount in DROPs
     * @param rippleAccount RippleAccount containing ripple account
     * @param amount BigDecimal containing amount in DROPS
     * @param nonce BitInteger containing current nonce of account
     */
    public void fundAccount(RippleAccount rippleAccount, BigDecimal amount, BigInteger nonce) {
        if (null != rippleAccount && null != amount && NETWORK.TEST.equals(rippleAccount.getNetwork())) {
            try {
                this.webClient
                        .post()
                        .uri(this.url, rippleAccount.getPublicKey(), amount)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .retrieve()
                        .onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse
                                .bodyToMono(ByteArrayResource.class)
                                .map(ByteArrayResource::getByteArray)
                                .map(String::new)
                                .map(ClientResponseException::new)
                        )
                        .onStatus(HttpStatus::is5xxServerError, clientResponse -> clientResponse
                                .bodyToMono(ByteArrayResource.class)
                                .map(ByteArrayResource::getByteArray)
                                .map(String::new)
                                .map(ClientResponseException::new)
                        )
                        .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header("Location").get(0))))
                        .bodyToMono(String.class)
                        .doOnSuccess(faucetResponse -> rippleAccount.setNonce(null != nonce?nonce:BigInteger.ONE))
                        .block();
            } catch (RedirectException e) {
                this.redirectPost(rippleAccount, e.getUrl(), nonce);
            }
        }
    }

    /**
     * Fund Ripple address with given amount in XRP
     * @param rippleAccount RippleAccount containing ripple account
     * @param amount BigDecimal containing amount in XRP
     */
    public void fundAccount(RippleAccount rippleAccount, BigDecimal amount) {
        this.fundAccount(rippleAccount, amount, BigInteger.ONE);
    }

    public static RippleFaucetHelper getInstance(String url) {
        if (null == I) {
            I = new RippleFaucetHelper();
        }
        I.url = url;
        return I;
    }

}
