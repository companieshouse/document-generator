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

    private CompanyReportApiClientService companyReportApiClientService;

    private static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    private static final String ITEMS_PER_PAGE_VALUE = "100";
    private static final String START_INDEX_KEY = "start_index";

    @Autowired
    public RecentFilingHistoryService(CompanyReportApiClientService companyReportApiClientService) {
        this.companyReportApiClientService = companyReportApiClientService;
    }

    private static final UriTemplate GET_RECENT_FILING_HISTORY_URI =
        new UriTemplate("/company/{company_number}/filing-history");

    public FilingHistoryApi getFilingHistory(String companyNumber) throws ServiceException {

        FilingHistoryApi filingHistoryApi = null;

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        int startIndex = 0;
        int itemsPerPage = 100;

        filingHistoryApi = retrieveFilingHistory(companyNumber, apiClient, startIndex, itemsPerPage);

        while (filingHistoryApi.getItems().size() < filingHistoryApi.getTotalCount()) {
            startIndex += itemsPerPage;
            FilingHistoryApi moreResults = retrieveFilingHistory(companyNumber, apiClient, startIndex, itemsPerPage);
            filingHistoryApi.getItems().addAll(moreResults.getItems());
        }

        return filingHistoryApi;
    }

    private FilingHistoryApi retrieveFilingHistory(String companyNumber, ApiClient apiClient, Integer startIndex, Integer itemsPerPage)
        throws ServiceException {

        String uri = GET_RECENT_FILING_HISTORY_URI.expand(companyNumber).toString();

        FilingHistoryApi filingHistoryApi;

        try {
            FilingHistoryList filingHistoryList = apiClient.filingHistory().list(uri);
            filingHistoryList.addQueryParams(ITEMS_PER_PAGE_KEY, ITEMS_PER_PAGE_VALUE);
            filingHistoryList.addQueryParams(START_INDEX_KEY, startIndex.toString());

            filingHistoryApi = filingHistoryList.execute().getData();
        } catch (ApiErrorResponseException e) {

            throw new ServiceException("Error retrieving filing history items", e);
        } catch (URIValidationException e) {

            throw new ServiceException("Invalid URI for filing history resource", e);
        }
        return filingHistoryApi;
    }
}
