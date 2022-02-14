package uk.gov.companieshouse.document.generator.company.report.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.charges.ChargesResourceHandler;
import uk.gov.companieshouse.api.handler.charges.request.ChargesGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.api.model.charges.ChargesApi;

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
import static uk.gov.companieshouse.document.generator.company.report.service.PageRetrieverClientConstants.ITEMS_PER_PAGE_KEY;
import static uk.gov.companieshouse.document.generator.company.report.service.PageRetrieverClientConstants.START_INDEX_KEY;

@ExtendWith(MockitoExtension.class)
public class ChargesServiceTest {

    private static final String COMPANY_NUMBER = "00000000";
    private static final String CHARGES_URI = "/company/00000000/charges";

    @InjectMocks
    private ChargesService chargesService;

    @Mock
    private ApiClient mockApiClient;

    @Mock
    private  CompanyReportApiClientService mockCompanyReportApiClientService;

    @Mock
    private PageRetrieverService<ChargesApi> pageRetrieverService;

    @Mock
    private ChargesApi allPages;

    @Mock
    private ChargesApi anotherPage;

    @Mock
    private List<ChargeApi> items;

    @Mock
    private List<ChargeApi> anotherPageItems;

    @Mock
    private ChargesResourceHandler resourceHandler;

    @Mock
    private ChargesGet chargesGet;

    @Mock
    private ApiResponse<ChargesApi> response;

    @Mock
    private ApiErrorResponseException apiErrorResponseException;

    @Mock
    private URIValidationException uriValidationException;

    @Test
    @DisplayName("Test get charges api response is not null")
    void testGetChargesSuccessful() throws Exception {

        // Given
        when(mockCompanyReportApiClientService.getApiClient()).thenReturn(mockApiClient);
        when(pageRetrieverService.retrieveAllPages(eq(chargesService), eq(CHARGES_URI), eq(mockApiClient), anyInt()))
                .thenReturn(createChargesApi());

        // When
        final ChargesApi chargesApi =  chargesService.getCharges(COMPANY_NUMBER);

        // Then
        assertNotNull(chargesApi);
        assertEquals(2L, (long) chargesApi.getSatisfiedCount());
    }

    @Test
    @DisplayName("getSize() behaves correctly")
    void getSizeBehavesCorrectly() {

        // Given
        when(allPages.getItems()).thenReturn(items);
        when(items.size()).thenReturn(150);

        // When
        final int size = chargesService.getSize(allPages);

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
        final long count = chargesService.getTotalCount(allPages);

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
        chargesService.addPage(allPages, anotherPage);

        // Then
        verify(allPages).getItems();
        verify(anotherPage).getItems();
        verify(items).addAll(anotherPageItems);

    }

    @Test
    @DisplayName("retrievePage() behaves correctly")
    void retrievePageBehavesCorrectly() throws ApiErrorResponseException, URIValidationException {

        // Given
        when(mockApiClient.charges()).thenReturn(resourceHandler);
        when(resourceHandler.get(CHARGES_URI)).thenReturn(chargesGet);
        when(chargesGet.execute()).thenReturn(response);
        when(response.getData()).thenReturn(anotherPage);

        // When
        final ChargesApi pageRetrieved = chargesService.retrievePage(CHARGES_URI, mockApiClient, 200, 100);

        // Then
        assertThat(pageRetrieved, is(anotherPage));
        verify(mockApiClient).charges();
        verify(resourceHandler).get(CHARGES_URI);
        verify(chargesGet).addQueryParams(ITEMS_PER_PAGE_KEY, "100");
        verify(chargesGet).addQueryParams(START_INDEX_KEY, "200");
        verify(chargesGet).execute();
        verify(response).getData();
    }

    @Test
    @DisplayName("retrievePage() propagates ApiErrorResponseException")
    void retrievePagePropagatesApiErrorResponseException() throws Exception {

        // Given
        when(mockApiClient.charges()).thenReturn(resourceHandler);
        when(resourceHandler.get(CHARGES_URI)).thenReturn(chargesGet);
        when(chargesGet.execute()).thenThrow(apiErrorResponseException);
        when(apiErrorResponseException.getMessage()).thenReturn("Test message");

        // When and then
        final ApiErrorResponseException ex = assertThrows(ApiErrorResponseException.class, () ->
                chargesService.retrievePage(CHARGES_URI, mockApiClient, 200, 100));
        assertThat(ex.getMessage(), is("Test message"));

    }

    @Test
    @DisplayName("retrievePage() propagates URIValidationException")
    void retrievePagePropagatesURIValidationException() throws Exception {

        // Given
        when(mockApiClient.charges()).thenReturn(resourceHandler);
        when(resourceHandler.get(CHARGES_URI)).thenReturn(chargesGet);
        when(chargesGet.execute()).thenThrow(uriValidationException);
        when(uriValidationException.getMessage()).thenReturn("Test message");

        // When and then
        final URIValidationException ex = assertThrows(URIValidationException.class, () ->
                chargesService.retrievePage(CHARGES_URI, mockApiClient, 200, 100));
        assertThat(ex.getMessage(), is("Test message"));

    }


    private ChargesApi createChargesApi() {

        ChargesApi chargesApi = new ChargesApi();

        chargesApi.setEtag("etag");
        chargesApi.setPartSatisfiedCount(1L);
        chargesApi.setSatisfiedCount(2L);
        chargesApi.setTotalCount(2L);

        List<ChargeApi> items = new ArrayList<>();
        ChargeApi chargeApi = new ChargeApi();

        items.add(chargeApi);

        chargesApi.setItems(items);

        return chargesApi;
    }
}
