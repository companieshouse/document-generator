package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.CompanyAccounts;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SmallFullAccountsDataHandlerTest {

    @InjectMocks
    private SmallFullAccountsDataHandler smallFullAccountsDataHandler;

    @Mock
    private AccountsService accountsService;

    @Mock
    private Transaction transaction;

    private static final String ACCOUNTS_RESOURCE_LINK = "/transactions/091174-913515-326060";
    private static final String COMPANY_ACCOUNTS_RESOURCE_LINK = "/transactions/091174-913515-326060/company-accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";
    private static final String REQUEST_ID = "requestId";
    private static final String SERVICE_EXCEPTION = "Failure in service layer";

    @Test
    @DisplayName("Tests the unsuccessful return of accounts data due to failure in service layer")
    void testGetAccountsDataFailureFromServiceLayer() throws ServiceException {
        when(accountsService.getCompanyAccounts(anyString(), anyString())).thenThrow(new ServiceException(SERVICE_EXCEPTION));

        assertThrows(HandlerException.class, () -> smallFullAccountsDataHandler.getSmallFullAccountsData(transaction, ACCOUNTS_RESOURCE_LINK, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests the unsuccessful return of SmallFull; accounts data due to failure in service layer")
    void testGetSmallFullAccountsDataFailureFromServiceLayer() throws ServiceException {
        when(accountsService.getCompanyAccounts(anyString(), anyString())).thenReturn(createCompanyAccounts());
        when(accountsService.getSmallFullAccounts(anyString(), anyString(), any(Transaction.class))).thenThrow(new ServiceException(SERVICE_EXCEPTION));

        assertThrows(HandlerException.class, () -> smallFullAccountsDataHandler.getSmallFullAccountsData(transaction, ACCOUNTS_RESOURCE_LINK, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests the successful return of SmallFull accounts data")
    void testGetSmallFullAccountsData() throws ServiceException, HandlerException {
        when(accountsService.getCompanyAccounts(anyString(), anyString())).thenReturn(createCompanyAccounts());
        when(accountsService.getSmallFullAccounts(anyString(), anyString(), any(Transaction.class))).thenReturn(createCurrentSmallFullAccounts());

        assertNotNull(smallFullAccountsDataHandler.getSmallFullAccountsData(transaction, ACCOUNTS_RESOURCE_LINK, REQUEST_ID));
    }

    private SmallFullAccountIxbrl createCurrentSmallFullAccounts() {

        SmallFullAccountIxbrl smallFullAccountIxbrl = new SmallFullAccountIxbrl();

        smallFullAccountIxbrl.setBalanceSheet(new BalanceSheet());
        smallFullAccountIxbrl.setPeriod(createPeriod());
        smallFullAccountIxbrl.setCompany(new Company());
        smallFullAccountIxbrl.setApprovalDate("2018-01-01");
        smallFullAccountIxbrl.setApprovalName("name");

        return smallFullAccountIxbrl;
    }

    private Period createPeriod() {

        Period period = new Period();
        period.setCurrentPeriodStartOn("2018-01-01");
        period.setCurrentPeriodEndsOn("2018-12-31");

        return period;
    }

    private CompanyAccounts createCompanyAccounts() {

        CompanyAccountsApi companyAccounts = new CompanyAccountsApi();
        Map<String, String> links = new HashMap<>();
        links.put("abridged_accounts", COMPANY_ACCOUNTS_RESOURCE_LINK);
        companyAccounts.setLinks(links);

        return companyAccounts;
    }
}
