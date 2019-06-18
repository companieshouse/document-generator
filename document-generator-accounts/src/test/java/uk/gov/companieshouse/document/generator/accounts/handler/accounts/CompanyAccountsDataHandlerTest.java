package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsLinks;
import uk.gov.companieshouse.document.generator.accounts.AccountType;
import uk.gov.companieshouse.document.generator.accounts.data.IxbrlDataWrapper;
import uk.gov.companieshouse.document.generator.accounts.data.accounts.CompanyAccountsDocumentDataManager;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Resources;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.AccountsLinkNotFoundException;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.balancesheet.BalanceSheet;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.company.Company;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.period.Period;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyAccountsDataHandlerTest {

    @InjectMocks
    private CompanyAccountsDataHandler companyAccountsDataHandler;

    @Mock
    private AccountsService accountsService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private CompanyAccountsDocumentDataManager companyAccountsDocumentDataManager;

    private static final String COMPANY_ACCOUNTS_RESOURCE_URI = "/transactions/091174-913515-326060/company-accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";
    private static final String REQUEST_ID = "requestId";
    private static final String SERVICE_EXCEPTION = "Failure in service layer";

    @Test
    @DisplayName("Tests the unsuccessful return of accounts data due to failure in service layer")
    void testGetAccountsDataFailureFromServiceLayer() throws ServiceException {
        when(accountsService.getCompanyAccounts(anyString(), anyString())).thenThrow(new ServiceException(SERVICE_EXCEPTION));

        assertThrows(HandlerException.class, () -> companyAccountsDataHandler.getCompanyAccountsData(COMPANY_ACCOUNTS_RESOURCE_URI, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests an error is thrown if transaction service fails ")
    void testErrorThrownWhenTransactionServiceFails() throws ServiceException {
        when(accountsService.getCompanyAccounts(anyString(), anyString())).thenReturn(createCompanyAccounts());
        when(transactionService.getTransaction(anyString(), anyString())).thenThrow(new ServiceException(SERVICE_EXCEPTION));

        assertThrows(HandlerException.class, () -> companyAccountsDataHandler.getCompanyAccountsData(COMPANY_ACCOUNTS_RESOURCE_URI, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests the unsuccessful return of SmallFull; accounts data due to failure in service layer")
    void testGetSmallFullAccountsDataFailureFromServiceLayer() throws ServiceException, AccountsLinkNotFoundException {
        when(accountsService.getCompanyAccounts(anyString(), anyString())).thenReturn(createCompanyAccounts());
        when(transactionService.getTransaction(anyString(), anyString())).thenReturn(createTransaction(COMPANY_ACCOUNTS_RESOURCE_URI));
        when(companyAccountsDocumentDataManager.getCompanyAccountDocumentData(any(CompanyAccountsApi.class), any(AccountType.class),
                any(Transaction.class), anyString())).thenThrow(new ServiceException(SERVICE_EXCEPTION));

        assertThrows(HandlerException.class, () -> companyAccountsDataHandler.getCompanyAccountsData(COMPANY_ACCOUNTS_RESOURCE_URI, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests the successful return of SmallFull accounts data")
    void testGetSmallFullAccountsData() throws ServiceException, HandlerException, AccountsLinkNotFoundException {
        when(accountsService.getCompanyAccounts(anyString(), anyString())).thenReturn(createCompanyAccounts());
        when(transactionService.getTransaction(anyString(), anyString())).thenReturn(createTransaction(COMPANY_ACCOUNTS_RESOURCE_URI));
        when(companyAccountsDocumentDataManager.getCompanyAccountDocumentData(any(CompanyAccountsApi.class), any(AccountType.class),
                any(Transaction.class), anyString())).thenReturn(createIxbrlDataWrapper());

        assertNotNull(companyAccountsDataHandler.getCompanyAccountsData(COMPANY_ACCOUNTS_RESOURCE_URI, REQUEST_ID));
    }

    private IxbrlDataWrapper createIxbrlDataWrapper() {

        IxbrlDataWrapper ixbrlDataWrapper = new IxbrlDataWrapper();

        SmallFullAccountIxbrl smallFullAccountIxbrl = new SmallFullAccountIxbrl();
        smallFullAccountIxbrl.setBalanceSheet(new BalanceSheet());
        smallFullAccountIxbrl.setPeriod(createPeriod());
        smallFullAccountIxbrl.setCompany(new Company());
        smallFullAccountIxbrl.setApprovalDate("2018-01-01");
        smallFullAccountIxbrl.setApprovalName("name");

        ixbrlDataWrapper.setAccounts(Collections.singletonMap("small_full_accounts", smallFullAccountIxbrl));

        return ixbrlDataWrapper;
    }

    private Period createPeriod() {

        Period period = new Period();
        period.setCurrentPeriodStartOn("2018-01-01");
        period.setCurrentPeriodEndsOn("2018-12-31");

        return period;
    }

    private CompanyAccountsApi createCompanyAccounts() {
        CompanyAccountsApi companyAccounts = new CompanyAccountsApi();

        CompanyAccountsLinks links = new CompanyAccountsLinks();
        links.setTransaction("/transactions/091174-913515-326060");
        links.setSmallFullAccounts(COMPANY_ACCOUNTS_RESOURCE_URI);

        companyAccounts.setLinks(links);

        return companyAccounts;
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
