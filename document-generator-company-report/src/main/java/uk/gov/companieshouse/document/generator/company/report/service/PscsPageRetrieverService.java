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

    public PscsApi retrieve(final String uri,
                            final ApiClient apiClient,
                            final int itemsPerPage) throws ApiErrorResponseException, URIValidationException {
        final PscsList pscsList = apiClient.pscs().list(uri);
        pscsList.addQueryParams(ITEMS_PER_PAGE_KEY, Integer.toString(itemsPerPage));

        return pscsList.execute().getData();
    }
}
