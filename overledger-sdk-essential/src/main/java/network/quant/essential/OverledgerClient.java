package network.quant.essential;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import network.quant.OverledgerContext;
import network.quant.api.Client;
import network.quant.api.DltTransaction;
import network.quant.api.OverledgerTransactions;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.exception.ClientResponseException;
import network.quant.exception.RedirectException;
import network.quant.util.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Basic implementation of client
 */
@Slf4j
public final class OverledgerClient<T extends OverledgerTransactionRequest, S extends OverledgerTransactionResponse> implements Client<T, S> {

    public static final String REFRESH_TOKEN_STRING = "refresh_token";
    public static final String GRANT_TYPE_STRING = "grant_type";
    public static final String CLIENT_ID_STRING = "client_id";
    private static Client client;
    private static final String BEARER = "Bearer";
    private static final String HEADER_LOCATION = "Location";
    private WebClient webClient;

    private OverledgerClient() {
        System.out.println("====> Overledger.BASE_URL: " + OverledgerContext.BASE_URL);

        if(OverledgerContext.BASE_URL.contains("/v1")) {
            System.out.println("====> V1");

            this.webClient = WebClient.builder()
                    .defaultHeader(
                            HttpHeaders.AUTHORIZATION,
                            String.format("%s %s:%s", BEARER, OverledgerContext.MAPP_ID, OverledgerContext.BPI_KEY)
                    )
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
        } else {
            System.out.println("====> V2");
            this.webClient = WebClient.builder()
                    .defaultHeader(
                            HttpHeaders.AUTHORIZATION,
                            String.format("%s %s", BEARER, OverledgerContext.OAUTH2_ACCESS_TOKEN)
                    )
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }
    }

    private Mono<ClientResponseException> getClientResponse(ClientResponse clientResponse) {
        return clientResponse
                .bodyToMono(ByteArrayResource.class)
                .map(ByteArrayResource::getByteArray)
                .map(String::new)
                .map(ClientResponseException::new);
    }

    public StatusResponse postSubStatusUpdate(StatusRequest statusRequest) {
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.SUBSCRIBE_TRANSACTION_UPDATES)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(statusRequest))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(StatusResponse.class)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(statusRequest))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(StatusResponse.class)
                    .block();
        }
    }

    public StatusResponse postUnsubStatusUpdate(StatusRequest statusRequest){
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.UNSUBSCRIBE_TRANSACTION_UPDATES)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(statusRequest))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(StatusResponse.class)
                    .block();
        }catch (RedirectException e){
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(statusRequest))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(StatusResponse.class)
                    .block();
        }
    }

    @Override
    public ContractQueryResponseDto smartContractQuery(DltTransaction dltTransaction, String dlt){
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.SMART_CONTRACT_QUERY, dlt)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(dltTransaction))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(ContractQueryResponseDto.class)
                    .block();
        }catch (RedirectException e){
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(dltTransaction))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(ContractQueryResponseDto.class)
                    .block();
        }
    }

    public List<EventSubscribeResponse> postSubscribeEvent(DltTransaction dltTransaction) {
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.SUBSCRIBE_EVENT_UPDATES)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(dltTransaction))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    //.onStatus(HttpStatus::is2xxSuccessful, this::successMSG)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToFlux(EventSubscribeResponse.class)
                    .timeout(Duration.ofMillis(120000))
                    .collectList()
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(dltTransaction))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToFlux(EventSubscribeResponse.class)
                    .timeout(Duration.ofMillis(120000))
                    .collect(Collectors.toList())
                    .block();
        }
    }

    public List<EventSubscribeResponse> postUnsubscribeEvent(DltTransaction dltTransaction) {
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.UNSUBSCRIBE_EVENT_UPDATES)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(dltTransaction))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToFlux(EventSubscribeResponse.class)
                    .collect(Collectors.toList())
                    .timeout(Duration.ofMillis(120000))
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(dltTransaction))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToFlux(EventSubscribeResponse.class)
                    .collect(Collectors.toList())
                    .timeout(Duration.ofMillis(120000))
                    .block();
        }
    }

    @Override
    public S postTransaction(T ovlTransaction, Class<T> requestClass, Class<S> responseClass) {
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.WRITE_TRANSACTIONS)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(Mono.just(ovlTransaction), requestClass)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(Mono.just(ovlTransaction), requestClass)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public S getTransaction(UUID overledgerTransactionID, Class<S> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.READ_TRANSACTIONS_BY_TRANSACTION_ID, overledgerTransactionID)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public <S extends OverledgerTransactions> S getTransactions(String mappId, Class<S> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID, mappId)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public <S extends OverledgerTransactions> S getTransactions(String mappId, PageParams page, Class<S> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID_BY_PAGE, mappId, page.getOffset(), page.getLength())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
//                    .map(s -> {
//                        List<OverledgerTransactionResponse> responses = null;
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        try {
//                            responses = objectMapper.readValue(s, new TypeReference<List<OverledgerTransactionResponse>>() {});
//                        } catch (IOException ioe) {
//                            ioe.printStackTrace();
//                        }
//                        return (S)OverledgerTransactionsResponse.builder()
//                                .transactions(responses)
//                                .totalTransactions(null == responses?0:responses.size())
//                                .build();
//                    })
                    .block();
        }
    }

    @Override
    @Deprecated
    public S getTransaction(String dlt, String transactionHash, Class<S> responseClass) {
        return null;
    }

    @Override
    public List<BalanceResponse> postBalances(List<BalanceRequest> balanceRequests) {
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.BALANCES_CHECK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(balanceRequests))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(String.class)
                    .map(s -> {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            return (List<BalanceResponse>) objectMapper.readValue(s, new TypeReference<List<BalanceResponse>>() {
                            });
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        return null;
                    })
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(balanceRequests))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(String.class)
                    .map(s -> {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            return (List<BalanceResponse>) objectMapper.readValue(s, new TypeReference<List<BalanceResponse>>() {
                            });
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        return null;
                    })
                    .block();
        }
    }

    @Override
    public SequenceResponse postSequence(SequenceRequest sequenceRequest) {
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.SEQUENCE_CHECK)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(sequenceRequest))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(SequenceResponse.class)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(sequenceRequest))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(SequenceResponse.class)
                    .block();
        }
    }

    @Override
    public FeeEstimationResponse getFeeEstimation(String dltName, String blockNumber) {

        System.out.println("getFeeEstimation ---> " + dltName + ", blockNumber = " + blockNumber);
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.FEE_ESTIMATION, dltName, blockNumber)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(FeeEstimationResponse.class)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(FeeEstimationResponse.class)
                    .block();
        }
    }

    @Override
    public Oauth2RefreshResponse refreshAccessToken() {

        System.out.println("refresh Access Token, REFRESH URL = " + OverledgerContext.OAUTH2_REFRESH_TOKEN_URL);

        WebClient webClient1 = WebClient.builder().build();

        Oauth2RefreshResponse result = webClient1
                .post()
                .uri(OverledgerContext.OAUTH2_REFRESH_TOKEN_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromFormData(GRANT_TYPE_STRING, REFRESH_TOKEN_STRING)
                        .with(CLIENT_ID_STRING, OverledgerContext.OAUTH2_CLIENT_ID)
                        .with(REFRESH_TOKEN_STRING, OverledgerContext.OAUTH2_REFRESH_TOKEN))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                .bodyToMono(Oauth2RefreshResponse.class)
                .block();

        return result;

    }

    @Override
    public Transaction searchTransaction(String transactionHash, Class<Transaction> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.SEARCH_TRANSACTIONS, transactionHash)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public Address searchAddress(String address, Class<Address> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.SEARCH_ADDRESSES, address)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public Block searchBlock(String dlt, String blockhash, Class<Block> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.SEARCH_CHAIN_BLOCKS, dlt, blockhash)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    static Client getInstance() {
        if (null == client) {
            client = new OverledgerClient();
        }
        return client;
    }

}