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
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.DirectorsReportApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.SecretaryApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.StatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.BalanceSheetStatementsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.CurrentPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.Debtors.DebtorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.PreviousPeriodApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.SmallFullApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorsafteroneyear.CreditorsAfterOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.creditorswithinoneyear.CreditorsWithinOneYearApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.currentassetsinvestments.CurrentAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.employees.EmployeesApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.fixedassetsinvestments.FixedAssetsInvestmentsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.intangible.IntangibleApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.loanstodirectors.LoansToDirectorsApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.offBalanceSheet.OffBalanceSheetApi;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.StocksApi;
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

        return apiClient.accounts().get(link).execute().getData();
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

        return apiClient.abridgedAccounts().get(link).execute().getData();
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

        return apiClient.companyAccounts().get(link).execute().getData();
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

            SmallFullApi smallFull = apiClient.smallFull().get(link).execute().getData();

            errorString = "company accounts";

            String companyAccountsLink = StringUtils.stripEnd(link, "/" + SMALL_FULL_LINK_SUFFIX);
            smallFullApiData.setSmallFull(smallFull);

            if (!StringUtils.isEmpty(smallFull.getLinks().getPreviousPeriod())) {

                errorString = "previous period";

                PreviousPeriodApi previousPeriod = apiClient.smallFull().previousPeriod()
                        .get(smallFull.getLinks().getPreviousPeriod()).execute().getData();
                smallFullApiData.setPreviousPeriod(previousPeriod);

                if (!StringUtils.isEmpty(previousPeriod.getLinks().getProfitAndLoss())) {
                    smallFullApiData.setPreviousPeriodProfitAndLoss(
                            apiClient.smallFull().previousPeriodProfitAndLoss()
                                    .get(previousPeriod.getLinks().getProfitAndLoss()).execute().getData());
                }
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getCurrentPeriod())) {

                errorString = "current period";

                CurrentPeriodApi currentPeriod = apiClient.smallFull().currentPeriod()
                        .get(smallFull.getLinks().getCurrentPeriod()).execute().getData();
                smallFullApiData.setCurrentPeriod(currentPeriod);

                if (!StringUtils.isEmpty(currentPeriod.getLinks().getProfitAndLoss())) {
                    smallFullApiData.setCurrentPeriodProfitAndLoss(
                            apiClient.smallFull().currentPeriodProfitAndLoss()
                                    .get(currentPeriod.getLinks().getProfitAndLoss()).execute().getData());
                }
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getApproval())) {

                errorString = "approvals";

                ApprovalApi approvals = apiClient.smallFull().approval()
                        .get(smallFull.getLinks().getApproval()).execute().getData();
                smallFullApiData.setApproval(approvals);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getStatements())) {

                errorString = "statements";

                BalanceSheetStatementsApi statements = apiClient.smallFull().balanceSheetStatements()
                        .get(smallFull.getLinks().getStatements()).execute().getData();
                smallFullApiData.setBalanceSheetStatements(statements);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getAccountingPoliciesNote())) {

                errorString = "accounting policies";

                AccountingPoliciesApi policies = apiClient.smallFull().accountingPolicies()
                        .get(smallFull.getLinks().getAccountingPoliciesNote()).execute().getData();
                smallFullApiData.setAccountingPolicies(policies);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getTangibleAssetsNote())) {

                errorString = "tangible assets";

                TangibleApi tangible = apiClient.smallFull().tangible()
                        .get(smallFull.getLinks().getTangibleAssetsNote()).execute().getData();

                smallFullApiData.setTangibleAssets(tangible);
            }

            if(!StringUtils.isEmpty(smallFull.getLinks().getIntangibleAssetsNote())) {

                errorString = "intangible assets";

                IntangibleApi intangibleApi = apiClient.smallFull().intangible()
                        .get(smallFull.getLinks().getIntangibleAssetsNote()).execute().getData();
                smallFullApiData.setIntangibleAssets(intangibleApi);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getEmployeesNote())) {

                errorString = "employees";

                EmployeesApi employees = apiClient.smallFull().employees()
                        .get(smallFull.getLinks().getEmployeesNote()).execute().getData();

                smallFullApiData.setEmployees(employees);
            }


            if (!StringUtils.isEmpty(smallFull.getLinks().getStocksNote())) {

                errorString = "stocks";

                StocksApi stocks = apiClient.smallFull().stocks()
                        .get(smallFull.getLinks().getStocksNote()).execute().getData();

                smallFullApiData.setStocks(stocks);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getDebtorsNote())) {

                errorString = "debtors";

                DebtorsApi debtors = apiClient.smallFull().debtors()
                        .get(smallFull.getLinks().getDebtorsNote()).execute().getData();

                smallFullApiData.setDebtors(debtors);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getCurrentAssetsInvestmentsNote())) {

                errorString = "current assets investments";

                CurrentAssetsInvestmentsApi currentAssetsInvestmentsApi = apiClient.smallFull().currentAssetsInvestments()
                        .get(smallFull.getLinks().getCurrentAssetsInvestmentsNote()).execute().getData();

                smallFullApiData.setCurrentAssetsInvestments(currentAssetsInvestmentsApi);
            }
            
            if (!StringUtils.isEmpty(smallFull.getLinks().getCreditorsWithinOneYearNote())) {

                errorString = "creditors within one year";

                CreditorsWithinOneYearApi creditorsWithinOneYearApi = apiClient.smallFull().creditorsWithinOneYear()
                        .get(smallFull.getLinks().getCreditorsWithinOneYearNote()).execute().getData();

                smallFullApiData.setCreditorsWithinOneYear(creditorsWithinOneYearApi);
            }
            
            if (!StringUtils.isEmpty(smallFull.getLinks().getCreditorsAfterMoreThanOneYearNote())) {

                errorString = "creditors after one year";

                CreditorsAfterOneYearApi creditorsAfterOneYearApi = apiClient.smallFull().creditorsAfterOneYear()
                        .get(smallFull.getLinks().getCreditorsAfterMoreThanOneYearNote()).execute().getData();

                smallFullApiData.setCreditorsAfterOneYear(creditorsAfterOneYearApi);
            }
            
            if (!StringUtils.isEmpty(smallFull.getLinks().getFixedAssetsInvestmentsNote())) {

                errorString = "fixed assets investments";
                
                FixedAssetsInvestmentsApi fixedAssetsInvestmentsApi = apiClient.smallFull().fixedAssetsInvestments()
                        .get(smallFull.getLinks().getFixedAssetsInvestmentsNote()).execute().getData();

                smallFullApiData.setFixedAssetsInvestments(fixedAssetsInvestmentsApi);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getOffBalanceSheetArrangementsNote())) {

                errorString = "off balance sheet arrangements";

                OffBalanceSheetApi offBalanceSheetApi = apiClient.smallFull().offBalanceSheet()
                        .get(smallFull.getLinks().getOffBalanceSheetArrangementsNote()).execute().getData();

                smallFullApiData.setOffBalanceSheet(offBalanceSheetApi);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getLoansToDirectors())) {

                setLoansToDirectors(smallFull, apiClient, link, smallFullApiData);
            }

            if (!StringUtils.isEmpty(smallFull.getLinks().getDirectorsReport())) {

                errorString = "statements";
                DirectorsReportApi directorsReport = apiClient.smallFull().directorsReport()
                        .get(smallFull.getLinks().getDirectorsReport()).execute().getData();

                smallFullApiData.setDirectorsReport(directorsReport);

                if (!StringUtils.isEmpty(directorsReport.getLinks().getStatements())) {

                    StatementsApi statements = apiClient.smallFull().directorsReport().statements()
                            .get(directorsReport.getLinks().getStatements()).execute().getData();
                    smallFullApiData.setDirectorsReportStatements(statements);
                }

                Object directorLinks =  directorsReport.getDirectors().values().toArray()[0];

                String directorsLink = (String) directorLinks;
                directorsLink = directorsLink.substring(0, directorsLink.lastIndexOf('/'));
                DirectorApi[] directors = apiClient.smallFull().directorsReport().directors().getAll(directorsLink).execute().getData();

                smallFullApiData.setDirectors(directors);

                if (!StringUtils.isEmpty(directorsReport.getLinks().getSecretary())) {

                    SecretaryApi secretary = apiClient.smallFull().directorsReport().secretary()
                            .get(directorsReport.getLinks().getSecretary()).execute().getData();
                    smallFullApiData.setSecretary(secretary);
                }

                if (!StringUtils.isEmpty(directorsReport.getLinks().getApproval())) {

                    uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi approval = apiClient.smallFull().directorsReport().approval()
                            .get(directorsReport.getLinks().getApproval()).execute().getData();
                    smallFullApiData.setDirectorsApproval(approval);
                }
            }

        } catch (ApiErrorResponseException e) {
            handleException(e, errorString, link);
        }
        
        smallFullApiData.setCompanyProfile(companyService.getCompanyProfile(transaction.getCompanyNumber()));


        return smallFullIXBRLMapper.mapSmallFullIXBRLModel(smallFullApiData);
    }

    private void setLoansToDirectors(SmallFullApi smallFull, ApiClient apiClient, String link, SmallFullApiData smallFullApiData) throws ApiErrorResponseException, URIValidationException {

        String errorString = "";

        try {

            errorString = "loans to directors";

            LoansToDirectorsApi loansToDirectors =
                    apiClient.smallFull().loansToDirectors()
                            .get(smallFull.getLinks().getLoansToDirectors()).execute().getData();

            smallFullApiData.setLoansToDirectors(loansToDirectors);

            if (loansToDirectors.getLoans() != null && !loansToDirectors.getLoans().isEmpty()) {

                errorString = "loans";

                // Find the first loan id from the map of loans
                String loanId =
                        loansToDirectors.getLoans()
                                .keySet().stream().findFirst()
                                        .orElseThrow(() -> new IllegalStateException("No loans found in links"));

                // Use the loan id to derive a loan self link from the map of loans
                String loanSelfLink = loansToDirectors.getLoans().get(loanId);

                // Trim the end of the self link to calculate a generic 'loans' link, used to fetch all loans
                String loansLink = loanSelfLink.substring(0, loanSelfLink.lastIndexOf('/'));

                smallFullApiData.setLoans(
                        apiClient.smallFull().loansToDirectors().loans().getAll(loansLink).execute()
                                .getData());
            }

            if (!StringUtils.isEmpty(loansToDirectors.getLinks().getAdditionalInformation())) {

                errorString = "loans additional info";

                smallFullApiData.setLoansAdditionalInfo(
                        apiClient.smallFull().loansToDirectors().additionalInformation()
                                .get(loansToDirectors.getLinks().getAdditionalInformation())
                                    .execute().getData());
            }
        } catch (ApiErrorResponseException e) {
            handleException(e, errorString, link);
        }
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
