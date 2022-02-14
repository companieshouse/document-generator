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
import uk.gov.companieshouse.api.handler.officers.OfficersResourceHandler;
import uk.gov.companieshouse.api.handler.officers.request.OfficersList;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.officers.CompanyOfficerApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;

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
import static uk.gov.companieshouse.document.generator.company.report.service.OfficerService.ITEMS_PER_PAGE_KEY;
import static uk.gov.companieshouse.document.generator.company.report.service.OfficerService.START_INDEX_KEY;

@ExtendWith(MockitoExtension.class)
class OfficerServiceTest {

    private static final String COMPANY_NUMBER = "00000000";
    private static final String OFFICERS_URI = "/company/00000000/officers";

    @InjectMocks
    private OfficerService officerService;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CompanyReportApiClientService companyReportApiClientService;

    @Mock
    private PageRetrieverService<OfficersApi> pageRetrieverService;

    @Mock
    private OfficersApi allPages;

    @Mock
    private OfficersApi anotherPage;

    @Mock
    private List<CompanyOfficerApi> items;

    @Mock
    private List<CompanyOfficerApi> anotherPageItems;

    @Mock
    private OfficersResourceHandler resourceHandler;

    @Mock
    private OfficersList list;

    @Mock
    private ApiResponse<OfficersApi> response;

    @Mock
    private ApiErrorResponseException apiErrorResponseException;

    @Mock
    private URIValidationException uriValidationException;

    @Test
    @DisplayName("Test get officers api response is not null")
    void testGetOfficersSuccessful() throws Exception {

        when(companyReportApiClientService.getApiClient()).thenReturn(apiClient);
        when(pageRetrieverService.retrieveAllPages(eq(officerService),
                eq(OFFICERS_URI), eq(apiClient), anyInt())).thenReturn(createOfficersApi());

        OfficersApi officersApi = officerService.getOfficers(COMPANY_NUMBER);

        assertNotNull(officersApi);
        assertEquals(2L, (long) officersApi.getActiveCount());
    }

    @Test
    @DisplayName("getSize() behaves correctly")
    void getSizeBehavesCorrectly() {

        // Given
        when(allPages.getItems()).thenReturn(items);
        when(items.size()).thenReturn(150);

        // When
        final int size = officerService.getSize(allPages);

        // Then
        assertThat(size, is(150));
        verify(allPages).getItems();
        verify(items).size();
    }

    @Test
    @DisplayName("getTotalCount() behaves correctly")
    void getTotalCountBehavesCorrectly() {

        // Given
        when(allPages.getTotalResults()).thenReturn(150);

        // When
        final long count = officerService.getTotalCount(allPages);

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
        officerService.addPage(allPages, anotherPage);

        // Then
        verify(allPages).getItems();
        verify(anotherPage).getItems();
        verify(items).addAll(anotherPageItems);

    }

    @Test
    @DisplayName("retrievePage() behaves correctly")
    void retrievePageBehavesCorrectly() throws ApiErrorResponseException, URIValidationException {

        // Given
        when(apiClient.officers()).thenReturn(resourceHandler);
        when(resourceHandler.list(OFFICERS_URI)).thenReturn(list);
        when(list.execute()).thenReturn(response);
        when(response.getData()).thenReturn(anotherPage);

        // When
        final OfficersApi pageRetrieved = officerService.retrievePage(OFFICERS_URI, apiClient, 200, 100);

        // Then
        assertThat(pageRetrieved, is(anotherPage));
        verify(apiClient).officers();
        verify(resourceHandler).list(OFFICERS_URI);
        verify(list).addQueryParams(ITEMS_PER_PAGE_KEY, "100");
        verify(list).addQueryParams(START_INDEX_KEY, "200");
        verify(list).execute();
        verify(response).getData();
    }

    @Test
    @DisplayName("retrievePage() propagates ApiErrorResponseException")
    void retrievePagePropagatesApiErrorResponseException() throws Exception {

        // Given
        when(apiClient.officers()).thenReturn(resourceHandler);
        when(resourceHandler.list(OFFICERS_URI)).thenReturn(list);
        when(list.execute()).thenThrow(apiErrorResponseException);
        when(apiErrorResponseException.getMessage()).thenReturn("Test message");

        // When and then
        final ApiErrorResponseException ex = assertThrows(ApiErrorResponseException.class, () ->
                officerService.retrievePage(OFFICERS_URI, apiClient, 200, 100));
        assertThat(ex.getMessage(), is("Test message"));

    }

    @Test
    @DisplayName("retrievePage() propagates URIValidationException")
    void retrievePagePropagatesURIValidationException() throws Exception {

        // Given
        when(apiClient.officers()).thenReturn(resourceHandler);
        when(resourceHandler.list(OFFICERS_URI)).thenReturn(list);
        when(list.execute()).thenThrow(uriValidationException);
        when(uriValidationException.getMessage()).thenReturn("Test message");

        // When and then
        final URIValidationException ex = assertThrows(URIValidationException.class, () ->
                officerService.retrievePage(OFFICERS_URI, apiClient, 200, 100));
        assertThat(ex.getMessage(), is("Test message"));

    }

    private OfficersApi createOfficersApi() {
        final OfficersApi api = new OfficersApi();
        api.setActiveCount(2L);
        return api;
    }

}
