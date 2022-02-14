package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.filinghistory.request.FilingHistoryList;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class FilingHistoryService implements PageRetrieverClient<FilingHistoryApi> {

    private final CompanyReportApiClientService companyReportApiClientService;
    private final PageRetrieverService<FilingHistoryApi> pageRetrieverService;

    private static final UriTemplate GET_FILING_HISTORY_URI =
            new UriTemplate("/company/{company_number}/filing-history");
    private static final int ITEMS_PER_PAGE_VALUE = 100;

    public static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    public static final String START_INDEX_KEY = "start_index";

    public FilingHistoryService(CompanyReportApiClientService companyReportApiClientService,
                                PageRetrieverService<FilingHistoryApi> pageRetrieverService) {
        this.companyReportApiClientService = companyReportApiClientService;
        this.pageRetrieverService = pageRetrieverService;
    }

    public FilingHistoryApi getFilingHistory(String companyNumber) throws ServiceException {

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_FILING_HISTORY_URI.expand(companyNumber).toString();

        return pageRetrieverService.retrieveAllPages(this, uri, apiClient, ITEMS_PER_PAGE_VALUE);

    }

    @Override
    public int getSize(FilingHistoryApi allPages) {
        return allPages.getItems().size();
    }

    @Override
    public long getTotalCount(FilingHistoryApi allPages) {
        return allPages.getTotalCount();
    }

    @Override
    public void addPage(FilingHistoryApi allPages, FilingHistoryApi anotherPage) {
        allPages.getItems().addAll(anotherPage.getItems());
    }

    @Override
    public FilingHistoryApi retrievePage(String uri, ApiClient apiClient, int startIndex, int itemsPerPage)
            throws ApiErrorResponseException, URIValidationException {
        final FilingHistoryList filingHistoryList = apiClient.filingHistory().list(uri);
        filingHistoryList.addQueryParams(ITEMS_PER_PAGE_KEY, Integer.toString(itemsPerPage));
        filingHistoryList.addQueryParams(START_INDEX_KEY, Integer.toString(startIndex));

        return filingHistoryList.execute().getData();
    }
}
