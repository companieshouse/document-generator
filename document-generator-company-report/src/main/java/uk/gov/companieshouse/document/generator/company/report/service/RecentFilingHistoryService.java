package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.filinghistory.request.FilingHistoryList;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class RecentFilingHistoryService {

    @Autowired
    private CompanyReportApiClientService companyReportApiClientService;

    private static final UriTemplate GET_RECENT_FILING_HISTORY_URI =
        new UriTemplate("/company/{company_number}/filing-history");

    public FilingHistoryApi getFilingHistory(String companyNumber) throws ServiceException, ApiErrorResponseException, URIValidationException {

        FilingHistoryApi filingHistoryApi;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_RECENT_FILING_HISTORY_URI.expand(companyNumber).toString();

        try {
            FilingHistoryList filingHistoryList = apiClient.filingHistory().list(uri);
            filingHistoryList.addQueryParams("items_per_page", "100");

            filingHistoryApi = filingHistoryList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving recent filing history", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for recent filing history resource", e);
        }
        return filingHistoryApi;
    }
}
