package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;

/**
 * Handles the logic to be able to accumulate all the instances of the resource available, by paging through the
 * resources until exhausted.
 *
 * @param <T> the API (type) of the container of the data to be retrieved
 */
@Service
public class PageRetrieverService<T> {

    /**
     * Retrieves all the API items available by paging through them.
     *
     * @param client       the {@link PageRetrieverClient} client of this service
     * @param uri          the request URI
     * @param apiClient    the API client
     * @param itemsPerPage the number of items per page (page size)
     * @return T instance containing all the items
     * @throws ApiErrorResponseException possibly arising in communication with remote API
     * @throws URIValidationException    should the URI provided prove to be invalid
     */
    public T retrieveAllPages(final PageRetrieverClient<T> client,
                              final String uri,
                              final ApiClient apiClient,
                              final int itemsPerPage) throws ApiErrorResponseException, URIValidationException {
        int startIndex = 0;
        final T allPages = client.retrievePage(uri, apiClient, startIndex, itemsPerPage);

        while (client.getSize(allPages) < client.getTotalCount(allPages)) {
            startIndex += itemsPerPage;
            final T anotherPage = client.retrievePage(uri, apiClient, startIndex, itemsPerPage);
            client.addPage(allPages, anotherPage);
        }

        return allPages;
    }

}
