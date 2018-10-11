package uk.gov.companieshouse.document.generator.accounts.data.accounts;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.http.ApiKeyHttpClient;
import uk.gov.companieshouse.api.http.HttpClient;
import uk.gov.companieshouse.api.model.accounts.Accounts;
import uk.gov.companieshouse.api.model.accounts.abridged.AbridgedAccountsApi;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static uk.gov.companieshouse.document.generator.accounts.AccountsDocumentInfoServiceImpl.MODULE_NAME_SPACE;

/**
 * Temporary solution until private-sdk has been completed (SFA-518, SFA-670). When completed, this
 * file will get removed alongside the data package and all references to this file will be replaced
 * with calls to the private-sdk.
 */
@Component
public class AccountsManager {

    private static final EnvironmentReader READER = new EnvironmentReaderImpl();

    private final String apiUrl = READER.getMandatoryString("API_URL");
    private final String chsApiKey = READER.getMandatoryString("CHS_API_KEY");
    private static final String X_REQUEST_ID_HEADER = "x-request-id";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    /**
     * Get accounts resource if exists
     *
     * @param link - self link for the accounts resource
     * @return accounts object along with the status or not found status.
     * @throws ApiErrorResponseException
     * @throws URIValidationException
     */
    public Accounts getAccounts(String link) throws ApiErrorResponseException, URIValidationException {

        HttpClient httpClient = new ApiKeyHttpClient(chsApiKey);

        setRequestId(httpClient);

        ApiClient apiClient = new ApiClient(httpClient);
        apiClient.setBasePath(apiUrl);

        return apiClient.accounts().get(link).execute();
    }

    /**
     *Get abridged accounts resource if exists
     *
     * @param link - self link for the abridged accounts resource
     * @return AbridgedAccountsApi object
     * @throws ApiErrorResponseException
     * @throws URIValidationException
     */
    public AbridgedAccountsApi getAbridgedAccounts(String link) throws ApiErrorResponseException, URIValidationException {

        HttpClient httpClient = new ApiKeyHttpClient(chsApiKey);

        setRequestId(httpClient);

        ApiClient apiClient = new ApiClient(httpClient);
        apiClient.setBasePath(apiUrl);

        return apiClient.abridgedAccounts().get(link).execute();
    }

    /**
     *  Set request ID using httpclient
     * @param httpClient
     */
    private static void setRequestId(HttpClient httpClient) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        String requestId = (String) request.getAttribute(X_REQUEST_ID_HEADER);

        if (requestId == null)
            requestId = request.getHeader(X_REQUEST_ID_HEADER);

        if (requestId == null || requestId.isEmpty()) {
            requestId = generateRequestId();
            request.setAttribute(X_REQUEST_ID_HEADER, requestId);
        }

        httpClient.setRequestId(requestId);
    }

    /**
     * Generate a universally unique identifier
     * @return
     */
    private static String generateRequestId() {
        return UUID.randomUUID().toString().substring(0,20);
    }
}
