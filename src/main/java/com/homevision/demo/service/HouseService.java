package com.homevision.demo.service;

import com.homevision.demo.exception.CustomClientException;
import com.homevision.demo.exception.CustomServerException;
import com.homevision.demo.model.House;
import com.homevision.demo.model.HouseApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

@Service
public class HouseService {


    private final WebClient webClient;

    @Value("${api.url}")
    private String apiUrl;

    @Autowired
    public HouseService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<House> fetchHouses(int page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("page", page)
                        .queryParam("per_page", 10)
                        .build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse ->
                        Mono.error(new CustomClientException("Client error fetching houses.")))
                .onStatus(status -> status.is5xxServerError(), clientResponse ->
                        Mono.error(new CustomServerException("Server error fetching houses.")))
                .bodyToMono(HouseApiResponse.class)
                .retryWhen(applyRetryStrategy())
                .flatMapMany(response -> Flux.fromIterable(response.getHouses()))
                .flatMap(house ->
                        fetchImageData(house.getPhotoUrl())
                                .retryWhen(applyRetryStrategy())
                                .flatMap(imageData -> saveImageToFile(buildFilename(house), imageData))
                                .thenReturn(house)
                );
    }

    private String buildFilename(House house) {
        return house.getId() +
                sanitizeAddress(house.getAddress()) + "." + house.getPhotoUrl().substring(house.getPhotoUrl().lastIndexOf(".") + 1);
    }

    private String sanitizeAddress(String address) {
        if (address == null) {
            return "no_address";
        }
        return address.replaceAll("[^a-zA-Z0-9]", "_");
    }

    private Retry applyRetryStrategy() {
        return Retry.backoff(3, Duration.ofSeconds(5))
                .doBeforeRetry(retrySignal ->
                        System.out.println("Retrying... Attempt: " + retrySignal.totalRetriesInARow())
                )
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                        new RuntimeException("Exhausted retries.", retrySignal.failure()));
    }

    public Mono<byte[]> fetchImageData(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response ->
                        Mono.error(new CustomClientException("Error fetching image with URL: " + url)))
                .onStatus(status -> status.is5xxServerError(), response ->
                        Mono.error(new CustomServerException("Server error while fetching image with URL: " + url)))
                .bodyToMono(DataBuffer.class)
                .flatMap(this::convertDataBufferToBytes);
    }

    private Mono<byte[]> convertDataBufferToBytes(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer);
        return Mono.just(bytes);
    }

    public Mono<Path> saveImageToFile(String filename, byte[] bytes) {
        Path path = Paths.get("./downloads", filename);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);
            return Mono.just(path);
        } catch (IOException e) {
            return Mono.error(new RuntimeException("Error writing image to path: " + path, e));
        }
    }
}