package uk.gov.companieshouse.document.generator.accounts.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.data.accounts.AccountsManager;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AccountsServiceImplTest {

    @InjectMocks
    private AccountsServiceImpl accountsService;

    @Mock
    private AccountsManager accountsManager;

    private static final String REQUEST_ID = "requestId";
    private static final String RESOURCE = "resource";

    @Test
    @DisplayName("Tests unsuccessful retrieval of accounts that throws exception")
    void testGetAccountsThrownException() throws Exception {
        when(accountsManager.getAccounts(anyString())).thenThrow(new URIValidationException(""));

        assertThrows(ServiceException.class, () -> accountsService.getAccounts(RESOURCE, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests unsuccessful retrieval of accounts that returns null")
    void testGetAccountsReturningNull() throws Exception {
        when(accountsManager.getAccounts(anyString())).thenReturn(null);

        assertNull(accountsService.getAccounts(RESOURCE, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests successful retrieval of accounts")
    void testGetAccountsSuccess() throws Exception {
        when(accountsManager.getAccounts(anyString())).thenReturn(new Accounts());

        assertNotNull(accountsService.getAccounts(RESOURCE, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests unsuccessful retrieval of abridged accounts that throws exception")
    void testGetAbridgedAccountsThrownException() throws Exception {
        when(accountsManager.getAbridgedAccounts(anyString())).thenThrow(new URIValidationException(""));

        assertThrows(ServiceException.class, () -> accountsService.getAbridgedAccounts(RESOURCE, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests unsuccessful retrieval of abridged accounts that returns null")
    void testGetAbridgedAccountsReturningNull() throws Exception {
        when(accountsManager.getAbridgedAccounts(anyString())).thenReturn(null);

        assertNull(accountsService.getAbridgedAccounts(RESOURCE, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests successful retrieval of an abridged accounts")
    void testGetAbridgedAccountsSuccess() throws Exception {
        when(accountsManager.getAbridgedAccounts(anyString())).thenReturn(new AbridgedAccountsApi());

        assertNotNull(accountsService.getAbridgedAccounts(RESOURCE, REQUEST_ID));
    }
}
