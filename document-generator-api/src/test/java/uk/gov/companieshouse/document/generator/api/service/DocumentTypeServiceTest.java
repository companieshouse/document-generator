package uk.gov.companieshouse.document.generator.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.api.exception.ServiceException;
import uk.gov.companieshouse.document.generator.api.service.impl.DocumentTypeServiceImpl;
import uk.gov.companieshouse.document.generator.api.document.DocumentType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentTypeServiceTest {

    private DocumentTypeService documentTypeService;

    private static final String ACCOUNTS_RESOURCE_URI = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";

    private static final String COMPANY_ACCOUNTS_RESOURCE_URI = "/transactions/091174-913515-326060/company-accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";

    private static final String REQUEST_ID = "requestId";

    private static final String RESOURCE_URI = "resource_uri";

    Map<String, String> requestParameters;

    @BeforeEach
    public void setUp() {
        documentTypeService = new DocumentTypeServiceImpl();

        requestParameters = new HashMap<>();
        requestParameters.put("request_id", REQUEST_ID);
        requestParameters.put("resource_uri", RESOURCE_URI);
    }

    @Test
    @DisplayName("tests that a DocumentType containing 'ACCOUNTS' is returned when uri contains 'accounts'")
    public void testSuccessfulPatternMatchToAccountsReturnedForAccounts() throws ServiceException {

        requestParameters.put(RESOURCE_URI, ACCOUNTS_RESOURCE_URI);
        DocumentType result = documentTypeService.getDocumentType(requestParameters);

        assertEquals(DocumentType.ACCOUNTS, result);
    }

    @Test
    @DisplayName("tests that a DocumentType containing 'ACCOUNTS' is returned when uri contains 'company-accounts'")
    public void testSuccessfulPatternMatchToAccountsReturnedForCompanyAccounts() throws ServiceException {

        requestParameters.put(RESOURCE_URI, COMPANY_ACCOUNTS_RESOURCE_URI);
        DocumentType result = documentTypeService.getDocumentType(requestParameters);

        assertEquals(DocumentType.ACCOUNTS, result);
    }

    @Test
    @DisplayName("tests that an error returned when incorrect Uri input")
    public void testErrorReturnedWhenUriDoesNotMatchPattern() {

        requestParameters.put(RESOURCE_URI, "wrong data");
        assertThrows(ServiceException.class, () -> documentTypeService.getDocumentType(requestParameters));
    }
}
