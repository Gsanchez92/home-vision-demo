package controller;

import com.homevision.demo.controller.HouseController;
import com.homevision.demo.model.House;
import com.homevision.demo.model.HouseApiResponse;
import com.homevision.demo.service.HouseService;
import config.WebClientMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HouseControllerTest {

    @InjectMocks
    private HouseController houseController;

    @Mock
    private HouseService houseService;

    @BeforeEach
    public void setUp() {
        HouseApiResponse mockResponse = createMockHouseApiResponse();

        // Mock the WebClient in the HouseService to use my WebClientMock
        WebClient mockWebClient = WebClientMock.getWebClientMock(mockResponse);
        when(houseService.fetchHouses(anyInt())).thenAnswer(invocation -> {
            int page = invocation.getArgument(0);
            return mockWebClient.get()
                    .uri("/houses?page=" + page)
                    .retrieve()
                    .bodyToFlux(House.class);
        });
    }

    @Test
    public void testFetchAndSaveHouses() {
        Flux<String> responseFlux = houseController.fetchAndSaveHouses();

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    private HouseApiResponse createMockHouseApiResponse() {
        House house1 = new House();
        house1.setId(1);
        house1.setAddress("123 Street");
        house1.setPhotoURL("http://image1.jpg");

        House house2 = new House();
        house2.setId(2);
        house2.setAddress("456 Avenue");
        house2.setPhotoURL("http://image2.jpg");

        return new HouseApiResponse(Arrays.asList(house1, house2));
    }
}