package uk.gov.companieshouse.document.generator.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.document.generator.accounts.data.transaction.Resources;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.AbridgedAccountsDataHandler;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.SmallFullAccountsDataHandler;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.exception.DocumentInfoException;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

    @Mock
    private TransactionService transactionService;

    @Mock
    private Transaction transaction;

    private static final String RESOURCE_ID = "/transactions/091174-913515-326060";
    private static final String RESOURCE_URI_ABRIDGED = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";
    private static final String RESOURCE_URI_SMALL_FULL = "/transactions/091174-913515-326060/company-accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";
    private static final String REQUEST_ID = "requestId";

    @Test
    @DisplayName("Test DocumentInfoException thrown when error returned from transaction retrieval")
    void testErrorThrownWhenFailedTransactionRetrieval() throws ServiceException {
        when(transactionService.getTransaction(anyString(), anyString())).thenThrow(new ServiceException("error"));

        assertThrows(DocumentInfoException.class, () ->
                accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_ABRIDGED)));
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of an document data due to an error in transaction retrieval")
    void testUnsuccessfulGetDocumentInfoFailedTransactionRetrieval() throws ServiceException {
        when(transactionService.getTransaction(anyString(), anyString())).thenThrow(new ServiceException("error"));

        assertThrows(ServiceException.class, () -> transactionService.getTransaction("", REQUEST_ID));
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of document data due to no accounts resource in transaction")
    void testUnsuccessfulGetDocumentInfoNoAccountsResourceInTransaction() throws ServiceException, DocumentInfoException {
        Transaction transaction = createTransaction(RESOURCE_URI_ABRIDGED);
        transaction.getResources().remove(RESOURCE_ID);
        transaction.getResources().put("error", createResource(RESOURCE_URI_ABRIDGED));
        when(transactionService.getTransaction(anyString(), anyString())).thenReturn(transaction);

        assertNull(accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_ABRIDGED)));
    }

    @Test
    @DisplayName("Test DocumentInfoException thrown when error returned from  abridged accounts handler")
    void testErrorThrownWhenFailedAbridgedAccountsHandler() throws HandlerException, ServiceException {

        when(transactionService.getTransaction(anyString(), anyString())).thenReturn(createTransaction(RESOURCE_URI_ABRIDGED));
        when(abridgedAccountsDataHandler.getAbridgedAccountsData(any(Transaction.class),  anyString(), anyString())).
                thenThrow(new HandlerException("error"));

        assertThrows(DocumentInfoException.class, () ->
                accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_ABRIDGED)));
    }

    @Test
    @DisplayName("Tests the successful retrieval of document data for abridged")
    void testSuccessfulGetDocumentInfoForAbridged() throws HandlerException, ServiceException, DocumentInfoException {
        when(transactionService.getTransaction(anyString(), anyString())).thenReturn(createTransaction(RESOURCE_URI_ABRIDGED));
        when(abridgedAccountsDataHandler.getAbridgedAccountsData(any(Transaction.class), anyString(), anyString())).thenReturn(new DocumentInfoResponse());

        assertNotNull(accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_ABRIDGED)));
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of document data due to error in abridged accounts handler")
    void testUnsuccessfulGetDocumentInfoExceptionFromAbridgedAccountsHandler()
            throws HandlerException {
        when(abridgedAccountsDataHandler.getAbridgedAccountsData(any(Transaction.class),  anyString(), anyString())).thenThrow(new HandlerException("error"));

        assertThrows(HandlerException.class, () -> abridgedAccountsDataHandler.getAbridgedAccountsData(transaction, "", REQUEST_ID));
    }

    @Test
    @DisplayName("Test DocumentInfoException thrown when error returned from small full accounts handler")
    void testErrorThrownWhenFailedSmallFullAccountsHandler() throws HandlerException, ServiceException {

        when(transactionService.getTransaction(anyString(), anyString())).thenReturn(createTransaction(RESOURCE_URI_SMALL_FULL));
        when(smallFullAccountsDataHandler.getSmallFullAccountsData(any(Transaction.class),  anyString(), anyString())).
                thenThrow(new HandlerException("error"));

        assertThrows(DocumentInfoException.class, () ->
                accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_SMALL_FULL)));
    }

    @Test
    @DisplayName("Tests the successful retrieval of document data for small full")
    void testSuccessfulGetDocumentInfoForSmallFull() throws HandlerException, ServiceException, DocumentInfoException {
        when(transactionService.getTransaction(anyString(), anyString())).thenReturn(createTransaction(RESOURCE_URI_SMALL_FULL));
        when(smallFullAccountsDataHandler.getSmallFullAccountsData(any(Transaction.class), anyString(), anyString())).thenReturn(new DocumentInfoResponse());

        assertNotNull(accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest(RESOURCE_URI_SMALL_FULL)));
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of document data due to error in small full accounts handler")
    void testUnsuccessfulGetDocumentInfoExceptionFromSmallAccountsHandler()
            throws HandlerException {
        when(smallFullAccountsDataHandler.getSmallFullAccountsData(any(Transaction.class),  anyString(), anyString())).thenThrow(new HandlerException("error"));

        assertThrows(HandlerException.class, () -> smallFullAccountsDataHandler.getSmallFullAccountsData(transaction, "", REQUEST_ID));
    }


    private DocumentInfoRequest createDocumentInfoRequest(String resourceUri) {
        DocumentInfoRequest documentInfoRequest = new DocumentInfoRequest();
        documentInfoRequest.setResourceId(RESOURCE_ID);
        documentInfoRequest.setResourceUri(resourceUri);
        documentInfoRequest.setRequestId(REQUEST_ID);
        return documentInfoRequest;
    }

    private Transaction createTransaction(String resourceUri) {
        Map<String, Resources> resources = new HashMap<>();
        resources.put(resourceUri, createResource(resourceUri));

        Transaction transaction = new Transaction();
        transaction.setResources(resources);

        return transaction;
    }

    private Resources createResource(String resourceUri) {
        Resources resource = new Resources();
        resource.setKind("kind");
        Map<String, String> links = new HashMap<>();
        links.put("resource", resourceUri);
        resource.setLinks(links);
        return resource;
    }
}
