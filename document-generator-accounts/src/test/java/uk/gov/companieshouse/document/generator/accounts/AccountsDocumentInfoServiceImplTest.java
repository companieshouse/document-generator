package uk.gov.companieshouse.document.generator.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.AbridgedAccountsDataHandler;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.SmallFullAccountsDataHandler;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AccountsDocumentInfoServiceImplTest {

    @InjectMocks
    private AccountsDocumentInfoServiceImpl accountsDocumentInfoService;

    @Mock
    private AbridgedAccountsDataHandler abridgedAccountsDataHandler;

    @Mock
    private SmallFullAccountsDataHandler smallFullAccountsDataHandler;

    private static final String RESOURCE_URI_ABRIDGED = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";
    private static final String RESOURCE_URI_SMALL_FULL = "/transactions/091174-913515-326060/company-accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";
    private static final String INCORRECT_URI_NO_ACCOUNT = "/incorrect/1234-212/incorrect/1231-1231";
    private static final String REQUEST_ID = "requestId";

    @Test
    @DisplayName("Test DocumentInfoException thrown when no matching uri is located")
    void testErrorThrownWhenWhenNoMatchingUriFound() {
        assertThrows(DocumentInfoException.class, () ->
                accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(INCORRECT_URI_NO_ACCOUNT)));
    }


    @Test
    @DisplayName("Test DocumentInfoException thrown when error returned from  abridged accounts handler")
    void testErrorThrownWhenFailedAbridgedAccountsHandler() throws HandlerException {
        when(abridgedAccountsDataHandler.getAbridgedAccountsData(anyString(), anyString())).
                thenThrow(new HandlerException("error"));

        assertThrows(DocumentInfoException.class, () ->
                accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_ABRIDGED)));
    }

    @Test
    @DisplayName("Tests the successful retrieval of document data for abridged")
    void testSuccessfulGetDocumentInfoForAbridged() throws HandlerException, DocumentInfoException {
        when(abridgedAccountsDataHandler.getAbridgedAccountsData(anyString(), anyString())).thenReturn(new DocumentInfoResponse());

        assertNotNull(accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_ABRIDGED)));
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of document data due to error in abridged accounts handler")
    void testUnsuccessfulGetDocumentInfoExceptionFromAbridgedAccountsHandler()
            throws HandlerException {
        when(abridgedAccountsDataHandler.getAbridgedAccountsData(anyString(), anyString())).thenThrow(new HandlerException("error"));

        assertThrows(HandlerException.class, () -> abridgedAccountsDataHandler.getAbridgedAccountsData("", REQUEST_ID));
    }

    @Test
    @DisplayName("Test DocumentInfoException thrown when error returned from small full accounts handler")
    void testErrorThrownWhenFailedSmallFullAccountsHandler() throws HandlerException {
        when(smallFullAccountsDataHandler.getSmallFullAccountsData(anyString(), anyString())).
                thenThrow(new HandlerException("error"));

        assertThrows(DocumentInfoException.class, () ->
                accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_SMALL_FULL)));
    }

    @Test
    @DisplayName("Tests the successful retrieval of document data for small full")
    void testSuccessfulGetDocumentInfoForSmallFull() throws HandlerException, DocumentInfoException {
        when(smallFullAccountsDataHandler.getSmallFullAccountsData(anyString(), anyString())).thenReturn(new DocumentInfoResponse());

        assertNotNull(accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_SMALL_FULL)));
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of document data due to error in small full accounts handler")
    void testUnsuccessfulGetDocumentInfoExceptionFromSmallAccountsHandler()
            throws HandlerException {
        when(smallFullAccountsDataHandler.getSmallFullAccountsData(anyString(), anyString())).thenThrow(new HandlerException("error"));

        assertThrows(HandlerException.class, () -> smallFullAccountsDataHandler.getSmallFullAccountsData("", REQUEST_ID));
    }


    private DocumentInfoRequest createDocumentInfoRequest(String resourceUri) {
        DocumentInfoRequest documentInfoRequest = new DocumentInfoRequest();
        documentInfoRequest.setResourceUri(resourceUri);
        documentInfoRequest.setRequestId(REQUEST_ID);
        return documentInfoRequest;
    }
}
