package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

/**
 * Temporary solution until private-sdk has been completed (SFA-518, SFA-670). When completed, this
 * file will get removed alongside the data package and all references to this file will be replaced
 * with calls to the private-sdk.
 */
@Component
public class AccountsManager {

    /** represents the Authorization header name in the request */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final EnvironmentReader READER = new EnvironmentReaderImpl();

    private final String apiUrl = READER.getMandatoryString("API_URL");
    private final String chsApiKey = READER.getMandatoryString("CHS_API_KEY");

    private static final RestTemplate restTemplate = createRestTemplate();

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    /**
     * Get accounts resource if exists
     *
     * @param link - self link for the accounts resource
     * @return accounts object along with the status or not found status.
     * @throws Exception - throws a generic exception to mimic the private sdk throwing an exception.
     *                     We're not to create a custom exception as it will have to be removed when
     *                     the private sdk  gets implemented - additionally the generic exception is
     *                     sufficient
     */
    public Accounts getAccounts(String link) throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AUTHORIZATION_HEADER, getApiKey());

        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        ResponseEntity<Accounts> accountsResponseEntity = restTemplate.exchange(getRootUri() + link, HttpMethod.GET, requestEntity, Accounts.class);

        if (accountsResponseEntity.getStatusCode() != HttpStatus.OK) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("resource", link);
            logMap.put("status", accountsResponseEntity.getStatusCode());
            LOG.error("Failed to retrieve data from API", logMap);

            throw new Exception("Failed to retrieve data from API");
        }
        return accountsResponseEntity.getBody();
    }

    /**
     * Get abridged accounts resource if exists
     *
     * @param link - self link for the abridged accounts resource
     * @return AbridgedAccountsApi object
     * @throws Exception - throws a generic exception to mimic the private sdk throwing an exception.
     *                     We're not to create a custom exception as it will have to be removed when
     *                     the private sdk  gets implemented - additionally the generic exception is
     *                     sufficient
     */
    public AbridgedAccountsApi getAbridgedAccounts(String link) throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AUTHORIZATION_HEADER, getApiKey());
        requestHeaders.set("Content-Type", "application/Json");

        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        ResponseEntity<AbridgedAccountsApi> abridgedAccountsResponseEntity = restTemplate.exchange(getRootUri() + link, HttpMethod.GET, requestEntity, AbridgedAccountsApi.class);

        if (abridgedAccountsResponseEntity.getStatusCode() != HttpStatus.OK) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("resource", link);
            logMap.put("status", abridgedAccountsResponseEntity.getStatusCode());
            LOG.error("Failed to retrieve data from API", logMap);

            throw new Exception("Failed to retrieve data from API");
        }
        return abridgedAccountsResponseEntity.getBody();
    }

    /**
     * Creates the rest template when class first loads
     *
     * @return Returns a statically created rest template
     */
    private static RestTemplate createRestTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        return new RestTemplate(requestFactory);
    }

    private String getRootUri() {
        return apiUrl;
    }

    private String getApiKey() {
        return chsApiKey;
    }
}
