package ru.taratonov.dossier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import ru.taratonov.dossier.dto.ApplicationDTO;
import ru.taratonov.dossier.dto.PaymentScheduleElement;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentGeneratorService {

    private final RestTemplateRequestsService restTemplateRequestsService;

    public FileSystemResource generateCreditInformation(Long id) {
        ApplicationDTO applicationDTO = getCreditFromDeal(id);
        Path information;
        try {
            information = Files.createTempFile("information", ".txt");
        } catch (IOException e) {
            log.error("error of creating temp file");
            throw new RuntimeException(e);
        }
        try (FileWriter fileWriter = new FileWriter(information.toFile())) {
            fileWriter.write(String.join(" ",
                    "Credit information for",
                    applicationDTO.getLastName(),
                    applicationDTO.getFirstName(),
                    applicationDTO.getMiddleName()));
            fileWriter.write("\nLoan mount: " + applicationDTO.getAmount());
            fileWriter.write("\nTerm: " + applicationDTO.getTerm());
            fileWriter.write("\nMonthly payment: " + applicationDTO.getMonthlyPayment());
            fileWriter.write("\nRate: " + applicationDTO.getRate());
            fileWriter.write("\nPsk: " + applicationDTO.getPsk());
            fileWriter.write("\nInsurance enable: " + applicationDTO.getInsuranceEnable());
            fileWriter.write("\nSalary client: " + applicationDTO.getSalaryClient());
        } catch (IOException e) {
            log.error("error of writing data to file");
            throw new RuntimeException(e);
        }
        log.debug("file with credit information created {}", information);
        FileSystemResource fileSystemResource =
                new FileSystemResource(information);

        log.info("system file with credit data is ready for sending on client mail");
        return fileSystemResource;
    }

    public FileSystemResource generatePaymentScheduleInformation(Long id) {
        ApplicationDTO applicationDTO = getCreditFromDeal(id);
        Path information;
        try {
            information = Files.createTempFile("Payment schedule", ".txt");
        } catch (IOException e) {
            log.error("error of creating temp file");
            throw new RuntimeException(e);
        }
        try (FileWriter fileWriter = new FileWriter(information.toFile())) {
            List<PaymentScheduleElement> paymentSchedule = applicationDTO.getPaymentSchedule();
            fileWriter.write(String.join(" ",
                    "Payment schedule for",
                    applicationDTO.getLastName(),
                    applicationDTO.getFirstName(),
                    applicationDTO.getMiddleName()));
            for (PaymentScheduleElement paymentScheduleElement : paymentSchedule) {
                fileWriter.write("\nPayment number: " + paymentScheduleElement.getNumber());
                fileWriter.write("\nPayment date: " + paymentScheduleElement.getDate());
                fileWriter.write("\nTotal payment: " + paymentScheduleElement.getTotalPayment());
                fileWriter.write("\nInterest payment: " + paymentScheduleElement.getInterestPayment());
                fileWriter.write("\nDebt payment: " + paymentScheduleElement.getDebtPayment());
                fileWriter.write("\nRemaining debt: " + paymentScheduleElement.getRemainingDebt());
                fileWriter.write("\n\n");
            }
        } catch (IOException e) {
            log.error("error of writing data to file");
            throw new RuntimeException(e);
        }
        log.debug("file with credit payment schedule created {}", information);
        FileSystemResource fileSystemResource =
                new FileSystemResource(information);
        log.info("system file with payment schedule is ready for sending on client mail");
        return fileSystemResource;
    }

    private ApplicationDTO getCreditFromDeal(Long id) {
        ApplicationDTO applicationDTO = restTemplateRequestsService.requestToGetApplication(id);
        log.info("request to deal successfully completed");
        return applicationDTO;
    }
}
