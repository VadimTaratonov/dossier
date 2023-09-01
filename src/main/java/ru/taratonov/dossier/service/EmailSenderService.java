package ru.taratonov.dossier.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.taratonov.dossier.dto.EmailMessageDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    private final JavaMailSender emailSender;
    private final EmailTextGeneratorService emailTextGeneratorService;
    private final AttachmentGeneratorService attachmentGeneratorService;

    public void sendSimpleEmail(EmailMessageDTO emailMessageDTO) {
        SimpleMailMessage simpleMailMessage = initializeSimpleMessage(emailMessageDTO);
        emailSender.send(simpleMailMessage);
        log.info("message was sent to {} ", emailMessageDTO.getEmailAddress());
    }

    public void sendEmailWithAttachment(EmailMessageDTO emailMessageDTO) throws MessagingException {
        MimeMessage mimeMessage = initializeMessageWithAttachment(emailMessageDTO);
        emailSender.send(mimeMessage);
        log.info("message with attachment was sent to {}", emailMessageDTO.getEmailAddress());
    }

    private SimpleMailMessage initializeSimpleMessage(EmailMessageDTO emailMessageDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("credit.conveyor@mail.ru");
        simpleMailMessage.setTo(emailMessageDTO.getEmailAddress());
        simpleMailMessage.setSubject(emailMessageDTO.getTheme().getTitle());
        simpleMailMessage.setText(generateEmailText(emailMessageDTO));
        log.debug("simple message was initialized");
        return simpleMailMessage;
    }

    private MimeMessage initializeMessageWithAttachment(EmailMessageDTO emailMessageDTO) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("credit.conveyor@mail.ru");
        mimeMessageHelper.setTo(emailMessageDTO.getEmailAddress());
        mimeMessageHelper.setSubject(emailMessageDTO.getTheme().getTitle());
        mimeMessageHelper.setText(generateEmailText(emailMessageDTO));
        mimeMessageHelper.addAttachment("Credit information.txt",
                attachmentGeneratorService.generateCreditInformation(emailMessageDTO.getApplicationId()));
        mimeMessageHelper.addAttachment("Payment schedule.txt",
                attachmentGeneratorService.generatePaymentScheduleInformation(emailMessageDTO.getApplicationId()));
        log.debug("message with attachment was initialized");
        return mimeMessage;
    }

    private String generateEmailText(EmailMessageDTO emailMessageDTO) {
        String message;
        switch (emailMessageDTO.getTheme()) {
            case FINISH_REGISTRATION ->
                    message = emailTextGeneratorService.generateFinishRegistrationText(emailMessageDTO);
            case CREATE_DOCUMENTS -> message = emailTextGeneratorService.generateSendDocumentsText(emailMessageDTO);
            case SEND_DOCUMENTS -> message = emailTextGeneratorService.generateSignDocumentsText(emailMessageDTO);
            case SEND_SES -> message = emailTextGeneratorService.generateSesCodeText(emailMessageDTO);
            case CREDIT_ISSUED -> message = emailTextGeneratorService.generateCreditIssuedText(emailMessageDTO);
            case APPLICATION_DENIED -> message = emailTextGeneratorService.generateApplicationDeniedText(emailMessageDTO);
            default -> message = "Error in bank. Sorry";
        }
        log.debug("message create - {}", message);
        return message;
    }
}
