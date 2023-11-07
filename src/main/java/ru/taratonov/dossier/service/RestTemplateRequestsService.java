package ru.taratonov.dossier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.taratonov.dossier.dto.ApplicationDTO;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestTemplateRequestsService {

    private final RestTemplate restTemplate;

    @Value("${custom.integration.deal.get.credit}")
    private String PATH_TO_DEAL_GET_CREDIT;

    public ApplicationDTO requestToGetApplication(Long id) {
        log.info("request to get application to deal with id {}", id);
        ResponseEntity<ApplicationDTO> responseEntity =
                restTemplate.getForEntity(PATH_TO_DEAL_GET_CREDIT.replace("{id}", id.toString()), ApplicationDTO.class);
        return Objects.requireNonNull(responseEntity.getBody());
    }
}



