package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.officers.request.OfficersList;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class OfficerService implements PageRetrieverClient<OfficersApi> {

    private static final UriTemplate GET_OFFICERS_URI =
            new UriTemplate("/company/{companyNumber}/officers");
    private static final int ITEMS_PER_PAGE_VALUE = 100;

    public static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    public static final String START_INDEX_KEY = "start_index";

    private final CompanyReportApiClientService companyReportApiClientService;
    private final PageRetrieverService<OfficersApi> pageRetrieverService;

    public OfficerService(CompanyReportApiClientService companyReportApiClientService, PageRetrieverService<OfficersApi> pageRetrieverService) {
        this.companyReportApiClientService = companyReportApiClientService;
        this.pageRetrieverService = pageRetrieverService;
    }

    public OfficersApi getOfficers(String companyNumber) throws ServiceException {

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_OFFICERS_URI.expand(companyNumber).toString();

        return pageRetrieverService.retrieveAllPages(this, uri, apiClient, ITEMS_PER_PAGE_VALUE);

    }

    @Override
    public int getSize(OfficersApi allPages) {
        return allPages.getItems().size();
    }

    @Override
    public long getTotalCount(OfficersApi allPages) {
        return allPages.getTotalResults();
    }

    @Override
    public void addPage(OfficersApi allPages, OfficersApi anotherPage) {
        allPages.getItems().addAll(anotherPage.getItems());
    }

    @Override
    public OfficersApi retrievePage(String uri, ApiClient apiClient, int startIndex, int itemsPerPage)
            throws ApiErrorResponseException, URIValidationException {
        final OfficersList officersList = apiClient.officers().list(uri);
        officersList.addQueryParams(ITEMS_PER_PAGE_KEY, Integer.toString(itemsPerPage));
        officersList.addQueryParams(START_INDEX_KEY, Integer.toString(startIndex));

        return officersList.execute().getData();
    }
}
