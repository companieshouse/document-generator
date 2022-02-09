package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.psc.request.PscsList;
import uk.gov.companieshouse.api.model.psc.PscsApi;

/**
 * Handles the logic to be able to accumulate all the instances of the resource available, by paging through the
 * resources until exhausted.
 */
@Service
public class PscsPageRetrieverService {

    private static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    private static final String START_INDEX_KEY = "start_index";

    /**
     * Retrieves all of the API items available by paging through them.
     * @param uri the request URI
     * @param apiClient the API client
     * @param itemsPerPage the number of items per page (page size)
     * @return {@link PscsApi} instance containing all of the items
     * @throws ApiErrorResponseException possibly arising in communication with remote API
     * @throws URIValidationException should the URI provided prove to be invalid
     */
    public PscsApi retrieve(final String uri,
                            final ApiClient apiClient,
                            final int itemsPerPage) throws ApiErrorResponseException, URIValidationException {
        int startIndex = 0;
        final PscsApi api = retrievePage(uri, apiClient, startIndex, itemsPerPage);

        while (api.getItems().size() < api.getTotalResults()) {
            startIndex += itemsPerPage;
            final PscsApi moreResults = retrievePage(uri, apiClient, startIndex, itemsPerPage);
            api.getItems().addAll(moreResults.getItems());
        }

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
    private PscsApi retrievePage(final String uri,
                                 final ApiClient apiClient,
                                 final int startIndex,
                                 final int itemsPerPage) throws ApiErrorResponseException, URIValidationException {

        final PscsList pscsList = apiClient.pscs().list(uri);
        pscsList.addQueryParams(ITEMS_PER_PAGE_KEY, Integer.toString(itemsPerPage));
        pscsList.addQueryParams(START_INDEX_KEY, Integer.toString(startIndex));

        return pscsList.execute().getData();
    }
}
