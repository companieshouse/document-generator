package uk.gov.companieshouse.document.generator.accounts.data.transaction;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

/**
 * TransactionManager is the current temporary internal project solution for communicating with
 * microservices internally. This will be replaced by the private-sdk and its use shall be replaced
 * in the service layer also.
 */
@Component
public class TransactionManager {

    /** represents the Authorization header name in the request */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final EnvironmentReader READER = new EnvironmentReaderImpl();

    private final String apiUrl = READER.getMandatoryString("API_URL");
    private final String chsApiKey = READER.getMandatoryString("CHS_API_KEY");

    /** represents the Spring rest template that is created for cross microservice contact */
    private static final RestTemplate restTemplate = createRestTemplate();

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    /**
     * Get transaction if exists
     *
     * @param id - transaction id
     * @param requestId - the id of the request
     * @return transaction object along with the status or not found status.
     * @throws Exception - throws a generic exception to mimic the private sdk throwing an exception.
     *                     We're not to create a custom exception as it will have to be removed when
     *                     the private sdk  gets implemented - additionally the generic exception is
     *                     sufficient
     */
    public Transaction getTransaction(String id, String requestId) throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AUTHORIZATION_HEADER, getApiKey());

        HttpEntity requestEntity = new HttpEntity(requestHeaders);
        String url = getBaseUrl(id);

        ResponseEntity<Transaction> transactionResponseEntity = restTemplate.exchange(getRootUri() + url, HttpMethod.GET, requestEntity, Transaction.class);
        if (transactionResponseEntity.getStatusCode() != HttpStatus.OK) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("transaction_id", id);
            logMap.put("uri_path", url);
            logMap.put("status", transactionResponseEntity.getStatusCode());
            LOG.infoContext(requestId,"Failed to retrieve data from API", logMap);

            throw new Exception("Failed to retrieve data from API");
        }
        return transactionResponseEntity.getBody();
    }

    /**
     * Creates the rest template when class is initialised
     *
     * @return a statically created rest template
     */
    private static RestTemplate createRestTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        return new RestTemplate(requestFactory);
    }

    /**
     * Builds the private transaction link with the transaction id
     *
     * @param id id of the transaction
     * @return the private endpoint transaction url with the transaction id
     */
    private String getBaseUrl(String id) {
        return new StringBuilder("/private").append(id).toString();
    }

    private String getRootUri() {
        return apiUrl;
    }

    private String getApiKey() {
        return chsApiKey;
    }
}
