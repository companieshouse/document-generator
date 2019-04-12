package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.CompanyAccountsApi;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.DebtorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.currentassetsinvestments.CurrentAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.SmallFullApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.EmployeesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.tangible.TangibleApi;
import uk.gov.companieshouse.document.generator.accounts.data.transaction.Transaction;
import uk.gov.companieshouse.document.generator.accounts.exception.ServiceException;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers.SmallFullIXBRLMapper;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.service.ApiClientService;
import uk.gov.companieshouse.document.generator.accounts.service.CompanyService;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

/**
 * Temporary solution until private-sdk has been completed (SFA-518, SFA-670). When completed, this
 * file will get removed alongside the data package and all references to this file will be replaced
 * with calls to the private-sdk.
 */
@Component
public class AccountsManager {

    @Autowired
    private ApiClientService apiClientService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SmallFullIXBRLMapper smallFullIXBRLMapper;

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private static final String NOT_FOUND_API_DATA = "No data found in %s api for link: ";

    private static final String SMALL_FULL_LINK_SUFFIX = "small-full";

    /**
     * Get accounts resource if exists
     *
     * @param link - self link for the accounts resource
     * @return accounts object along with the status or not found status.
     * @throws ApiErrorResponseException
     * @throws URIValidationException
     */
    public Accounts getAccounts(String link) throws ApiErrorResponseException,
            URIValidationException {

        ApiClient apiClient = apiClientService.getApiClient();

        return apiClient.accounts().get(link).execute();
    }

    /**
     * Get abridged accounts resource if exists
     *
     * @param link - self link for the abridged accounts resource
     * @return AbridgedAccountsApi object
     * @throws ApiErrorResponseException
     * @throws URIValidationException
     */
    public AbridgedAccountsApi getAbridgedAccounts(String link) throws ApiErrorResponseException
            , URIValidationException {

        ApiClient apiClient = apiClientService.getApiClient();

        return apiClient.abridgedAccounts().get(link).execute();
    }

    /**
     * Get company-accounts resource if exists
     *
     * @param link - self link for the accounts resource
     * @return CompanyAccountsApi object along with the status or not found status.
     * @throws ApiErrorResponseException
     * @throws URIValidationException
     */
    public CompanyAccountsApi getCompanyAccounts(String link) throws ApiErrorResponseException,
            URIValidationException {

        ApiClient apiClient = apiClientService.getApiClient();

        return apiClient.companyAccounts().get(link).execute();
    }

    /**
     * Get smallFull resources if exits and map to SmallFull IXBRL model
     *
     * @param link - self link for the abridged accounts resource
     * @return SmallFullAccountIxbrl object
     * @throws ApiErrorResponseException
     * @throws URIValidationException
     */
    public SmallFullAccountIxbrl getSmallFullAccounts(String link, Transaction transaction)
            throws URIValidationException, ServiceException, ApiErrorResponseException {

        SmallFullApiData smallFullApiData = new SmallFullApiData();

        ApiClient apiClient = apiClientService.getApiClient();

        String errorString = "small full";

        try {

            SmallFullApi smallFull = apiClient.smallFull().get(link).execute();

            errorString = "company accounts";

            String companyAccountsLink = StringUtils.stripEnd(link, "/" + SMALL_FULL_LINK_SUFFIX);
            CompanyAccountsApi companyAccountsApi = apiClient.companyAccounts().get(companyAccountsLink).execute();
            smallFullApiData.setCompanyAccounts(companyAccountsApi);

            if (!StringUtils.isEmpty(smallFull.getLinks().getPreviousPeriod())) {

                errorString = "previous period";

                PreviousPeriodApi previousPeriod = apiClient.smallFull().previousPeriod()
                        .get(smallFull.getLinks().getPreviousPeriod()).execute();
                smallFullApiData.setPreviousPeriod(previousPeriod);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getCurrentPeriod())) {

                errorString = "current period";

                CurrentPeriodApi currentPeriod = apiClient.smallFull().currentPeriod()
                        .get(smallFull.getLinks().getCurrentPeriod()).execute();
                smallFullApiData.setCurrentPeriod(currentPeriod);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getApproval())) {

                errorString = "approvals";

                ApprovalApi approvals = apiClient.smallFull().approval()
                        .get(smallFull.getLinks().getApproval()).execute();
                smallFullApiData.setApproval(approvals);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getStatements())) {

                errorString = "statements";

                BalanceSheetStatementsApi statements = apiClient.smallFull().balanceSheetStatements()
                        .get(smallFull.getLinks().getStatements()).execute();
                smallFullApiData.setBalanceSheetStatements(statements);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getAccountingPolicyNote())) {

                errorString = "accounting policies";

                AccountingPoliciesApi policies = apiClient.smallFull().accountingPolicies()
                        .get(smallFull.getLinks().getAccountingPolicyNote()).execute();
                smallFullApiData.setAccountingPolicies(policies);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getTangibleAssetsNote())) {

                errorString = "tangible assets";

                TangibleApi tangible = apiClient.smallFull().tangible()
                        .get(smallFull.getLinks().getTangibleAssetsNote()).execute();

                smallFullApiData.setTangibleAssets(tangible);
            }


            if (!StringUtils.isEmpty(smallFull.getLinks().getEmployeesNote())) {

                errorString = "employees";

                EmployeesApi employees = apiClient.smallFull().employees()
                        .get(smallFull.getLinks().getEmployeesNote()).execute();

                smallFullApiData.setEmployees(employees);
            }


            if (!StringUtils.isEmpty(smallFull.getLinks().getStocksNote())) {

                errorString = "stocks";

                StocksApi stocks = apiClient.smallFull().stocks()
                        .get(smallFull.getLinks().getStocksNote()).execute();

                smallFullApiData.setStocks(stocks);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getDebtorsNote())) {

                errorString = "debtors";

                DebtorsApi debtors = apiClient.smallFull().debtors()
                        .get(smallFull.getLinks().getDebtorsNote()).execute();

                smallFullApiData.setDebtors(debtors);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getCurrentAssetsInvestmentsNote())) {

                errorString = "current assets investments";

                CurrentAssetsInvestmentsApi currentAssetsInvestmentsApi = apiClient.smallFull().currentAssetsInvestments()
                        .get(smallFull.getLinks().getCurrentAssetsInvestmentsNote()).execute();

                smallFullApiData.setCurrentAssetsInvestments(currentAssetsInvestmentsApi);
            }
            
            if (!StringUtils.isEmpty(smallFull.getLinks().getCreditorsWithinOneYearNote())) {

                errorString = "creditors within one year";

                CreditorsWithinOneYearApi creditorsWithinOneYearApi = apiClient.smallFull().creditorsWithinOneYear()
                        .get(smallFull.getLinks().getCreditorsWithinOneYearNote()).execute();

                smallFullApiData.setCreditorsWithinOneYear(creditorsWithinOneYearApi);
            }
            
            if (!StringUtils.isEmpty(smallFull.getLinks().getCreditorsAfterMoreThanOneYearNote())) {

                errorString = "creditors after one year";

                CreditorsAfterOneYearApi creditorsAfterOneYearApi = apiClient.smallFull().creditorsAfterOneYear()
                        .get(smallFull.getLinks().getCreditorsAfterMoreThanOneYearNote()).execute();

                smallFullApiData.setCreditorsAfterOneYear(creditorsAfterOneYearApi);
            }

        } catch (ApiErrorResponseException e) {
            handleException(e, errorString, link);
        }
        
        smallFullApiData.setCompanyProfile(companyService.getCompanyProfile(transaction.getCompanyNumber()));


        return smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);
    }

    private void handleException(ApiErrorResponseException e, String text, String link)
            throws ApiErrorResponseException {

        if (e.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
            LOG.info(String.format(NOT_FOUND_API_DATA, text, link), setDebugMap(link));
        } else {
            throw e;
        }
    }

    private Map<String, Object> setDebugMap(String link) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("LINK", link);

        return logMap;
    }
}
