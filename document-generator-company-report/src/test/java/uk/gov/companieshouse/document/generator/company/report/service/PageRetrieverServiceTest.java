package uk.gov.companieshouse.document.generator.company.report.service;

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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageRetrieverServiceTest {

    private static final String PSCS_URI = "/company/00000000/persons-with-significant-control";

    // Loop over 2 pages of 2 + 1 items = total 3 items.
    private static final int INITIAL_SIZE = 0;
    private static final int ITEMS_PER_PAGE = 2;
    private static final int TOTAL_ITEM_COUNT = 3;

    @InjectMocks
    private PageRetrieverService<PscsApi> pageRetrieverService;

    @Mock
    private PageRetrieverClient<PscsApi> pageRetrieverClient;

    @Mock
    private ApiClient apiClient;

    @Test
    @DisplayName("retrieveAllPages() gets PscsApi instance containing all items successfully")
    void retrieveAllPagesRetrievesSuccessfully() throws ApiErrorResponseException, URIValidationException {

        // Given
        final PscsApi allPages = new PscsApi();
        when(pageRetrieverClient.retrievePage(eq(PSCS_URI), eq(apiClient), anyInt(), eq(ITEMS_PER_PAGE)))
                .thenReturn(allPages, new PscsApi());
        when(pageRetrieverClient.getSize(allPages)).thenReturn(INITIAL_SIZE, ITEMS_PER_PAGE, TOTAL_ITEM_COUNT);
        when(pageRetrieverClient.getTotalCount(allPages)).thenReturn((long) TOTAL_ITEM_COUNT);

        // When
        final PscsApi items =
                pageRetrieverService.retrieveAllPages(pageRetrieverClient, PSCS_URI, apiClient, ITEMS_PER_PAGE);

        // Then
        assertNotNull(items);
        assertThat(items, is(allPages));
        verify(pageRetrieverClient, times(3)).retrievePage(eq(PSCS_URI), eq(apiClient), anyInt(), eq(ITEMS_PER_PAGE));
        verify(pageRetrieverClient, times(3)).getSize(allPages);
        verify(pageRetrieverClient, times(3)).getTotalCount(allPages);
    }

}