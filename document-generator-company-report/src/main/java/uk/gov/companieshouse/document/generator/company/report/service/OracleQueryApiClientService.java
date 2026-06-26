package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.document.generator.common.OracleQueryApiProperties;
import uk.gov.companieshouse.sdk.manager.ApiSdkManager;

/**
 * Service to get the internal API client and configure it to use the oracleQueryApiUrl for paths
 * that have a different base url than that of the Oracle Query API
 */
@Component
public class OracleQueryApiClientService {

    private final String oracleQueryApiUrl;

    public OracleQueryApiClientService(OracleQueryApiProperties oracleQueryApiProperties) {
        this.oracleQueryApiUrl = oracleQueryApiProperties.getUrl();
    }

    public InternalApiClient getInternalApiClient() {
        InternalApiClient internalApiClient = ApiSdkManager.getInternalSDK();
        internalApiClient.setBasePath(oracleQueryApiUrl);
        return internalApiClient;
    }
}
