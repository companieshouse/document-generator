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
import uk.gov.companieshouse.api.handler.filinghistory.FilingHistoryResourceHandler;
import uk.gov.companieshouse.api.handler.filinghistory.request.FilingHistoryList;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.companieshouse.document.generator.company.report.service.FilingHistoryService.ITEMS_PER_PAGE_KEY;
import static uk.gov.companieshouse.document.generator.company.report.service.FilingHistoryService.START_INDEX_KEY;

@ExtendWith(MockitoExtension.class)
class FilingHistoryServiceTest {

    private static final String COMPANY_NUMBER = "00000000";
    private static final String FILING_HISTORY_URI = "/company/00000000/filing-history";

    @InjectMocks
    private FilingHistoryService filingHistoryService;

    @Mock
    private ApiClient mockApiClient;

    @Mock
    private CompanyReportApiClientService mockCompanyReportApiClientService;

    @Mock
    private PageRetrieverService<FilingHistoryApi> pageRetrieverService;

    @Mock
    private ApiClient apiClient;

    @Mock
    private FilingHistoryApi allPages;

    @Mock
    private FilingHistoryApi anotherPage;

    @Mock
    private List<FilingApi> items;

    @Mock
    private List<FilingApi> anotherPageItems;

    @Mock
    private FilingHistoryResourceHandler resourceHandler;

    @Mock
    private FilingHistoryList list;

    @Mock
    private ApiResponse<FilingHistoryApi> response;

    @Mock
    private ApiErrorResponseException apiErrorResponseException;

    @Mock
    private URIValidationException uriValidationException;

    @Test
    @DisplayName("Test get filing history api response is not null")
    void testGetFilingHistorySuccessful() throws Exception {

        when(mockCompanyReportApiClientService.getApiClient()).thenReturn(mockApiClient);
        when(pageRetrieverService.retrieveAllPages(eq(filingHistoryService),
                eq(FILING_HISTORY_URI), eq(mockApiClient), anyInt()))
                .thenReturn(createFilingHistoryApi());

        FilingHistoryApi filingHistoryApi = filingHistoryService.getFilingHistory(COMPANY_NUMBER);

        assertNotNull(filingHistoryApi);
        assertEquals(1, filingHistoryApi.getItems().size());
    }

    @Test
    @DisplayName("getSize() behaves correctly")
    void getSizeBehavesCorrectly() {

        // Given
        when(allPages.getItems()).thenReturn(items);
        when(items.size()).thenReturn(150);

        // When
        final int size = filingHistoryService.getSize(allPages);

        // Then
        assertThat(size, is(150));
        verify(allPages).getItems();
        verify(items).size();
    }

    @Test
    @DisplayName("getTotalCount() behaves correctly")
    void getTotalCountBehavesCorrectly() {

        // Given
        when(allPages.getTotalCount()).thenReturn(150L);

        // When
        final long count = filingHistoryService.getTotalCount(allPages);

        // Then
        assertThat(count, is(150L));
        verify(allPages).getTotalCount();
    }

    @Test
    @DisplayName("addPage() behaves correctly")
    void addPageBehavesCorrectly() {

        // Given
        when(allPages.getItems()).thenReturn(items);
        when(anotherPage.getItems()).thenReturn(anotherPageItems);

        // When
        filingHistoryService.addPage(allPages, anotherPage);

        // Then
        verify(allPages).getItems();
        verify(anotherPage).getItems();
        verify(items).addAll(anotherPageItems);

    }

    @Test
    @DisplayName("retrievePage() behaves correctly")
    void retrievePageBehavesCorrectly() throws ApiErrorResponseException, URIValidationException {

        // Given
        when(apiClient.filingHistory()).thenReturn(resourceHandler);
        when(resourceHandler.list(FILING_HISTORY_URI)).thenReturn(list);
        when(list.execute()).thenReturn(response);
        when(response.getData()).thenReturn(anotherPage);

        // When
        final FilingHistoryApi pageRetrieved = filingHistoryService.retrievePage(FILING_HISTORY_URI, apiClient, 200, 100);

        // Then
        assertThat(pageRetrieved, is(anotherPage));
        verify(apiClient).filingHistory();
        verify(resourceHandler).list(FILING_HISTORY_URI);
        verify(list).addQueryParams(ITEMS_PER_PAGE_KEY, "100");
        verify(list).addQueryParams(START_INDEX_KEY, "200");
        verify(list).execute();
        verify(response).getData();
    }

    @Test
    @DisplayName("retrievePage() propagates ApiErrorResponseException")
    void retrievePagePropagatesApiErrorResponseException() throws Exception {

        // Given
        when(apiClient.filingHistory()).thenReturn(resourceHandler);
        when(resourceHandler.list(FILING_HISTORY_URI)).thenReturn(list);
        when(list.execute()).thenThrow(apiErrorResponseException);
        when(apiErrorResponseException.getMessage()).thenReturn("Test message");

        // When and then
        final ApiErrorResponseException ex = assertThrows(ApiErrorResponseException.class, () ->
                filingHistoryService.retrievePage(FILING_HISTORY_URI, apiClient, 200, 100));
        assertThat(ex.getMessage(), is("Test message"));

    }

    @Test
    @DisplayName("retrievePage() propagates URIValidationException")
    void retrievePagePropagatesURIValidationException() throws Exception {

        // Given
        when(apiClient.filingHistory()).thenReturn(resourceHandler);
        when(resourceHandler.list(FILING_HISTORY_URI)).thenReturn(list);
        when(list.execute()).thenThrow(uriValidationException);
        when(uriValidationException.getMessage()).thenReturn("Test message");

        // When and then
        final URIValidationException ex = assertThrows(URIValidationException.class, () ->
                filingHistoryService.retrievePage(FILING_HISTORY_URI, apiClient, 200, 100));
        assertThat(ex.getMessage(), is("Test message"));

    }


    private FilingHistoryApi createFilingHistoryApi() {

        FilingHistoryApi filingHistoryApi = new FilingHistoryApi();
        filingHistoryApi.setEtag("etag");
        filingHistoryApi.setFilingHistoryStatus("status");
        filingHistoryApi.setKind("kind");
        filingHistoryApi.setItemsPerPage(1L);
        filingHistoryApi.setStartIndex(0L);
        filingHistoryApi.setTotalCount(2L);

        List<FilingApi> items = new ArrayList<>();
        FilingApi filingApi = new FilingApi();

        items.add(filingApi);

        filingHistoryApi.setItems(items);

        return filingHistoryApi;
    }
}
