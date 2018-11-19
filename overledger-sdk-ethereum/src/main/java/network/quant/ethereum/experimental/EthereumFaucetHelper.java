package network.quant.ethereum.experimental;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.quant.OverledgerContext;
import network.quant.ethereum.EthereumAccount;
import network.quant.ethereum.experimental.dto.FaucetResponseDto;
import network.quant.exception.ClientResponseException;
import network.quant.exception.RedirectException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.web3j.crypto.Credentials;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Experimental class for helping developer use Overleder Ethereum faucet
 */
public class EthereumFaucetHelper {

    private static final String BEARER = "Bearer";
    private static EthereumFaucetHelper I;
    private WebClient webClient;
    private String url;

    private EthereumFaucetHelper() {
        this.webClient = WebClient.builder()
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        String.format("%s %s:%s", BEARER, OverledgerContext.MAPP_ID, OverledgerContext.BPI_KEY)
                )
                .build();
    }

    private void redirectPost(EthereumAccount ethereumAccount, String uri) {
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
                .doOnSuccess(result -> ethereumAccount.setNonce(BigInteger.ZERO))
                .doOnError(RuntimeException::new)
                .block();
    }

    /**
     * Found given EthereumAccount with 1 ETH
     * @param ethereumAccount EthereumAccount containing the Ethereum account
     */
    public void fundAccount(EthereumAccount ethereumAccount) {
        try {
            String address = Credentials.create(ethereumAccount.getEcKeyPair()).getAddress();
            this.webClient
                    .post()
                    .uri(this.url, address, new BigDecimal("1000000000000000000"))
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
                    .doOnSuccess(faucetResponseDto -> {
                        System.out.println(faucetResponseDto);
                        ethereumAccount.setNonce(BigInteger.ZERO);
                    })
                    .doOnError(RuntimeException::new)
                    .block();
        } catch (RedirectException e) {
            this.redirectPost(ethereumAccount, e.getUrl());
        }
    }

    public static EthereumFaucetHelper getInstance(String url) {
        if (null == I) {
            I = new EthereumFaucetHelper();
        }
        I.url = url;
        return I;
    }

}
