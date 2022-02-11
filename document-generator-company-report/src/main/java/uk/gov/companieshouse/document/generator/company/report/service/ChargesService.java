package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.charges.request.ChargesGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class ChargesService implements PageRetrieverClient<ChargesApi> {

    private static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    private static final String START_INDEX_KEY = "start_index";
    private static final UriTemplate GET_CHARGES_URI =
        new UriTemplate("/company/{companyNumber}/charges");
    private static final int ITEMS_PER_PAGE_VALUE = 100;

    private final CompanyReportApiClientService companyReportApiClientService;
    private final PageRetrieverService<ChargesApi> pageRetrieverService;

    public ChargesService(CompanyReportApiClientService companyReportApiClientService, PageRetrieverService<ChargesApi> pageRetrieverService) {
        this.companyReportApiClientService = companyReportApiClientService;
        this.pageRetrieverService = pageRetrieverService;
    }

    public ChargesApi getCharges(String companyNumber) throws ServiceException {

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_CHARGES_URI.expand(companyNumber).toString();

        return pageRetrieverService.retrieveAllPages(this, uri, apiClient, ITEMS_PER_PAGE_VALUE);
    }

    @Override
    public int getSize(ChargesApi allPages) {
        return allPages.getItems().size();
    }

    @Override
    public long getTotalCount(ChargesApi allPages) {
        return allPages.getTotalCount();
    }

    @Override
    public void addPage(ChargesApi allPages, ChargesApi anotherPage) {
        allPages.getItems().addAll(anotherPage.getItems());
    }

    @Override
    public ChargesApi retrievePage(String uri, ApiClient apiClient, int startIndex, int itemsPerPage)
            throws ApiErrorResponseException, URIValidationException {

        final ChargesGet chargesGet = apiClient.charges().get(uri);
        chargesGet.addQueryParams(ITEMS_PER_PAGE_KEY, Integer.toString(itemsPerPage));
        chargesGet.addQueryParams(START_INDEX_KEY, Integer.toString(startIndex));

        return chargesGet.execute().getData();
    }
}
