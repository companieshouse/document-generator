package uk.gov.companieshouse.document.generator.company.report.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfficerServiceTest {

    @InjectMocks
    private OfficerService officerService;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CompanyReportApiClientService companyReportApiClientService;

    @Mock
    private PageRetrieverService<OfficersApi> pageRetrieverService;

    private static final String COMPANY_NUMBER = "00000000";
    private static final String OFFICERS_URI = "/company/00000000/officers";

    @BeforeEach
    void init() {
        when(companyReportApiClientService.getApiClient()).thenReturn(apiClient);
    }

    @Test
    @DisplayName("Test get officers api response is not null")
    void testGetOfficersSuccessful() throws Exception {

        when(pageRetrieverService.retrieveAllPages(eq(officerService),
                eq(OFFICERS_URI), eq(apiClient), anyInt())).thenReturn(createOfficersApi());

        OfficersApi officersApi = officerService.getOfficers(COMPANY_NUMBER);

        assertNotNull(officersApi);
        assertEquals(2L, (long) officersApi.getActiveCount());
    }

    @Test
    @DisplayName("Test get officers api throws service exception with api error exception")
    void testGetOfficersApiErrorResponse() throws Exception {

        when(pageRetrieverService.retrieveAllPages(eq(officerService),
                eq(OFFICERS_URI), eq(apiClient), anyInt())).thenThrow(ApiErrorResponseException.class);

        assertThrows(ServiceException.class, () ->
                officerService.getOfficers(COMPANY_NUMBER));
    }

    @Test
    @DisplayName("Test get officers api throws service exception with uri validation exception")
    void testGetOfficersURIValidation() throws Exception {

        when(pageRetrieverService.retrieveAllPages(eq(officerService),
                eq(OFFICERS_URI), eq(apiClient), anyInt())).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () ->
                officerService.getOfficers(COMPANY_NUMBER));
    }

    private OfficersApi createOfficersApi() {
        final OfficersApi api = new OfficersApi();
        api.setActiveCount(2L);
        return api;
    }

}
