package ru.taratonov.dossier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.taratonov.dossier.dto.ApplicationDTO;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource(value = "classpath:application.properties", encoding = "UTF8")
public class EmailTextGeneratorService {
    @Value("${link.finish.registration}")
    private String FINISH_REGISTRATION_LINK;
    @Value("${link.documents.send}")
    private String SEND_DOCUMENTS_LINK;
    @Value("${link.documents.sign}")
    private String SIGN_DOCUMENTS_LINK;
    @Value("${link.documents.code}")
    private String CODE_DOCUMENTS_LINK;
    @Value("${link.documents.deny}")
    private String DENY_DOCUMENTS_LINK;
    @Value("${text.documents.deny}")
    private String DENY_DOCUMENTS_TEXT;
    @Value("${text.documents.issue}")
    private String ISSUE_DOCUMENTS_TEXT;
    @Value("${text.documents.code}")
    private String CODE_DOCUMENTS_TEXT;
    @Value("${text.documents.sign}")
    private String SIGN_DOCUMENTS_TEXT;
    @Value("${text.documents.send}")
    private String SEND_DOCUMENTS_TEXT;
    @Value("${text.finish.registration}")
    private String FINISH_REGISTRATION_TEXT;

    private final RestTemplateRequestsService restTemplateRequestsService;

    public String generateFinishRegistrationText(Long id) {
        String message = FINISH_REGISTRATION_TEXT
                .replace("{id}", id.toString())
                .replace("{link}", FINISH_REGISTRATION_LINK);
        log.debug("message for finish registration created");
        return message;
    }

    public String generateSendDocumentsText(Long id) {
        String message = SEND_DOCUMENTS_TEXT
                .replace("{id}", id.toString())
                .replace("{link}", SEND_DOCUMENTS_LINK);
        log.debug("message for create documents created");
        return message;
    }

    public String generateSignDocumentsText(Long id) {
        String message = SIGN_DOCUMENTS_TEXT
                .replace("{id}", id.toString())
                .replace("{link1}", SIGN_DOCUMENTS_LINK)
                .replace("{link2}", DENY_DOCUMENTS_LINK);
        log.debug("message for sign documents created");
        return message;
    }

    public String generateSesCodeText(Long id) {
        ApplicationDTO applicationDto = restTemplateRequestsService.requestToGetApplication(id);
        String message = CODE_DOCUMENTS_TEXT
                .replace("{id}", id.toString())
                .replace("{ses}", applicationDto.getSesCode().toString())
                .replace("{link}", CODE_DOCUMENTS_LINK);
        log.debug("message for ses code created");
        return message;
    }

    public String generateCreditIssuedText(Long id) {
        String message = ISSUE_DOCUMENTS_TEXT
                .replace("{id}", id.toString());
        log.debug("message for credit issued created");
        return message;
    }

    public String generateApplicationDeniedText(Long id) {
        String message = DENY_DOCUMENTS_TEXT
                .replace("{id}", id.toString());
        log.debug("message for application denied created");
        return message;
    }
}
