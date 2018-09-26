package uk.gov.companieshouse.document.generator.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.api.exception.DocumentGeneratorServiceException;
import uk.gov.companieshouse.document.generator.api.service.impl.DocumentTypeServiceImpl;
import uk.gov.companieshouse.document.generator.api.document.DocumentType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentTypeServiceTest {

    private DocumentTypeService documentTypeService;

    private static final String TRANSACTIONS_URI = "/transactions/111111-222222-333333";

    private static final String COMPANY_ACCOUNTS_URI = "/company-accounts/Abc1Defg2hiJkLmNoP34QR5sT6u=";

    private static final String ACCOUNTS_URI = "/accounts/Abc1Defg2hiJkLmNoP34QR5sT6u=";

    @BeforeEach
    public void setUp() {
        documentTypeService = new DocumentTypeServiceImpl();
    }

    @Test
    @DisplayName("tests that a DocumentType containing 'ACCOUNTS' is returned when uri contains 'accounts'")
    public void testSuccessfulPatternMatchToAccountsReturnedForAccounts() throws DocumentGeneratorServiceException {

        String testValidAccountUri = TRANSACTIONS_URI + ACCOUNTS_URI;
        DocumentType result = documentTypeService.getDocumentType(testValidAccountUri);

        assertEquals(DocumentType.ACCOUNTS, result);
    }

    @Test
    @DisplayName("tests that a DocumentType containing 'ACCOUNTS' is returned when uri contains 'company-accounts'")
    public void testSuccessfulPatternMatchToAccountsReturnedForCompanyAccounts() throws DocumentGeneratorServiceException {

        String testValidAccountUri = TRANSACTIONS_URI + COMPANY_ACCOUNTS_URI;
        DocumentType result = documentTypeService.getDocumentType(testValidAccountUri);

        assertEquals(DocumentType.ACCOUNTS, result);
    }

    @Test
    @DisplayName("tests that an error returned when incorrect Uri input")
    public void testErrorReturnedWhenUriDoesNotMatchPattern() {

        String testInvalidAccountUri = TRANSACTIONS_URI;

        assertThrows(DocumentGeneratorServiceException.class, () -> documentTypeService.getDocumentType(testInvalidAccountUri));
    }
}
