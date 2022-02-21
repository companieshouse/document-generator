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
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class PageRetrieverServiceTest {

    private static final String PSCS_URI = "/company/00000000/persons-with-significant-control";

    private static final int INITIAL_SIZE = 0;
    private static final int ITEMS_PER_PAGE = 2;
    private static final int TOTAL_ITEM_COUNT = 3;

    @InjectMocks
    private PageRetrieverService<PscsApi> pageRetrieverService;

    @Mock
    private PageRetrieverClient<PscsApi> pageRetrieverClient;

    @Mock
    private ApiClient apiClient;

    @Mock
    private ApiErrorResponseException apiErrorResponseException;

    @Mock
    private URIValidationException uriValidationException;

    @Test
    @DisplayName("retrieveAllPages() gets PscsApi instance containing all items successfully")
    void retrieveAllPagesRetrievesSuccessfully() throws Exception {

        // Given
        final PscsApi allPages = new PscsApi();
        when(pageRetrieverClient.retrievePage(eq(PSCS_URI), eq(apiClient), anyInt(), eq(ITEMS_PER_PAGE)))
                .thenReturn(allPages, new PscsApi());
        // Loop over 2 pages of 2 + 1 items = total 3 items.
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

    @Test
    @DisplayName("retrieveAllPages() propagates ApiErrorResponseException as a ServiceException when no items retrieved")
    void retrieveAllPagesPropagatesApiErrorResponseException() throws Exception {

        // Given
        when(pageRetrieverClient.retrievePage(eq(PSCS_URI), eq(apiClient), anyInt(), eq(ITEMS_PER_PAGE)))
                .thenThrow(apiErrorResponseException);

        // When and then
        final ServiceException ex = assertThrows(ServiceException.class, () ->
                pageRetrieverService.retrieveAllPages(pageRetrieverClient, PSCS_URI, apiClient, ITEMS_PER_PAGE));
       assertThat(ex.getMessage(), is("Error retrieving items from /company/00000000/persons-with-significant-control"));

    }

    @Test
    @DisplayName("retrieveAllPages() propagates URIValidationException as a ServiceException when no items retrieved")
    void retrieveAllPagesPropagatesURIValidationException() throws Exception {

        // Given
        when(pageRetrieverClient.retrievePage(eq(PSCS_URI), eq(apiClient), anyInt(), eq(ITEMS_PER_PAGE)))
                .thenThrow(uriValidationException);

        // When and then
        final ServiceException ex = assertThrows(ServiceException.class, () ->
                pageRetrieverService.retrieveAllPages(pageRetrieverClient, PSCS_URI, apiClient, ITEMS_PER_PAGE));
        assertThat(ex.getMessage(), is("Error retrieving items from /company/00000000/persons-with-significant-control"));

    }

    @Test
    @DisplayName("retrieveAllPages() does not propagate ApiErrorResponseException as a ServiceException when items retrieved")
    void retrieveAllPagesDoesNotPropagateApiErrorResponseException() throws Exception {

        // Given
        final PscsApi allPages = new PscsApi();
        when(pageRetrieverClient.retrievePage(eq(PSCS_URI), eq(apiClient), anyInt(), eq(ITEMS_PER_PAGE)))
                .thenReturn(allPages, new PscsApi())
                .thenThrow(apiErrorResponseException);
        // Loop over 2 pages of 2 + 1 items = total 3 items (if it had completed).
        when(pageRetrieverClient.getSize(allPages)).thenReturn(INITIAL_SIZE, ITEMS_PER_PAGE, ITEMS_PER_PAGE, ITEMS_PER_PAGE);
        when(pageRetrieverClient.getTotalCount(allPages)).thenReturn((long) TOTAL_ITEM_COUNT);

        // When
        final PscsApi items =
                pageRetrieverService.retrieveAllPages(pageRetrieverClient, PSCS_URI, apiClient, ITEMS_PER_PAGE);

        // Then
        assertNotNull(items);
        assertThat(items, is(allPages));
        verify(pageRetrieverClient, times(3))
                .retrievePage(eq(PSCS_URI), eq(apiClient), anyInt(), eq(ITEMS_PER_PAGE));
        verify(pageRetrieverClient, times(4)).getSize(allPages);
        verify(pageRetrieverClient, times(3)).getTotalCount(allPages);

    }

    @Test
    @DisplayName("retrieveAllPages() propagate URIValidationException as a ServiceException when items retrieved")
    void retrieveAllPagesDoesNotPropagatesURIValidationException()  throws Exception {

        // Given
        final PscsApi allPages = new PscsApi();
        when(pageRetrieverClient.retrievePage(eq(PSCS_URI), eq(apiClient), anyInt(), eq(ITEMS_PER_PAGE)))
                .thenReturn(allPages, new PscsApi())
                .thenThrow(uriValidationException);
        // Loop over 2 pages of 2 + 1 items = total 3 items (if it had completed).
        when(pageRetrieverClient.getSize(allPages)).thenReturn(INITIAL_SIZE, ITEMS_PER_PAGE, ITEMS_PER_PAGE, ITEMS_PER_PAGE);
        when(pageRetrieverClient.getTotalCount(allPages)).thenReturn((long) TOTAL_ITEM_COUNT);

        // When
        final PscsApi items =
                pageRetrieverService.retrieveAllPages(pageRetrieverClient, PSCS_URI, apiClient, ITEMS_PER_PAGE);

        // Then
        assertNotNull(items);
        assertThat(items, is(allPages));
        verify(pageRetrieverClient, times(3))
                .retrievePage(eq(PSCS_URI), eq(apiClient), anyInt(), eq(ITEMS_PER_PAGE));
        verify(pageRetrieverClient, times(4)).getSize(allPages);
        verify(pageRetrieverClient, times(3)).getTotalCount(allPages);
    }



}