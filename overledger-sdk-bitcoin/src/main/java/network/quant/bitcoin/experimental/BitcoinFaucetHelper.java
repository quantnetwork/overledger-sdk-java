package network.quant.bitcoin.experimental;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.quant.OverledgerContext;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.bitcoin.experimental.Dto.FaucetResponse;
import network.quant.bitcoin.experimental.Dto.FaucetResponseDto;
import network.quant.exception.ClientResponseException;
import network.quant.exception.RedirectException;
import org.bitcoinj.params.RegTestParams;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

/**
 * Experimental class for helping developers use Overledger Bitcoin faucet
 */
public class BitcoinFaucetHelper {

    private static BitcoinFaucetHelper I;
    private static final BigDecimal BTC_IN_SATOSHI = BigDecimal.valueOf(100000000L);
    private static final int ONE_BTC = 1;
    private static final String BEARER = "Bearer";
    private WebClient webClient;
    private String url;

    private BitcoinFaucetHelper() {
        this.webClient = WebClient.builder()
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        String.format("%s %s:%s", BEARER, OverledgerContext.MAPP_ID, OverledgerContext.BPI_KEY)
                )
                .build();
    }

    private void addUtxo(BitcoinAccount bitcoinAccount, String result) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FaucetResponse faucetResponse = objectMapper.readValue(result, FaucetResponse.class);
            FaucetResponseDto faucetResponseDto = objectMapper.readValue(faucetResponse.getMessage(), FaucetResponseDto.class);
            bitcoinAccount.addUtxo(
                    faucetResponseDto.getTxnHash(),
                    faucetResponseDto.getVout(),
                    BTC_IN_SATOSHI.multiply(faucetResponseDto.amount).abs().longValue(),
                    1,
                    faucetResponseDto.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirectPost(BitcoinAccount bitcoinAccount, String uri) {
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
                .doOnSuccess(result -> this.addUtxo(bitcoinAccount, result))
                .doOnError(RuntimeException::new)
                .block();
    }

    /**
     * Fund given BitcoinAccount with 1 BTC
     * @param bitcoinAccount BitcoinAccount containing the bitcoin account
     */
    public void fundAccount(BitcoinAccount bitcoinAccount) {
        if (null != bitcoinAccount && RegTestParams.get().equals(bitcoinAccount.getNetworkParam())) {
            try {
                this.webClient
                        .post()
                        .uri(
                                url,
                                bitcoinAccount.getKey().toAddress(bitcoinAccount.getNetworkParameters()).toBase58(),
                                BTC_IN_SATOSHI.multiply(BigDecimal.ONE)
                        )
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
                        .doOnSuccess(result -> this.addUtxo(bitcoinAccount, result))
                        .block();
            } catch (RedirectException e) {
                this.redirectPost(bitcoinAccount, e.getUrl());
            }
        }
    }

    public static BitcoinFaucetHelper getInstance(String url) {
        if (null == I) {
            I = new BitcoinFaucetHelper();
        }
        I.url = url;
        return I;
    }

}
