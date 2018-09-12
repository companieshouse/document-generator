package uk.gov.companieshouse.document.generator.accounts;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.transaction.Resource;
import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.handler.accounts.AccountsHandler;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoRequest;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfoResponse;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AccountsDocumentInfoServiceImplTest {

    @InjectMocks
    private AccountsDocumentInfoServiceImpl accountsDocumentInfoService;

    @Mock
    private AccountsHandler accountsHandlerMock;

    @Mock
    private TransactionService transactionService;

    private static final String RESOURCE_URI = "/transactions/091174-913515-326060";
    private static final String RESOURCE_ID = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";

    @Test
    @DisplayName("Tests the unsuccessful retrieval of an document data due to an error in transaction retrieval")
    void testUnsuccessfulGetDocumentInfoFailedTransactionRetrieval() {
        when(transactionService.getTransaction(anyString())).thenReturn(null);

        assertNull(accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest()));
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of an accounts document data due to no accounts resource in transaction")
    void testUnsuccessfulGetDocumentInfoNoAccountsResourceInTransaction() {
        Transaction transaction = createTransaction();
        transaction.getResources().remove(RESOURCE_ID);
        transaction.getResources().put("error", createResource());
        when(transactionService.getTransaction(anyString())).thenReturn(transaction);

        assertNull(accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest()));
    }

    @Test
    @DisplayName("Tests the successful retrieval of document data")
    void testSuccessfulGetDocumentInfo() {
        when(transactionService.getTransaction(anyString())).thenReturn(createTransaction());
        when(accountsHandlerMock.getAccountsData(anyString())).thenReturn(new DocumentInfoResponse());

        assertNotNull(accountsDocumentInfoService.getDocumentInfo(createDocumentInfoRequest()));
    }

    private DocumentInfoRequest createDocumentInfoRequest() {
        DocumentInfoRequest documentInfoRequest = new DocumentInfoRequest();
        documentInfoRequest.setResourceId(RESOURCE_ID);
        documentInfoRequest.setResourceUri(RESOURCE_URI);
        return documentInfoRequest;
    }

    private Transaction createTransaction() {
        Map<String, Resource> resources = new HashMap<>();
        resources.put(RESOURCE_ID, createResource());

        Transaction transaction = new Transaction();
        transaction.setResources(resources);

        return transaction;
    }

    private Resource createResource() {
        Resource resource = new Resource();
        resource.setKind("kind");
        Map<String, String> links = new HashMap<>();
        links.put("resource", RESOURCE_ID);
        resource.setLinks(links);
        return resource;
    }
}
