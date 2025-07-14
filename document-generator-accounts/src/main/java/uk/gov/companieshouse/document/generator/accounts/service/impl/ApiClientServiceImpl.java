package uk.gov.companieshouse.document.generator.accounts.service.impl;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.http.HttpClient;
import uk.gov.companieshouse.document.generator.accounts.service.ApiClientService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import uk.gov.companieshouse.sdk.manager.ApiSdkManager;

@Service
public class ApiClientServiceImpl implements ApiClientService {

    private static final String X_REQUEST_ID_HEADER = "x-request-id";

    @Override
    public ApiClient getApiClient() {

        return ApiSdkManager.getSDK();
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
