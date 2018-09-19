package uk.gov.companieshouse.document.generator.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.api.Exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.service.impl.DocumentTypeServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentTypeServiceTest {

    private DocumentTypeService documentTypeService;

    @BeforeEach
    public void setUp() {
        documentTypeService = new DocumentTypeServiceImpl();
    }

    @Test
    @DisplayName("tests that a string containing 'ACCOUNTS' is returned")
    public void testSuccessfulPatternMatchToAccountsReturned() throws DocumentGeneratorServiceException {

        String testValidAccountUri = "/transactions/111111-222222-333333/accounts/Abc1Defg2hiJkLmNoP34QR5sT6u=";
        String result = documentTypeService.getDocumentType(testValidAccountUri);

        assertEquals("ACCOUNTS", result);
    }

    @Test
    @DisplayName("tests that an error returned when incorrect Uri input")
    public void testErrorReturnedWhenUriDoesNotMatchPattern() throws DocumentGeneratorServiceException {

        String testInvalidAccountUri = "/transactions";

        assertThrows(DocumentGeneratorServiceException.class, () -> documentTypeService.getDocumentType(testInvalidAccountUri));
    }
}
