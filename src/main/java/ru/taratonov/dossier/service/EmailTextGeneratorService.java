package ru.taratonov.dossier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.taratonov.dossier.dto.ApplicationDTO;
import ru.taratonov.dossier.dto.EmailMessageDTO;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailTextGeneratorService {
    @Value("${finish.registration.link}")
    private String FINISH_REGISTRATION_LINK;

    @Value("${link.documents.send}")
    private String SEND_DOCUMENTS_LINK;

    @Value("${link.documents.sign}")
    private String SIGN_DOCUMENTS_LINK;

    @Value("${link.documents.code}")
    private String CODE_DOCUMENTS_LINK;

    @Value("${link.documents.deny}")
    private String DENY_DOCUMENTS_LINK;

    private final RestTemplateRequestsService restTemplateRequestsService;

    public String generateFinishRegistrationText(EmailMessageDTO emailMessageDTO) {
        String message = String.join(" ", "Приветствуем Вас в банке ВадимНеофлекс. " +
                "Спасибо за оформление заявки по кредиту. Номер вашей заявки - " +
                emailMessageDTO.getApplicationId() + ". Мы одобрили Ваше предложение, пожалуйста, " +
                "завершите оформление. Для этого перейдите по ссылке:", FINISH_REGISTRATION_LINK);
        log.debug("message for finish registration created");
        return message;
    }

    public String generateSendDocumentsText(EmailMessageDTO emailMessageDTO) {
        String message = String.join(" ", "Благодарим Вас за предоставленные данные. Кредитное предложение " +
                emailMessageDTO.getApplicationId() +
                " одобрено. Для формирования документов перейдите по ссылке:", SEND_DOCUMENTS_LINK);
        log.debug("message for create documents created");
        return message;
    }

    public String generateSignDocumentsText(EmailMessageDTO emailMessageDTO){
        String message = String.join(" ",
                "Документы для кредитного предложения " +
                        emailMessageDTO.getApplicationId() +
                        " сформированы. Далее необходимо подписать документы. Для этого перейдите по ссылке:",
                SIGN_DOCUMENTS_LINK, "Если хотите отменить заявку, то перейдите по ссылке:", DENY_DOCUMENTS_LINK);
        log.debug("message for sign documents created");
        return message;
    }

    public String generateSesCodeText(EmailMessageDTO emailMessageDTO){
        ApplicationDTO applicationDTO = restTemplateRequestsService.requestToGetApplication(emailMessageDTO.getApplicationId());
        String message = String.join(" ",
                "Код для подписания кредитной заявки " +
                        emailMessageDTO.getApplicationId() +" следующий: " + applicationDTO.getSesCode() +
                        " Далее необходимо выслать данный код по ссылке:", CODE_DOCUMENTS_LINK);
        log.debug("message for ses code created");
        return message;
    }

    public String generateCreditIssuedText(EmailMessageDTO emailMessageDTO){
        String message = "Кредитная заявка  " +
                        emailMessageDTO.getApplicationId() + " успешно создана! " +
                        "Поздравляем с оформлением кредита! Деньги уже в пути!";
        log.debug("message for credit issued created");
        return message;
    }

    public String generateApplicationDeniedText(EmailMessageDTO emailMessageDTO){
        String message = "Кредитная заявка  " +
                emailMessageDTO.getApplicationId() + " отменена. Спасибо за обращение в наш банк.";
        log.debug("message for application denied created");
        return message;
    }
}
