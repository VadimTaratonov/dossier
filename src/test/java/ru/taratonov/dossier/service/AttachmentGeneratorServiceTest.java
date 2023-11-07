package ru.taratonov.dossier.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import ru.taratonov.dossier.dto.ApplicationDTO;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttachmentGeneratorServiceTest {

    @Mock
    private RestTemplateRequestsService restTemplateRequestsService;
    @InjectMocks
    private AttachmentGeneratorService attachmentGeneratorService;
    private final Long id = 10L;

    private final ApplicationDTO application = new ApplicationDTO()
            .setLastName("Taratonov")
            .setFirstName("Vadim")
            .setMiddleName("Nikolaevich")
            .setAmount(BigDecimal.valueOf(11000).setScale(2, RoundingMode.CEILING))
            .setTerm(9)
            .setMonthlyPayment(BigDecimal.valueOf(1221.54).setScale(2, RoundingMode.CEILING))
            .setRate(BigDecimal.valueOf(0.1).setScale(1, RoundingMode.CEILING))
            .setPsk(BigDecimal.valueOf(0.061).setScale(3, RoundingMode.CEILING))
            .setInsuranceEnable(true)
            .setSalaryClient(true);

    @Disabled
    @Test
    void generateCreditInformation() {
        when(restTemplateRequestsService.requestToGetApplication(id)).thenReturn(application);

        FileSystemResource expectedFileSystemResource = new FileSystemResource(new File("src/test/resources/information.txt"));

        FileSystemResource resultFileSystemResource = attachmentGeneratorService.generateCreditInformation(id);

        String expectedText = FileUtils.getAndReadFromFile(expectedFileSystemResource.getFile().getAbsolutePath());
        String resultText = FileUtils.getAndReadFromFile(resultFileSystemResource.getFile().getAbsolutePath());

        Assertions.assertEquals(expectedText, resultText);

    }

    @Test
    void generatePaymentScheduleInformation() {
    }
}