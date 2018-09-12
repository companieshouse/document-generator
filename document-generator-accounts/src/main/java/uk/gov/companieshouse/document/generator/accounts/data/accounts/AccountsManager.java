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

public class AccountsManager {

    /** represents the Authorization header name in the request */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final EnvironmentReader READER = new EnvironmentReaderImpl();

    private static String API_URL = READER.getMandatoryString("API_URL");
    private static String CHS_API_KEY = READER.getMandatoryString("CHS_API_KEY");

    private static final RestTemplate restTemplate = createRestTemplate();

    /**
     * Try get accounts resource if exists
     *
     * @param link - self link for the accounts object
     * @return accounts object along with the status or not found status.
     */
    public static ResponseEntity<Accounts> getAccounts(String link) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AUTHORIZATION_HEADER, getApiKey());

        HttpEntity requestEntity = new HttpEntity(requestHeaders);
        return getAccounts(link, requestEntity);
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



    /**
     * GET the accounts resource
     *
     * @param url - url to send the get request
     * @param requestEntity - the request entity object
     */
    private static ResponseEntity<Accounts> getAccounts(String url, HttpEntity requestEntity) {
        return restTemplate.exchange(getRootUri() + url, HttpMethod.GET, requestEntity, Accounts.class);
    }

    /**
     * Get the root uri from the properties file
     *
     * @return Get the root uri if set, otherwise throw an exception
     */
    private static String getRootUri() {
        return API_URL;
    }

    /**
     * Gets the api key environment variable
     *
     * @return Returns the api key if set
     */
    private static String getApiKey() {
        return CHS_API_KEY;
    }
}
