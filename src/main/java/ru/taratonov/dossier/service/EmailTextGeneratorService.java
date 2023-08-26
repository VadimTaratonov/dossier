package ru.taratonov.dossier.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.taratonov.dossier.dto.EmailMessageDTO;

@Service
@Slf4j
public class EmailTextGeneratorService {
    @Value("${finish.registration.link}")
    private String FINISH_REGISTRATION_LINK;

    @Value("${create.documents.link}")
    private String CREATE_DOCUMENTS_LINK;

    public String generateFinishRegistrationText(EmailMessageDTO emailMessageDTO) {
        String message = String.join(" ", "Приветствуем Вас в банке ВадимНеофлекс. " +
                "Спасибо за оформление заявки по кредиту. Номер вашей заявки - " +
                emailMessageDTO.getApplicationId() + ". Мы одобрили Ваше предложение, пожалуйста, " +
                "завершите оформление. Для этого перейдите по ссылке:", FINISH_REGISTRATION_LINK);
        log.debug("message for finish registration created");
        return message;
    }

    public String generateCreateDocumentsText(EmailMessageDTO emailMessageDTO) {
        String message = String.join(" ", "Благодарим Вас за предоставленные данные. Кредитное предложение " +
                emailMessageDTO.getApplicationId() +
                " одобрено. Для формирования документов перейдите по ссылке:", CREATE_DOCUMENTS_LINK);
        log.debug("message for create documents created");
        return message;
    }
}
