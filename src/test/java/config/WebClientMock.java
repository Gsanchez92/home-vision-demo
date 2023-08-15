package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientMock {

    public static WebClient getWebClientMock(Object obj) {
        try {
            String bodyContent = new ObjectMapper().writeValueAsString(obj);

            return WebClient.builder()
                    .exchangeFunction(clientRequest -> Mono.just(
                            ClientResponse.create(HttpStatus.OK)
                                    .header("content-type", "application/json")
                                    .body(bodyContent)
                                    .build()
                    ))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error while serializing object to JSON", e);
        }
    }
}