package uk.gov.companieshouse.document.generator.prosecution.tmpclient;

import static uk.gov.companieshouse.document.generator.prosecution.ProsecutionDocumentInfoService.MODULE_NAME_SPACE;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.gov.companieshouse.document.generator.prosecution.ConfigKeys;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@Component
public class ProsecutionClient {
    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String LOGGING_RESOURCE_KEY = "resource";

    private final String chsApiKey;
    private final String prosecutionServiceUri;
    public ProsecutionClient(EnvironmentReader environmentReader) {
        chsApiKey = environmentReader.getMandatoryString(ConfigKeys.CHS_API_KEY);
        prosecutionServiceUri =
                environmentReader.getMandatoryString(ConfigKeys.PROSECUTION_SERVICE_URI);
    }

    /**
     * Temporary utility method to get ProsecutionCase, needed until we have a Prosecution SDK.
     * 
     * @param prosecutionCaseUri The URI of the case in the Prosecution Service.
     * @param requestId The ID of the request that this method is serving.
     * @return The prosecution Case.
     * @throws SdkException If the case could not be retrieved.
     */
    public ProsecutionCase getProsecutionCase(String prosecutionCaseUri, String requestId)
            throws SdkException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AUTHORIZATION_HEADER, chsApiKey);

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpEntity<ProsecutionCase> requestEntity = new HttpEntity<>(requestHeaders);

        try {
            ResponseEntity<ProsecutionCase> prosecutionCaseResponse =
                    restTemplate.exchange(prosecutionServiceUri + prosecutionCaseUri,
                            HttpMethod.GET, requestEntity, ProsecutionCase.class);
            return prosecutionCaseResponse.getBody();
        } catch (RuntimeException ex) {
            // It is true that this could be more sophisticated about how it catches exceptions,
            // but we will get rid of this whole method when we move to a Prosecution SDK, so
            // we do not need to be too sophisticated for short-term code.
            Map<String, Object> logMap = new HashMap<>();
            logMap.put(LOGGING_RESOURCE_KEY, prosecutionCaseUri);
            LOG.errorContext(requestId,
                    "Error getting prosecution case from: " + prosecutionCaseUri, ex, logMap);
            throw new SdkException("Could not get prosecution case: " + prosecutionCaseUri, ex);
        }
    }
}
