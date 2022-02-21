package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Service;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

/**
 * Handles the logic to be able to accumulate all the instances of the resource available, by paging through the
 * resources until exhausted.
 *
 * @param <T> the API (type) of the container of the data to be retrieved
 */
@Service
public class PageRetrieverService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    /**
     * Retrieves all the API items available by paging through them.
     *
     * @param client        the {@link PageRetrieverClient} client of this service
     * @param uri           the request URI
     * @param apiClient     the API client
     * @param itemsPerPage  the number of items per page (page size)
     * @return T instance containing all the items
     * @throws ServiceException should the URI provided prove to be invalid or an error occur communicating
     * with remote API
     */
    public T retrieveAllPages(final PageRetrieverClient<T> client,
                              final String uri,
                              final ApiClient apiClient,
                              final int itemsPerPage) throws ServiceException {
        int startIndex = 0;
        T allPages = null;
        try {
            allPages = client.retrievePage(uri, apiClient, startIndex, itemsPerPage);
            while (client.getSize(allPages) < client.getTotalCount(allPages)) {
                    startIndex = getNextPage(client, uri, apiClient, itemsPerPage, startIndex, allPages);
            }
        } catch (ApiErrorResponseException | URIValidationException ex) {
            if (allPages != null && client.getSize(allPages) > 0) {
                LOG.error("Possible data discrepancy while retrieving all items for " + uri +
                        ", total item count = " + client.getTotalCount(allPages) +
                        ", total count of items actually retrieved = " + client.getSize(allPages) +
                        ". Underlying error: " + ex +".");
                return allPages;
            } else {
                throw new ServiceException("Error retrieving items from " + uri, ex);
            }
        }

        return allPages;
    }

    int getNextPage(final PageRetrieverClient<T> client,
                    final String uri,
                    final ApiClient apiClient,
                    final int itemsPerPage,
                    final int startIndex,
                    final T allPages) throws ApiErrorResponseException, URIValidationException {
        final int newStartIndex = startIndex + itemsPerPage;
        final T anotherPage = client.retrievePage(uri, apiClient, newStartIndex, itemsPerPage);
        client.addPage(allPages, anotherPage);
        return newStartIndex;
    }

}
