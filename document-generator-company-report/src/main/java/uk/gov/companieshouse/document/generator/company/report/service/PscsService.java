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

        try {
            return pageRetrieverService.retrieve(this, uri, apiClient, ITEMS_PER_PAGE_VALUE);
        } catch (ApiErrorResponseException e) {
            throw new ServiceException("Error retrieving pscs", e);
        } catch (URIValidationException e) {
            throw new ServiceException("Invalid URI for pscs resource", e);
        }
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

    /**
     * Retrieves a single page of API items.
     * @param uri the request URI
     * @param apiClient the API client
     * @param startIndex the start of the next page (offset in items)
     * @param itemsPerPage the number of items per page (page size)
     * @return {@link PscsApi} instance containing the items on the page
     * @throws ApiErrorResponseException possibly arising in communication with remote API
     * @throws URIValidationException should the URI provided prove to be invalid
     */
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
