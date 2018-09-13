package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;

/**
 * Temporary solution until private-sdk has been completed (SFA-518, SFA-670). When completed, this
 * file will get removed alongside the data package and all references to this file will be replaced
 * with calls to the private-sdk.
 */
public class AccountsManager {

    /** represents the Authorization header name in the request */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final EnvironmentReader READER = new EnvironmentReaderImpl();

    private static String API_URL = READER.getMandatoryString("API_URL");
    private static String CHS_API_KEY = READER.getMandatoryString("CHS_API_KEY");

    private static final RestTemplate restTemplate = createRestTemplate();

    /**
     * Get accounts resource if exists
     *
     * @param link - self link for the accounts object
     * @return accounts object along with the status or not found status.
     */
    public static ResponseEntity<Accounts> getAccounts(String link) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AUTHORIZATION_HEADER, getApiKey());

        HttpEntity requestEntity = new HttpEntity(requestHeaders);
        return restTemplate.exchange(getRootUri() + link, HttpMethod.GET, requestEntity, Accounts.class);
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

    private static String getRootUri() {
        return API_URL;
    }

    private static String getApiKey() {
        return CHS_API_KEY;
    }
}
