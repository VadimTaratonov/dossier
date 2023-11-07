package ru.taratonov.dossier.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.taratonov.dossier.util.DossierResponseErrorHandler;

@Configuration
public class Config {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder
                .errorHandler(new DossierResponseErrorHandler())
                .build();
    }
}
