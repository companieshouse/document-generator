package uk.gov.companieshouse.document.generator.accounts.data.accounts;

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
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Resources;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.AccountsLinkNotFoundException;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.cic.model.CicReport;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.service.AccountsService;

import java.util.HashMap;
import java.util.Map;
import uk.gov.companieshouse.document.generator.accounts.service.CicReportService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyAccountsDocumentDataManagerTest {

    @InjectMocks
    private CompanyAccountsDocumentDataManager companyAccountsDocumentDataManager;

    @Mock
    private AccountsService mockAccountsService;

    @Mock
    private CicReportService cicReportService;

    @Mock
    private SmallFullAccountIxbrl smallFullAccountIxbrl;

    @Mock
    private CicReport cicReport;

    private static final String COMPANY_ACCOUNTS_RESOURCE_URI = "/transactions/091174-913515-326060/company-accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";

    private static final String CIC_REPORT_RESOURCE_URI = COMPANY_ACCOUNTS_RESOURCE_URI + "/cic-report";

    private static final String SERVICE_EXCEPTION = "Failure in service layer";

    private static final String SMALL_FULL_ACCOUNTS_RESOURCE = "small_full_accounts";

    @Test
    @DisplayName("Tests successful return of accounts data for small full")
    void testSuccessfulReturnOfAccountsDataForSmallFull() throws ServiceException, AccountsLinkNotFoundException {

        when(mockAccountsService.getSmallFullAccounts(anyString(), anyString(), any(Transaction.class)))
                .thenReturn(smallFullAccountIxbrl);

        IxbrlDataWrapper ixbrlDataWrapper =
                companyAccountsDocumentDataManager.getCompanyAccountDocumentData(createCompanyAccounts(true),
                        createAccountType(true), createTransaction(COMPANY_ACCOUNTS_RESOURCE_URI),
                                COMPANY_ACCOUNTS_RESOURCE_URI);

        assertNotNull(ixbrlDataWrapper);
        assertNotNull(ixbrlDataWrapper.getAccounts());
        assertTrue(ixbrlDataWrapper.getAccounts().containsKey(SMALL_FULL_ACCOUNTS_RESOURCE));
        assertEquals(smallFullAccountIxbrl, ixbrlDataWrapper.getAccounts().get(SMALL_FULL_ACCOUNTS_RESOURCE));
        assertNull(ixbrlDataWrapper.getCicReport());
    }

    @Test
    @DisplayName("Tests successful return of accounts data for small full with cic report data")
    void testSuccessfulReturnOfAccountsDataForSmallFullWithCicReport() throws ServiceException, AccountsLinkNotFoundException {

        when(mockAccountsService.getSmallFullAccounts(anyString(), anyString(), any(Transaction.class)))
                .thenReturn(smallFullAccountIxbrl);

        when(cicReportService.getCicReport(anyString(), anyString()))
                .thenReturn(cicReport);

        IxbrlDataWrapper ixbrlDataWrapper =
                companyAccountsDocumentDataManager.getCompanyAccountDocumentData(createCompanyAccountsWithCicReport(),
                        createAccountType(true), createTransaction(COMPANY_ACCOUNTS_RESOURCE_URI),
                                COMPANY_ACCOUNTS_RESOURCE_URI);

        assertNotNull(ixbrlDataWrapper);
        assertNotNull(ixbrlDataWrapper.getAccounts());
        assertTrue(ixbrlDataWrapper.getAccounts().containsKey(SMALL_FULL_ACCOUNTS_RESOURCE));
        assertEquals(smallFullAccountIxbrl, ixbrlDataWrapper.getAccounts().get(SMALL_FULL_ACCOUNTS_RESOURCE));
        assertEquals(cicReport, ixbrlDataWrapper.getCicReport());
    }

    @Test
    @DisplayName("Tests service error thrown when obtaining data for small full")
    void testServiceErrorThrownWhenObtainingSmallFullAccounts() throws ServiceException {

        when(mockAccountsService.getSmallFullAccounts(anyString(), anyString(), any(Transaction.class)))
                .thenThrow(new ServiceException(SERVICE_EXCEPTION));

        assertThrows(ServiceException.class, () -> companyAccountsDocumentDataManager.getCompanyAccountDocumentData(
                createCompanyAccounts(true), createAccountType(true),
                createTransaction(COMPANY_ACCOUNTS_RESOURCE_URI), COMPANY_ACCOUNTS_RESOURCE_URI));
    }

    @Test
    @DisplayName("Tests AccountsLinkNotFoundException thrown when no small full account link found")
    void testErrorThrownWhenNoSmallFullAccountLinkFound() {

        assertThrows(AccountsLinkNotFoundException.class, () -> companyAccountsDocumentDataManager.getCompanyAccountDocumentData(
                createCompanyAccounts(false), createAccountType(true),
                createTransaction(COMPANY_ACCOUNTS_RESOURCE_URI), COMPANY_ACCOUNTS_RESOURCE_URI));
    }

    @Test
    @DisplayName("Tests AccountsLinkNotFoundException thrown when no account type link found")
    void testErrorThrownWhenNoAccountTypeLinkFound() {

        assertThrows(AccountsLinkNotFoundException.class, () -> companyAccountsDocumentDataManager.getCompanyAccountDocumentData(
                createCompanyAccounts(false), createAccountType(false),
                createTransaction(COMPANY_ACCOUNTS_RESOURCE_URI), COMPANY_ACCOUNTS_RESOURCE_URI));
    }

    private CompanyAccountsApi createCompanyAccounts(boolean validLink) {

        CompanyAccountsApi companyAccounts = new CompanyAccountsApi();
        CompanyAccountsLinks links = new CompanyAccountsLinks();
        links.setTransaction("/transactions/091174-913515-326060");
        if(validLink) {
            links.setSmallFullAccounts(COMPANY_ACCOUNTS_RESOURCE_URI);
        } else {
            links.setSmallFullAccounts(null);
        }
        companyAccounts.setLinks(links);

        return companyAccounts;
    }

    private CompanyAccountsApi createCompanyAccountsWithCicReport() {

        CompanyAccountsApi companyAccounts = createCompanyAccounts(true);
        companyAccounts.getLinks().setCicReport(CIC_REPORT_RESOURCE_URI);

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

    private AccountType createAccountType(boolean validAccountType) {
        if (validAccountType) {
            return AccountType.getAccountType("small_full_accounts");
        } else {
            return AccountType.getAccountType("abridged_accounts");
        }
    }
}
