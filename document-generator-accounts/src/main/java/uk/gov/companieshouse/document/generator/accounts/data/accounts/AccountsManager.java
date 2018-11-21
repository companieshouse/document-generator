package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.CompanyAccounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
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

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private static final String ERROR_CALLING_API = "An Api Error Response was thrown when obtaining the %s for link: %s";

    /**
     * Get accounts resource if exists
     *
     * @param link - self link for the accounts resource
     * @return accounts object along with the status or not found status.
     * @throws ApiErrorResponseException
     * @throws URIValidationException
     */
    public Accounts getAccounts(String link) throws ApiErrorResponseException, URIValidationException {

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
    public AbridgedAccountsApi getAbridgedAccounts(String link) throws ApiErrorResponseException, URIValidationException {

        ApiClient apiClient = apiClientService.getApiClient();

        return apiClient.abridgedAccounts().get(link).execute();
    }

    /**
     * Get company-accounts resource if exists
     *
     * @param link - self link for the accounts resource
     * @return CompanyAccounts object along with the status or not found status.
     * @throws ApiErrorResponseException
     * @throws URIValidationException
     */
    public CompanyAccounts getCompanyAccounts(String link) throws ApiErrorResponseException, URIValidationException {

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
            throws URIValidationException, ServiceException {

        SmallFullApiData smallFullApiData = new SmallFullApiData();

        ApiClient apiClient = apiClientService.getApiClient();

        try {
            smallFullApiData.setPreviousPeriod(apiClient.smallFull().previousPeriod()
                    .get(new StringBuilder(link).append("/previous-period").toString()).execute());
        } catch (ApiErrorResponseException e)  {
            LOG.errorContext(String.format(ERROR_CALLING_API, "previous period", link), e, setDebugMap(link));
        }

        try {
            smallFullApiData.setCurrentPeriod(apiClient.smallFull().currentPeriod()
                    .get(new StringBuilder(link).append("/current-period").toString()).execute());
        } catch (ApiErrorResponseException e) {
            LOG.errorContext(String.format(ERROR_CALLING_API, "current period", link), e, setDebugMap(link));
        }

        try {
            smallFullApiData.setApproval(apiClient.smallFull().approval()
                    .get(new StringBuilder(link).append("/approval").toString()).execute());
        } catch (ApiErrorResponseException e) {
            LOG.errorContext(String.format(ERROR_CALLING_API, "approvals", link), e, setDebugMap(link));
        }

        smallFullApiData.setCompanyProfile(companyService.getCompanyProfile(transaction.getCompanyNumber()));


        return SmallFullIXBRLMapper.INSTANCE.mapSmallFullIXBRLModel(smallFullApiData);
    }

    private Map<String,Object> setDebugMap(String link) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("LINK", link);

        return logMap;
    }
}
