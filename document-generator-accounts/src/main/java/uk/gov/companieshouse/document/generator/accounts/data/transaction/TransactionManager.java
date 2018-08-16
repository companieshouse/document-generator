package uk.gov.companieshouse.document.generator.accounts.data.transaction;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import uk.gov.companieshouse.api.model.transaction.Transaction;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;

/**
 * TransactionManager is the current temporary internal project solution for communicating with
 * microservices internally. This will be replaced by the private-sdk and its use shall be replaced
 * in the service layer also.
 */
public class TransactionManager {

    /** represents the Authorization header name in the request */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /** represents the Spring rest template that is created for cross microservice contact */
    private static final RestTemplate restTemplate = createRestTemplate();

    /**
     * Get transaction if exists
     *
     * @param id - transaction id
     * @return transaction object along with the status or not found status.
     */
    public static ResponseEntity<Transaction> getTransaction(String id) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AUTHORIZATION_HEADER, getApiKey());

        HttpEntity requestEntity = new HttpEntity(requestHeaders);
        String url = getBaseUrl(id);

        return getTransaction(url, requestEntity);
    }

    /**
     * Creates the rest template when class is initialised
     *
     * @return Returns a statically created rest template
     */
    private static RestTemplate createRestTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        return new RestTemplate(requestFactory);
    }

    /**
     * GET the transaction resource
     *
     * @param url - url to send the get request
     * @param requestEntity - the request entity object
     */
    private static ResponseEntity<Transaction> getTransaction(String url, HttpEntity requestEntity) {
        return restTemplate.exchange(getRootUri() + url, HttpMethod.GET, requestEntity, Transaction.class);
    }

    /**
     * Get the API_URL environment variable
     *
     * @return Get the API_URL if set, otherwise throw an exception
     */
    private static String getRootUri() {
        return new EnvironmentReaderImpl().getMandatoryString("API_URL");
    }

    /**
     * Gets the api key environment variable
     *
     * @return Returns the api key if set, otherwise throw an exception
     */
    private static String getApiKey() {
        return new EnvironmentReaderImpl().getMandatoryString("CHS_API_KEY");
    }

    /**
     * Builds the private transaction link with the transaction id
     *
     * @return Returns the private endpoint transaction url with the transaction id
     */
    private static String getBaseUrl(String id) {
        return "/private/transactions/" + id;
    }
}
