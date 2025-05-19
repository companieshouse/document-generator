package Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.filinghistory.FilingHistoryResourceHandler;
import uk.gov.companieshouse.api.handler.filinghistory.request.FilingHistoryList;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.filinghistory.FilingHistoryApi;
import uk.gov.companieshouse.document.generator.company.report.exception.OracleQueryApiException;
import uk.gov.companieshouse.document.generator.company.report.service.ApiClientService;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.FilingHistoryServiceOracle;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class FilingHistoryServiceOracleTest {

    @Mock
    private ApiClientService apiClientService;

    @InjectMocks
    private FilingHistoryServiceOracle filingHistoryServiceOracle;

    @Mock
    private InternalApiClient internalApiClient;

    @Mock
    private FilingHistoryResourceHandler filingHistory;

    @Mock
    private FilingHistoryList filingHistoryList;

    @Mock
    private ApiResponse<FilingHistoryApi> apiResponseFilingHistory;


    private static final String COMPANY_NUMBER = "00000000";

    @BeforeEach
    void setUp() {
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.filingHistory()).thenReturn(filingHistory);
        when(filingHistory.list(Mockito.anyString())).thenReturn(filingHistoryList);
    }

    @Test
    @DisplayName("Test filing history api response is not null")
    void testFilingHistoryServiceOracle() throws ApiErrorResponseException, URIValidationException {
        FilingHistoryApi filingHistoryApi = new FilingHistoryApi();

        when(filingHistoryList.execute()).thenReturn(apiResponseFilingHistory);
        when(apiResponseFilingHistory.getData()).thenReturn(filingHistoryApi);

         // Call the method under test
        FilingHistoryApi response = filingHistoryServiceOracle.getFilingHistory(COMPANY_NUMBER);

        assertNotNull(response);
    }

    @Test
    @DisplayName("Test filing history service throws OracleQueryApiException for URIValidationException")
    void testFilingHistoryServiceOracleUriValidationException() throws ApiErrorResponseException, URIValidationException {
        when(filingHistoryList.execute()).thenThrow(new URIValidationException("Invalid URI"));

        OracleQueryApiException exception = assertThrows(
                OracleQueryApiException.class,
                () -> filingHistoryServiceOracle.getFilingHistory(COMPANY_NUMBER)
        );

        assertEquals("Error Retrieving Filing history data for 00000000 at /company/00000000/filing-history", exception.getMessage());
    }

    @Test
    @DisplayName("Test filing history service throws OracleQueryApiException for ApiErrorResponseException")
    void testFilingHistoryServiceOracleApiErrorResponseException() throws ApiErrorResponseException, URIValidationException {
        when(filingHistoryList.execute()).thenThrow(ApiErrorResponseException.fromIOException(new IOException("IO error")));

        OracleQueryApiException exception = assertThrows(
                OracleQueryApiException.class,
                () -> filingHistoryServiceOracle.getFilingHistory(COMPANY_NUMBER)
        );

        assertEquals("Error Retrieving Filing history data for 00000000 at /company/00000000/filing-history", exception.getMessage());
    }

}
