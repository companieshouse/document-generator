package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.sdk.manager.ApiSdkManager;

/**
 * Service to get the internal API client.
 * Use "Report" as a prefix since this module is imported into other projects that already have an ApiClientService.
 */
@Component
public class ReportApiClientService {

    public InternalApiClient getInternalApiClient() {
        return ApiSdkManager.getPrivateSDK();
    }
}