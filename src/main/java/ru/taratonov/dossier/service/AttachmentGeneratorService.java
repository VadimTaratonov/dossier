package ru.taratonov.dossier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import ru.taratonov.dossier.dto.Credit;
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
        Credit credit = getCreditFromDeal(id);
        Path information;
        try {
            information = Files.createTempFile("information", ".txt");
        } catch (IOException e) {
            log.error("error of creating temp file");
            throw new RuntimeException(e);
        }
        try (FileWriter fileWriter = new FileWriter(information.toFile())) {
            fileWriter.write("Loan mount: " + credit.getAmount());
            fileWriter.write("\nTerm: " + credit.getTerm());
            fileWriter.write("\nMonthly payment: " + credit.getMonthlyPayment());
            fileWriter.write("\nRate: " + credit.getRate());
            fileWriter.write("\nPsk: " + credit.getPsk());
            fileWriter.write("\nInsurance enable: " + credit.getInsuranceEnable());
            fileWriter.write("\nSalary client: " + credit.getSalaryClient());
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

    public FileSystemResource generatePaymentScheduleInformation(Long id){
        Credit credit = getCreditFromDeal(id);
        Path information;
        try {
            information = Files.createTempFile("Payment schedule", ".txt");
        } catch (IOException e) {
            log.error("error of creating temp file");
            throw new RuntimeException(e);
        }
        try(FileWriter fileWriter = new FileWriter(information.toFile())) {
            List<PaymentScheduleElement> paymentSchedule = credit.getPaymentSchedule();
            for (PaymentScheduleElement paymentScheduleElement: paymentSchedule) {
                fileWriter.write( "Payment number: " + paymentScheduleElement.getNumber());
                fileWriter.write( "\nPayment date: " + paymentScheduleElement.getDate());
                fileWriter.write( "\nTotal payment: " + paymentScheduleElement.getTotalPayment());
                fileWriter.write( "\nInterest payment: " + paymentScheduleElement.getInterestPayment());
                fileWriter.write( "\nDebt payment: " + paymentScheduleElement.getDebtPayment());
                fileWriter.write( "\nRemaining debt: " + paymentScheduleElement.getRemainingDebt());
                fileWriter.write( "\n\n");
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

    private Credit getCreditFromDeal(Long id){
        Credit credit = restTemplateRequestsService.requestToGetApplication(id);
        log.debug("request to deal successfully completed");
        return credit;
    }
}
