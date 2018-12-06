package uk.gov.companieshouse.document.generator.accounts.handler.accounts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.api.model.accounts.abridged.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.abridged.balancesheet.BalanceSheetApi;
import uk.gov.companieshouse.api.model.accounts.abridged.notes.CurrentNotesApi;
import uk.gov.companieshouse.api.model.common.Links;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Resources;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.HandlerException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;
import uk.gov.companieshouse.document.generator.accounts.service.CompanyService;
import uk.gov.companieshouse.document.generator.accounts.service.TransactionService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbridgedAccountsDataHandlerTest {

    @InjectMocks
    private AbridgedAccountsDataHandler abridgedAccountsDataHandler;

    @Mock
    private AccountsService accountsService;

    @Mock
    private Transaction transaction;

    @Mock
    private TransactionService transactionService;

    @Mock
    private CompanyService companyService;

    private static final String ABRIDGED_ACCOUNTS_RESOURCE_URI = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";
    private static final String REQUEST_ID = "requestId";
    private static final String COMPANY_NAME = "company_name";
    private static final String SERVICE_EXCEPTION = "Failure in service layer";

    @Test
    @DisplayName("Tests the unsuccessful return of accounts data due to failure in service layer")
    void testGetAccountsDataFailureFromServiceLayer() throws ServiceException {
        when(accountsService.getAccounts(anyString(), anyString())).thenThrow(new ServiceException(SERVICE_EXCEPTION));

        assertThrows(HandlerException.class, () -> abridgedAccountsDataHandler.getAbridgedAccountsData(ABRIDGED_ACCOUNTS_RESOURCE_URI, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests the unsuccessful return of Abridged accounts data due to failure in service layer")
    void testGetAbridgedAccountsDataFailureFromServiceLayer() throws ServiceException {
        when(accountsService.getAccounts(anyString(), anyString())).thenReturn(createAccounts());
        when(accountsService.getAbridgedAccounts(anyString(), anyString())).thenThrow(new ServiceException(SERVICE_EXCEPTION));

        assertThrows(HandlerException.class, () -> abridgedAccountsDataHandler.getAbridgedAccountsData(ABRIDGED_ACCOUNTS_RESOURCE_URI, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests the successful return of Abridged accounts data")
    void testGetAbridgedAccountsData() throws ServiceException, HandlerException {
        when(accountsService.getAccounts(anyString(), anyString())).thenReturn(createAccounts());
        when(accountsService.getAbridgedAccounts(anyString(), anyString())).thenReturn(createCurrentAbridgedAccount());
        when(transactionService.getTransaction(anyString(), anyString())).thenReturn(createTransaction(ABRIDGED_ACCOUNTS_RESOURCE_URI));
        when(companyService.getCompanyProfile(anyString())).thenReturn(createCompanyProfile());

        assertNotNull(abridgedAccountsDataHandler.getAbridgedAccountsData(ABRIDGED_ACCOUNTS_RESOURCE_URI, REQUEST_ID));
    }

    @Test
    @DisplayName("Tests an error is thrown if transaction service fails ")
    void testErrorThrownWhenTransactionServiceFails() throws ServiceException {
        when(accountsService.getAccounts(anyString(), anyString())).thenReturn(createAccounts());
        when(transactionService.getTransaction(anyString(), anyString())).thenThrow(new ServiceException(SERVICE_EXCEPTION));

        assertThrows(HandlerException.class, () -> abridgedAccountsDataHandler.getAbridgedAccountsData(ABRIDGED_ACCOUNTS_RESOURCE_URI, REQUEST_ID));
    }

    private CompanyProfileApi createCompanyProfile() {

        CompanyProfileApi companyProfileApi = new CompanyProfileApi();

        companyProfileApi.setCompanyName(COMPANY_NAME);

        return companyProfileApi;
    }

    private AbridgedAccountsApi createCurrentAbridgedAccount() {

        AbridgedAccountsApi abridgedAccountsApi = new AbridgedAccountsApi();

        abridgedAccountsApi.setCurrentPeriodApi(createCurrentPeriod());

        return abridgedAccountsApi;
    }

    private CurrentPeriodApi createCurrentPeriod() {
        CurrentPeriodApi currentPeriodApi = new CurrentPeriodApi();

        currentPeriodApi.setBalanceSheetApi(new BalanceSheetApi());
        currentPeriodApi.setCurrentNotesApi(new CurrentNotesApi());
        currentPeriodApi.setPeriodEndDate("2019-09-30");
        currentPeriodApi.setPeriodStartDate("2018-10-01");

        return currentPeriodApi;
    }

    private Accounts createAccounts() {
        Accounts accounts = new Accounts();

        Links links = new Links();
        links.setTransaction("/transactions/091174-913515-326060");
        links.setAbridgedAccounts(ABRIDGED_ACCOUNTS_RESOURCE_URI);
        accounts.setLinks(links);

        return accounts;
    }

    private Transaction createTransaction(String resourceUri) {
        Map<String, Resources> resources = new HashMap<>();
        resources.put(resourceUri, createResource(resourceUri));

        Transaction transaction = new Transaction();
        transaction.setCompanyNumber("00006400");
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
