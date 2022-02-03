package uk.gov.companieshouse.document.generator.company.report.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilingHistoryServiceTest {

    @InjectMocks
    private FilingHistoryService filingHistoryService;

    @Mock
    private ApiClient mockApiClient;

    @Mock
    private FilingHistoryResourceHandler mockFilingHistoryResourceHandler;

    @Mock
    private FilingHistoryList mockFilingHistoryList;

    @Mock
    private CompanyReportApiClientService mockCompanyReportApiClientService;

    @Mock
    private ApiResponse<FilingHistoryApi> responseWithData;

    private static final String COMPANY_NUMBER = "00000000";
    private static final String FILING_HISTORY_URI = "/company/00000000/filing-history";

    @BeforeEach
    void init() {
        when(mockCompanyReportApiClientService.getApiClient()).thenReturn(mockApiClient);
        when(mockApiClient.filingHistory()).thenReturn(mockFilingHistoryResourceHandler);
        when(mockFilingHistoryResourceHandler.list(FILING_HISTORY_URI)).thenReturn(mockFilingHistoryList);
    }

    @Test
    @DisplayName("Test get filing history api response is not null")
    void testGetFilingHistorySuccessful() throws Exception {

        when(mockFilingHistoryList.execute()).thenReturn(responseWithData);
        when(responseWithData.getData()).thenReturn(createFilingHistoryApi());

        FilingHistoryApi filingHistoryApi = filingHistoryService.getFilingHistory(COMPANY_NUMBER);

        assertNotNull(filingHistoryApi);
        assertEquals(2, filingHistoryApi.getItems().size());
    }

    @Test
    @DisplayName("Test get filing history api throws service exception with api error exception")
    void testGetFilingHistoryApiErrorResponse() throws Exception {

        when(mockFilingHistoryList.execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ServiceException.class, () ->
            filingHistoryService.getFilingHistory(COMPANY_NUMBER));
    }

    @Test
    @DisplayName("Test get filing history api throws service exception with uri validation exception")
    void testGetFilingHistoryURIValidation() throws Exception {

        when(mockFilingHistoryList.execute()).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () ->
            filingHistoryService.getFilingHistory(COMPANY_NUMBER));
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
