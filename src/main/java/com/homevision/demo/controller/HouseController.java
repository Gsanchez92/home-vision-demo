package com.homevision.demo.controller;

import com.homevision.demo.service.HouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;



@RestController
@RequestMapping("/houses")
public class HouseController {

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);

    @Autowired
    private HouseService houseService;

    @GetMapping("/fetchAndSave")
    public Flux<String> fetchAndSaveHouses() {
        return Flux.range(1, 10)
                .flatMap(this::fetchAndLogHousesForPage)
                .onErrorReturn("An error occurred while processing houses.");
    }

    private Flux<String> fetchAndLogHousesForPage(int page) {
        return houseService.fetchHouses(page)
                .doOnNext(house -> logger.info("Successfully fetched and saved image for house: {}", house.getId()))
                .doOnError(this::logError)
                .map(house -> String.format("Processed house with ID: %d\n", house.getId()));
    }

    private void logError(Throwable throwable) {
        if (throwable != null) {
            logger.error("Client error: {}", throwable.getMessage());
        } else {
            logger.error("Unexpected error: {}", throwable.getMessage());
        }
    }
}