package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.psc.request.PscsList;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@Service
public class PscsService implements PageRetrieverClient<PscsApi> {

    private static final UriTemplate GET_PSCS_URI =
            new UriTemplate("/company/{companyNumber}/persons-with-significant-control");
    private static final int ITEMS_PER_PAGE_VALUE = 100;

    private static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    private static final String START_INDEX_KEY = "start_index";

    private final CompanyReportApiClientService companyReportApiClientService;
    private final PageRetrieverService<PscsApi> pageRetrieverService;

    public PscsService(CompanyReportApiClientService companyReportApiClientService,
                       PageRetrieverService<PscsApi> pageRetrieverService) {
        this.companyReportApiClientService = companyReportApiClientService;
        this.pageRetrieverService = pageRetrieverService;
    }

    public PscsApi getPscs(String companyNumber) throws ServiceException {

        ApiClient apiClient = companyReportApiClientService.getApiClient();

        String uri = GET_PSCS_URI.expand(companyNumber).toString();

        return pageRetrieverService.retrieveAllPages(this, uri, apiClient, ITEMS_PER_PAGE_VALUE, companyNumber);
    }

    @Override
    public int getSize(PscsApi api) {
        return api.getItems().size();
    }

    @Override
    public long getTotalCount(PscsApi api) {
        return api.getTotalResults();
    }

    @Override
    public void addPage(PscsApi api, PscsApi moreResults) {
        api.getItems().addAll(moreResults.getItems());
    }

    @Override
    public PscsApi retrievePage(final String uri,
                                final ApiClient apiClient,
                                final int startIndex,
                                final int itemsPerPage) throws ApiErrorResponseException, URIValidationException {

        final PscsList pscsList = apiClient.pscs().list(uri);
        pscsList.addQueryParams(ITEMS_PER_PAGE_KEY, Integer.toString(itemsPerPage));
        pscsList.addQueryParams(START_INDEX_KEY, Integer.toString(startIndex));

        return pscsList.execute().getData();
    }
}
