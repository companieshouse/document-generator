package uk.gov.companieshouse.document.generator.company.report.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.psc.PscsResourceHandler;
import uk.gov.companieshouse.api.handler.psc.request.PscsList;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.psc.PscApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;

@ExtendWith(MockitoExtension.class)
class PscsPageRetrieverServiceTest {

    private static final String PSCS_URI = "/company/00000000/persons-with-significant-control";
    private static final int ITEMS_PER_PAGE = 2;
    private static final long VALID_TOTAL_RESULTS_METRIC = 4;
    private static final long INVALID_TOTAL_RESULTS_METRIC = 10;

    @InjectMocks
    private PscsPageRetrieverService pageRetrieverService;

    @Mock
    private ApiClient apiClient;

    @Mock
    private PscsResourceHandler pscsResourceHandler;

    @Mock
    private PscsList pscsList;

    @Mock
    private ApiResponse<PscsApi> responseWithData;

    @Test
    @DisplayName("retrieve() gets PscsApi instance containing all items successfully")
    void retrieveRetrievesSuccessfully() throws ApiErrorResponseException, URIValidationException {

        // Given
        when(apiClient.pscs()).thenReturn(pscsResourceHandler);
        when(pscsResourceHandler.list(PSCS_URI)).thenReturn(pscsList);

        when(pscsList.execute()).thenReturn(responseWithData);
        when(responseWithData.getData()).thenReturn(createPage(VALID_TOTAL_RESULTS_METRIC));

        // When
        final PscsApi items = pageRetrieverService.retrieve(PSCS_URI, apiClient, ITEMS_PER_PAGE);

        // Then
        assertNotNull(items);
        assertThat(items.getItems().size(), is(4));
        verify(responseWithData, times(2)).getData();
    }

    @Test
    @DisplayName("retrieve() breaks from while loop if no more PSC data to be processed")
    void assertRetieveBreaksMakeLoopIfNoMorePSCDataToBeProcessed() throws ApiErrorResponseException, URIValidationException {

        // Given
        when(apiClient.pscs()).thenReturn(pscsResourceHandler);
        when(pscsResourceHandler.list(PSCS_URI)).thenReturn(pscsList);

        when(pscsList.execute()).thenReturn(responseWithData);
        when(responseWithData.getData()).thenReturn(createPage(INVALID_TOTAL_RESULTS_METRIC));

        // When
        final PscsApi items = pageRetrieverService.retrieve(PSCS_URI, apiClient, ITEMS_PER_PAGE);

        // Then
        assertNotNull(items);
        assertThat(items.getItems().size(), is(0));
        verify(responseWithData, times(2)).getData();
    }

    private PscsApi createPage(long totalResultsMetric) {
        final PscsApi page = new PscsApi();
        final List<PscApi> items = new ArrayList<>();
        // no items in list for assertRetieveBreaksMakeLoopIfNoMorePSCDataToBeProcessed()
        if (totalResultsMetric == VALID_TOTAL_RESULTS_METRIC) {
            items.add(new PscApi());
            items.add(new PscApi());
        }
        page.setItems(items);
        page.setTotalResults(totalResultsMetric);
        return page;
    }
}