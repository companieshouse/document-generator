package uk.gov.companieshouse.document.generator.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.transaction.Resource;
import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfo;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AccountsDocumentInfoServiceImplTest {

    @InjectMocks
    private AccountsDocumentInfoServiceImpl accountsDocumentInfoService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private AccountsService accountsService;

    @Test
    @DisplayName("Tests the unsuccessful retrieval of an accounts document data due to no accounts resource in transaction")
    void testUnsuccessfulGetDocumentInfoNoAccountsResourceInTransaction() {
        when(transactionService.getTransaction(anyString())).thenReturn(createTransaction());
        assertNull(accountsDocumentInfoService.getDocumentInfo());
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of an accounts document data due to error in transaction retrieval")
    void testUnsuccessfulGetDocumentInfoFailedTransactionRetrieval() {
        when(transactionService.getTransaction(anyString())).thenReturn(null);
        assertNull(accountsDocumentInfoService.getDocumentInfo());
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of an accounts document data due to null accounts data from service")
    void testUnSuccessfulGetDocumentInfoNullAccountRetrieval() {
        when(transactionService.getTransaction(anyString())).thenReturn(createTransaction());
        when(accountsService.getAccounts(anyString())).thenReturn(null);
        assertNull(accountsDocumentInfoService.getDocumentInfo());
    }

    @Test
    @DisplayName("Tests the unsuccessful retrieval of accounts document data due to no valid account type in the accounts links data")
    void testUnSuccessfulGetDocumentInfoNoAccountTypeInAccountsDataLinks() {
        when(transactionService.getTransaction(anyString())).thenReturn(createTransaction());

        Accounts accounts = createAccounts();
        accounts.getLinks().remove("abridged_accounts");
        when(accountsService.getAccounts(anyString())).thenReturn(accounts);

        assertNull(accountsDocumentInfoService.getDocumentInfo());
    }

    @Test
    @DisplayName("Tests the successful retrieval of an abridged accounts document data")
    void testSuccessfulGetDocumentInfo() {
        when(transactionService.getTransaction(anyString())).thenReturn(createTransaction());
        when(accountsService.getAccounts(anyString())).thenReturn(createAccounts());
        assertEquals(DocumentInfo.class, accountsDocumentInfoService.getDocumentInfo().getClass());
    }

    private Accounts createAccounts() {
        Accounts accounts =  new Accounts();
        Map<String, String> linksMap = new HashMap<>();
        linksMap.put("abridged_accounts", "/transactions/175725-236115-324362/accounts/wQSM2bWmQR3zrIw3x7apJOBjzWY=/abridged/0NbSemRX4YHphNj3tRq1gjyn6rg=");
        accounts.setLinks(linksMap);
        return accounts;
    }

    private Transaction createTransaction() {
        Map<String, Resource> resources = new HashMap<>();
        resources.put("", createResource());

        Transaction transaction = new Transaction();
        transaction.setResources(resources);
        return transaction;
    }

    private Resource createResource() {
        Resource resource = new Resource();
        resource.setKind("kind");
        Map<String, String> links = new HashMap<>();
        links.put("resource", "/transactions/175725-236115-324362/accounts/wQSM2bWmQR3zrIw3x7apJOBjzWY=");
        resource.setLinks(links);
        return resource;
    }

}
