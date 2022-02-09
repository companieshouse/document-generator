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
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PscsServiceTest {

    @InjectMocks
    private PscsService pscsService;

    @Mock
    private CompanyReportApiClientService companyReportApiClientService;

    @Mock
    private ApiClient apiClient;

    @Mock
    private PscsResourceHandler pscsResourceHandler;

    @Mock
    private PscsList pscsList;

    @Mock
    private ApiResponse<PscsApi> responseWithData;

    @Mock
    private ApiErrorResponseException apiErrorResponseException;

    @Mock
    private URIValidationException uriValidationException;

    private static final String COMPANY_NUMBER = "00000000";
    private static final String PSCS_URI = "/company/00000000/persons-with-significant-control";

    @Test
    @DisplayName("getPscs() gets PscsApi instance successfully")
    void getPscsSuccessfully() throws Exception {

        // Given
        when(companyReportApiClientService.getApiClient()).thenReturn(apiClient);
        when(apiClient.pscs()).thenReturn(pscsResourceHandler);
        when(pscsResourceHandler.list(PSCS_URI)).thenReturn(pscsList);

        when(pscsList.execute()).thenReturn(responseWithData);
        when(responseWithData.getData()).thenReturn(createPscsApi());

        // When
        final PscsApi api =  pscsService.getPscs(COMPANY_NUMBER);

        // Then
        assertNotNull(api);
        assertEquals(2L, (long) api.getActiveCount());
    }

    @Test
    @DisplayName("getPscs() propagates ApiErrorResponseException as a ServiceException")
    void getPscsPropagatesApiErrorResponseException() throws Exception {

        // Given
        when(companyReportApiClientService.getApiClient()).thenReturn(apiClient);
        when(apiClient.pscs()).thenReturn(pscsResourceHandler);
        when(pscsResourceHandler.list(PSCS_URI)).thenReturn(pscsList);

        when(pscsList.execute()).thenThrow(apiErrorResponseException);

        // When and then
        final ServiceException exception = assertThrows(ServiceException.class, () ->
                pscsService.getPscs(COMPANY_NUMBER));
        assertThat(exception.getMessage(), is("Error retrieving pscs"));
    }

    @Test
    @DisplayName("getPscs() propagates URIValidationException as a ServiceException")
    void getPscsPropagatesURIValidationException() throws Exception {

        // Given
        when(companyReportApiClientService.getApiClient()).thenReturn(apiClient);
        when(apiClient.pscs()).thenReturn(pscsResourceHandler);
        when(pscsResourceHandler.list(PSCS_URI)).thenReturn(pscsList);

        when(pscsList.execute()).thenThrow(uriValidationException);

        // When and then
        final ServiceException exception = assertThrows(ServiceException.class, () ->
                pscsService.getPscs(COMPANY_NUMBER));
        assertThat(exception.getMessage(), is("Invalid URI for pscs resource"));
    }

    private PscsApi createPscsApi() {
        final PscsApi api = new PscsApi();
        api.setActiveCount(2L);
        return api;
    }
}