package ru.taratonov.dossier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.taratonov.dossier.dto.ApplicationDTO;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
class RestTemplateRequestsServiceTest {

    @Value("${custom.integration.deal.get.credit}")
    private String PATH_TO_DEAL_GET_CREDIT;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplateRequestsService restTemplateRequestsService;
    private MockRestServiceServer mockServer;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Long id = 10L;
    private final ApplicationDTO applicationDto = new ApplicationDTO()
            .setApplicationId(id)
            .setAmount(BigDecimal.ONE)
            .setRate(BigDecimal.valueOf(2))
            .setFirstName("name")
            .setLastName("last name");

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void requestToGetApplication() throws URISyntaxException, JsonProcessingException {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(PATH_TO_DEAL_GET_CREDIT.replace("{id}", id.toString()))))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(applicationDto)));


        ApplicationDTO result = restTemplateRequestsService.requestToGetApplication(id);

        assertEquals(applicationDto.getApplicationId(), result.getApplicationId());
        assertEquals(applicationDto.getAmount(), result.getAmount());
        assertEquals(applicationDto.getRate(), result.getRate());
        assertEquals(applicationDto.getFirstName(), result.getFirstName());
        assertEquals(applicationDto.getLastName(), result.getLastName());
    }

    @Test
    void requestToGetApplicationReturnNullException() throws URISyntaxException {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(PATH_TO_DEAL_GET_CREDIT.replace("{id}", id.toString()))))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(""));

        assertThrows(NullPointerException.class, () -> {
            restTemplateRequestsService.requestToGetApplication(id);
        });
    }
}