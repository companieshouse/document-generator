package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AccountsHandlerImplTest {

    @InjectMocks
    private AccountsHandlerImpl accountsHandlerImpl;

    @Mock
    private AccountsService accountsService;

    private static final String ACCOUNTS_RESOURCE_LINK = "/transactions/091174-913515-326060";
    private static final String ABRIDGED_ACCOUNTS_RESOURCE_LINK = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";

    @Test
    @DisplayName("Tests the unsuccessful return of accounts data due to failure in service layer")
    void testGetAccountsDataFailureFromServiceLayer() throws ServiceException {
        when(accountsService.getAccounts(anyString())).thenThrow(new ServiceException("Failure in service layer"));

        assertThrows(ServiceException.class, () -> accountsService.getAccounts(anyString()));
        assertThrows(HandlerException.class, () -> accountsHandlerImpl.getAccountsData(ACCOUNTS_RESOURCE_LINK));
    }

    @Test
    @DisplayName("Tests the successful return of accounts data")
    void testGetAccountsData() throws ServiceException, HandlerException {
        when(accountsService.getAccounts(anyString())).thenReturn(createAccountsObject());
        assertNotNull(accountsHandlerImpl.getAccountsData(ACCOUNTS_RESOURCE_LINK));
    }

    private Accounts createAccountsObject() {
        Accounts accounts = new Accounts();

        Map<String, String> links = new HashMap<>();
        links.put("abridged_accounts", ABRIDGED_ACCOUNTS_RESOURCE_LINK);
        accounts.setLinks(links);

        return accounts;
    }
}
