package uk.gov.companieshouse.document.generator.company.report.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

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

@ExtendWith(MockitoExtension.class)
class PscsServiceTest {

    @InjectMocks
    private PscsService pscsService;

    @Mock
    private CompanyReportApiClientService companyReportApiClientService;

    @Mock
    private PscsPageRetrieverService pageRetrieverService;

    @Mock
    private ApiClient apiClient;

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
        when(pageRetrieverService.retrieve(eq(PSCS_URI), eq(apiClient), anyInt())).thenReturn(createPscsApi());

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
        when(pageRetrieverService.retrieve(eq(PSCS_URI), eq(apiClient), anyInt())).thenThrow(apiErrorResponseException);

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
        when(pageRetrieverService.retrieve(eq(PSCS_URI), eq(apiClient), anyInt())).thenThrow(uriValidationException);

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