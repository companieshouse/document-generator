package uk.gov.companieshouse.document.generator.company.report.service;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;

/**
 * Defines the contract that a client of the {@link PageRetrieverService} must implement to use it.
 *
 * @param <T> the API (type) of the container of the data to be retrieved
 */
public interface PageRetrieverClient<T> {

    /**
     * Gets the size of the pages so far accumulated in the looping process.
     * @param allPages the container of pages so far accumulated
     * @return the size of the pages, in terms of total items so far held
     */
    int getSize(T allPages);

    /**
     * Gets the total count of items held by the remote service.
     * @param allPages the container of pages so far accumulated
     * @return the total count of items the {@link PageRetrieverService} should return once it has collected all the
     * items held by the remote service
     */
    long getTotalCount(T allPages);

    /**
     * Adds the items held within anotherPage to those already held within allPages.
     * @param allPages the container of pages so far accumulated
     * @param anotherPage the container of items from another page
     */
    void addPage(T allPages, T anotherPage);

    /**
     * Retrieves a single page of API items.
     * @param uri the request URI
     * @param apiClient the API client
     * @param startIndex the start of the next page (offset in items)
     * @param itemsPerPage the number of items per page (page size)
     * @return T instance containing the items on the page
     * @throws ApiErrorResponseException possibly arising in communication with remote API
     * @throws URIValidationException should the URI provided prove to be invalid
     */
    T retrievePage(final String uri,
                   final ApiClient apiClient,
                   final int startIndex,
                   final int itemsPerPage) throws ApiErrorResponseException, URIValidationException;
}
