package ru.taratonov.dossier.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
class EmailTextGeneratorServiceTest {

    @Mock
    private RestTemplateRequestsService restTemplateRequestsService;
    @Autowired
    private EmailTextGeneratorService emailTextGeneratorService;
    private final Long id = 10L;

    @Test
    void generateFinishRegistrationText() {
        String expectedText = FileUtils.getAndReadFromFile("src/test/resources/emailTexts/finishRegistration.txt");
        String resultText = emailTextGeneratorService.generateFinishRegistrationText(id);

        assertEquals(expectedText, resultText);
    }

    @Test
    void generateSendDocumentsText() {
        String expectedText = FileUtils.getAndReadFromFile("src/test/resources/emailTexts/sendDocuments.txt");
        String resultText = emailTextGeneratorService.generateSendDocumentsText(id);

        assertEquals(expectedText, resultText);
    }

    @Test
    void generateSignDocumentsText() {
        String expectedText = FileUtils.getAndReadFromFile("src/test/resources/emailTexts/signDocuments.txt");
        String resultText = emailTextGeneratorService.generateSignDocumentsText(id);

        assertEquals(expectedText, resultText);
    }

    @Test
    void generateCreditIssuedText() {
        String expectedText = FileUtils.getAndReadFromFile("src/test/resources/emailTexts/creditIssued.txt");
        String resultText = emailTextGeneratorService.generateCreditIssuedText(id);

        assertEquals(expectedText, resultText);
    }

    @Test
    void generateApplicationDeniedText() {
        String expectedText = FileUtils.getAndReadFromFile("src/test/resources/emailTexts/applicationDenied.txt");
        String resultText = emailTextGeneratorService.generateApplicationDeniedText(id);

        assertEquals(expectedText, resultText);
    }
}