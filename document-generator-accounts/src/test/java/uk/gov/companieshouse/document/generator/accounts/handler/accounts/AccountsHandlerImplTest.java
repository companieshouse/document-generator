package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccountsHandlerImplTest {

    @Test
    @DisplayName("Tests the successful return of accounts data")
    void testGetAccountsData() {
        AccountsHandlerImpl accountsHandler = new AccountsHandlerImpl();
        assertNotNull(accountsHandler.getAccountsData(""));
    }
}
