package ru.taratonov.dossier.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.taratonov.dossier.dto.EmailMessageDTO;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class DossierService {

    private final ObjectMapper objectMapper;
    private final EmailSenderService emailSenderService;
    private final String TOPIC_FINISH_REGISTRATION = "finish-registration";
    private final String TOPIC_CREATE_DOCUMENTS = "create-documents";
    private final String TOPIC_SEND_DOCUMENTS = "send-documents";
    private final String TOPIC_SEND_SES = "send-ses";
    private final String TOPIC_CREDIT_ISSUED = "credit-issued";
    private final String TOPIC_APPLICATION_DENIED = "application-denied";

    @KafkaListener(topics = {TOPIC_FINISH_REGISTRATION, TOPIC_CREATE_DOCUMENTS, TOPIC_SEND_SES,
            TOPIC_CREDIT_ISSUED, TOPIC_APPLICATION_DENIED}, groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) throws JsonProcessingException {
        EmailMessageDTO emailMessageDTO = objectMapper.readValue(message, EmailMessageDTO.class);
        log.info("get message {}", emailMessageDTO);
        emailSenderService.sendSimpleEmail(emailMessageDTO);
    }

    @KafkaListener(topics = {TOPIC_SEND_DOCUMENTS}, groupId = "${spring.kafka.consumer.group-id}")
    public void get(String message) throws IOException, MessagingException {
        EmailMessageDTO emailMessageDTO = objectMapper.readValue(message, EmailMessageDTO.class);
        log.info("get message {}", emailMessageDTO);
        emailSenderService.sendEmailWithAttachment(emailMessageDTO);
    }


}
