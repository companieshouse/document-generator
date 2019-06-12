package uk.gov.companieshouse.document.generator.prosecution.service;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.http.ApiKeyHttpClient;
import uk.gov.companieshouse.api.http.HttpClient;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.environment.impl.EnvironmentReaderImpl;

@Component
public class ApiClientService {

    private static final EnvironmentReader READER = new EnvironmentReaderImpl();

    private static final String API_KEY = READER.getMandatoryString("CHS_API_KEY");
    private static final String API_URL = READER.getMandatoryString("API_URL");
    private static final String X_REQUEST_ID_HEADER = "x-request-id";

    public InternalApiClient getApiClient() {
        HttpClient httpClient = new ApiKeyHttpClient(API_KEY);
        setRequestId(httpClient);
        InternalApiClient apiClient = new InternalApiClient(httpClient);
        apiClient.setBasePath(API_URL);
        return apiClient;
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

        if (requestId == null) {
            requestId = request.getHeader(X_REQUEST_ID_HEADER);
        }

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
