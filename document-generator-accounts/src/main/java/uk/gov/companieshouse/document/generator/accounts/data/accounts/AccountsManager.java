package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers.SmallFullIXBRLMapper;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.SmallFullApiData;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.SmallFullAccountIxbrl;
import uk.gov.companieshouse.document.generator.accounts.service.ApiClientService;

/**
 * Temporary solution until private-sdk has been completed (SFA-518, SFA-670). When completed, this
 * file will get removed alongside the data package and all references to this file will be replaced
 * with calls to the private-sdk.
 */
@Component
public class AccountsManager {

    private static final String SMALL_FULL = "/small-full";

    @Autowired
    private ApiClientService apiClientService;

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
     * Get smallFull resources if exits and map to SmallFull IXBRL model
     *
     * @param link - self link for the abridged accounts resource
     * @return SmallFullAccountIxbrl object
     * @throws ApiErrorResponseException
     * @throws URIValidationException
     */
    public SmallFullAccountIxbrl getSmallFullAccounts(String link) throws ApiErrorResponseException, URIValidationException {

        SmallFullApiData smallFullApiData = new SmallFullApiData();

        ApiClient apiClient = apiClientService.getApiClient();

        smallFullApiData.setPreviousPeriod(apiClient.smallFull().previousPeriod()
                .get(new StringBuilder(link).append(SMALL_FULL).append("/previous-period").toString()).execute());
        smallFullApiData.setCurrentPeriod(apiClient.smallFull().currentPeriod()
                .get(new StringBuilder(link).append(SMALL_FULL).append("/current-period").toString()).execute());
        smallFullApiData.setCompanyProfile(apiClient.company()
                .get(new StringBuilder(link).append(SMALL_FULL).toString()).execute());
        smallFullApiData.setApproval(apiClient.smallFull().approval()
                .get(new StringBuilder(link).append(SMALL_FULL).append("/approval").toString()).execute());

        SmallFullAccountIxbrl smallFullAccountIxbrl = SmallFullIXBRLMapper.INSTANCE.mapSmallFullIXBRLModel(smallFullApiData);

        return smallFullAccountIxbrl;
    }
}
