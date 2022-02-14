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
import static uk.gov.companieshouse.document.generator.company.report.service.PscsService.ITEMS_PER_PAGE_KEY;
import static uk.gov.companieshouse.document.generator.company.report.service.PscsService.START_INDEX_KEY;

@ExtendWith(MockitoExtension.class)
class PscsServiceTest {

    private static final String COMPANY_NUMBER = "00000000";
    private static final String PSCS_URI = "/company/00000000/persons-with-significant-control";

    @InjectMocks
    private PscsService pscsService;

    @Mock
    private CompanyReportApiClientService companyReportApiClientService;

    @Mock
    private PageRetrieverService<PscsApi> pageRetrieverService;

    @Mock
    private ApiClient apiClient;

    @Mock
    private PscsApi allPages;

    @Mock
    private PscsApi anotherPage;

    @Mock
    private List<PscApi> items;

    @Mock
    private List<PscApi> anotherPageItems;

    @Mock
    private PscsResourceHandler resourceHandler;

    @Mock
    private PscsList list;

    @Mock
    private ApiResponse<PscsApi> response;

    @Mock
    private ApiErrorResponseException apiErrorResponseException;

    @Mock
    private URIValidationException uriValidationException;

    @Test
    @DisplayName("getPscs() gets PscsApi instance successfully")
    void getPscsSuccessfully() throws Exception {

        // Given
        when(companyReportApiClientService.getApiClient()).thenReturn(apiClient);
        when(pageRetrieverService.retrieveAllPages(eq(pscsService), eq(PSCS_URI), eq(apiClient), anyInt()))
                .thenReturn(createPscsApi());

        // When
        final PscsApi api =  pscsService.getPscs(COMPANY_NUMBER);

        // Then
        assertNotNull(api);
        assertEquals(2L, (long) api.getActiveCount());
    }

    @Test
    @DisplayName("getSize() behaves correctly")
    void getSizeBehavesCorrectly() {

        // Given
        when(allPages.getItems()).thenReturn(items);
        when(items.size()).thenReturn(150);

        // When
        final int size = pscsService.getSize(allPages);

        // Then
        assertThat(size, is(150));
        verify(allPages).getItems();
        verify(items).size();
    }

    @Test
    @DisplayName("getTotalCount() behaves correctly")
    void getTotalCountBehavesCorrectly() {

        // Given
        when(allPages.getTotalResults()).thenReturn(150L);

        // When
        final long count = pscsService.getTotalCount(allPages);

        // Then
        assertThat(count, is(150L));
        verify(allPages).getTotalResults();
    }

    @Test
    @DisplayName("addPage() behaves correctly")
    void addPageBehavesCorrectly() {

        // Given
        when(allPages.getItems()).thenReturn(items);
        when(anotherPage.getItems()).thenReturn(anotherPageItems);

        // When
        pscsService.addPage(allPages, anotherPage);

        // Then
        verify(allPages).getItems();
        verify(anotherPage).getItems();
        verify(items).addAll(anotherPageItems);

    }

    @Test
    @DisplayName("retrievePage() behaves correctly")
    void retrievePageBehavesCorrectly() throws ApiErrorResponseException, URIValidationException {

        // Given
        when(apiClient.pscs()).thenReturn(resourceHandler);
        when(resourceHandler.list(PSCS_URI)).thenReturn(list);
        when(list.execute()).thenReturn(response);
        when(response.getData()).thenReturn(anotherPage);

        // When
        final PscsApi pageRetrieved = pscsService.retrievePage(PSCS_URI, apiClient, 200, 100);

        // Then
        assertThat(pageRetrieved, is(anotherPage));
        verify(apiClient).pscs();
        verify(resourceHandler).list(PSCS_URI);
        verify(list).addQueryParams(ITEMS_PER_PAGE_KEY, "100");
        verify(list).addQueryParams(START_INDEX_KEY, "200");
        verify(list).execute();
        verify(response).getData();
    }

    @Test
    @DisplayName("retrievePage() propagates ApiErrorResponseException")
    void retrievePagePropagatesApiErrorResponseException() throws Exception {

        // Given
        when(apiClient.pscs()).thenReturn(resourceHandler);
        when(resourceHandler.list(PSCS_URI)).thenReturn(list);
        when(list.execute()).thenThrow(apiErrorResponseException);
        when(apiErrorResponseException.getMessage()).thenReturn("Test message");

        // When and then
        final ApiErrorResponseException ex = assertThrows(ApiErrorResponseException.class, () ->
                pscsService.retrievePage(PSCS_URI, apiClient, 200, 100));
        assertThat(ex.getMessage(), is("Test message"));

    }

    @Test
    @DisplayName("retrievePage() propagates URIValidationException")
    void retrievePagePropagatesURIValidationException() throws Exception {

        // Given
        when(apiClient.pscs()).thenReturn(resourceHandler);
        when(resourceHandler.list(PSCS_URI)).thenReturn(list);
        when(list.execute()).thenThrow(uriValidationException);
        when(uriValidationException.getMessage()).thenReturn("Test message");

        // When and then
        final URIValidationException ex = assertThrows(URIValidationException.class, () ->
                pscsService.retrievePage(PSCS_URI, apiClient, 200, 100));
        assertThat(ex.getMessage(), is("Test message"));

    }

    private PscsApi createPscsApi() {
        final PscsApi api = new PscsApi();
        api.setActiveCount(2L);
        return api;
    }
}