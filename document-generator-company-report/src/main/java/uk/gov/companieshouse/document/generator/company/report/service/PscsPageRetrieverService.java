package uk.gov.companieshouse.document.generator.company.report.service;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.psc.request.PscsList;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

/**
 * Handles the logic to be able to accumulate all the instances of the resource available, by paging through the
 * resources until exhausted.
 */
@Service
public class PscsPageRetrieverService {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    private static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    private static final String START_INDEX_KEY = "start_index";

    /**
     * Retrieves all the API items available by paging through them.
     * @param uri the request URI
     * @param apiClient the API client
     * @param itemsPerPage the number of items per page (page size)
     * @return {@link PscsApi} instance containing all of the items
     * @throws ApiErrorResponseException possibly arising in communication with remote API
     * @throws URIValidationException should the URI provided prove to be invalid
     */
    public PscsApi retrieve(final String uri, final ApiClient apiClient, final int itemsPerPage)
            throws ApiErrorResponseException, URIValidationException {
        LOG.info("retrieve(uri=%s, items_per_page=%d) method called.".formatted(uri, itemsPerPage));

        int startIndex = 0;

        final PscsApi api = retrievePage(uri, apiClient, startIndex, itemsPerPage);

        long itemsRemaining = api.getTotalResults() - api.getItems().size();
        LOG.info("Starting processing... %d item(s) remaining.".formatted(itemsRemaining));

        while (api.getItems().size() < api.getTotalResults()) {
            LOG.info("Items retrieved: %d -> (Start Index: %d, Total Results: %d)".formatted(
                    api.getItems().size(), startIndex, api.getTotalResults()));

            startIndex += itemsPerPage;

            PscsApi moreResults = retrievePage(uri, apiClient, startIndex, itemsPerPage);
            api.getItems().addAll(moreResults.getItems());
        }

        LOG.info("Finishing processing: %d item(s) retrieved.".formatted(api.getItems().size()));

        return api;
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
    private PscsApi retrievePage(final String uri, final ApiClient apiClient, final int startIndex, final int itemsPerPage)
            throws ApiErrorResponseException, URIValidationException {

        final PscsList pscsList = apiClient.pscs().list(uri);
        pscsList.addQueryParams(ITEMS_PER_PAGE_KEY, Integer.toString(itemsPerPage));
        pscsList.addQueryParams(START_INDEX_KEY, Integer.toString(startIndex));

        return pscsList.execute().getData();
    }
}
