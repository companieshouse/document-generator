package uk.gov.companieshouse.document.generator.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.api.Exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.service.impl.DocumentTypeServiceImpl;
import uk.gov.companieshouse.document.generator.api.utility.DocumentType;

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
    @DisplayName("tests that a DocumentType containing 'ACCOUNTS' is returned when uri contains 'accounts'")
    public void testSuccessfulPatternMatchToAccountsReturnedForAccounts() throws DocumentGeneratorServiceException {

        String testValidAccountUri = "/transactions/111111-222222-333333/accounts/Abc1Defg2hiJkLmNoP34QR5sT6u=";
        DocumentType result = documentTypeService.getDocumentType(testValidAccountUri);

        assertEquals(DocumentType.ACCOUNTS, result);
    }

    @Test
    @DisplayName("tests that a DocumentType containing 'ACCOUNTS' is returned when uri contains 'company-accounts'")
    public void testSuccessfulPatternMatchToAccountsReturnedForCompanyAccounts() throws DocumentGeneratorServiceException {

        String testValidAccountUri = "/transactions/111111-222222-333333/company-accounts/Abc1Defg2hiJkLmNoP34QR5sT6u=";
        DocumentType result = documentTypeService.getDocumentType(testValidAccountUri);

        assertEquals(DocumentType.ACCOUNTS, result);
    }

    @Test
    @DisplayName("tests that an error returned when incorrect Uri input")
    public void testErrorReturnedWhenUriDoesNotMatchPattern() {

        String testInvalidAccountUri = "/transactions";

        assertThrows(DocumentGeneratorServiceException.class, () -> documentTypeService.getDocumentType(testInvalidAccountUri));
    }
}
