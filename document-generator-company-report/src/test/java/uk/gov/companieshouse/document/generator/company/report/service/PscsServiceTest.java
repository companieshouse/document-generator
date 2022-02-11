package uk.gov.companieshouse.document.generator.company.report.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.model.psc.PscsApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PscsServiceTest {

    @InjectMocks
    private PscsService pscsService;

    @Mock
    private CompanyReportApiClientService companyReportApiClientService;

    @Mock
    private PageRetrieverService<PscsApi> pageRetrieverService;

    @Mock
    private ApiClient apiClient;

    private static final String COMPANY_NUMBER = "00000000";
    private static final String PSCS_URI = "/company/00000000/persons-with-significant-control";

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

    private PscsApi createPscsApi() {
        final PscsApi api = new PscsApi();
        api.setActiveCount(2L);
        return api;
    }
}