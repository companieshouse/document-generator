package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.sdk.manager.ApiSdkManager;

/**
 * Service to get the internal API client.
 * Need to set the Base Path to use the oracleQueryApiUrl.
 * Use "Report" as a prefix since this module is imported into other projects that already have an ApiClientService.
 */
@Component
public class ReportApiClientService {

    private final String oracleQueryApiUrl;

    public ReportApiClientService(@Value("${ORACLE_QUERY_API_URL}") String oracleQueryApiUrl) {
        this.oracleQueryApiUrl = oracleQueryApiUrl;
    }

    public InternalApiClient getInternalApiClient() {
        InternalApiClient internalApiClient = ApiSdkManager.getInternalSDK();
        internalApiClient.setBasePath(oracleQueryApiUrl);
        return internalApiClient;
    }
}