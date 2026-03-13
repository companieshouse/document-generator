package uk.gov.companieshouse.document.generator.company.report.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.psc.request.PscsList;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

/**
 * Handles the logic to be able to accumulate all the instances of the resource available, by paging through the
 * resources until exhausted.
 */
@Service
public class PscsPageRetrieverService {

    private static final String ITEMS_PER_PAGE_KEY = "items_per_page";
    private static final String START_INDEX_KEY = "start_index";
    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);
    private ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Retrieves all the API items available by paging through them.
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
        LOG.debug("retrieve(): Retrieving API items - start...");
        int startIndex = 0;
        final PscsApi api = retrievePage(uri, apiClient, startIndex, itemsPerPage);

        while (api.getItems().size() < api.getTotalResults()) {
            startIndex += itemsPerPage;
            final PscsApi moreResults = retrievePage(uri, apiClient, startIndex, itemsPerPage);
            api.getItems().addAll(moreResults.getItems());
        }

        try {
            LOG.debug("retrieve(): api -> "+mapper.writeValueAsString(api));
        } catch(JsonProcessingException ex){
            LOG.debug("retrieve(): error -> "+ex.getMessage());
        }

        LOG.debug("retrieve(): Retrieving API items - start...");
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
        LOG.debug("retrievePage(): Retrieving API items - start...");
        final PscsList pscsList = apiClient.pscs().list(uri);
        try {
            LOG.debug("retrievePage(): pscList -> "+mapper.writeValueAsString(pscsList));
        } catch(JsonProcessingException ex){
            LOG.debug("retrievePage(): error -> "+ex.getMessage());
        }
        pscsList.addQueryParams(ITEMS_PER_PAGE_KEY, Integer.toString(itemsPerPage));
        pscsList.addQueryParams(START_INDEX_KEY, Integer.toString(startIndex));

        PscsApi pscResponse = pscsList.execute().getData();
        try {
            LOG.debug("retrievePage(): pscResponse -> "+mapper.writeValueAsString(pscResponse));
        } catch(JsonProcessingException ex){
            LOG.debug("retrievePage(): error -> "+ex.getMessage());
        }

        LOG.debug("retrievePage(): Retrieving API items - end...");
        return pscResponse;
    }
}
