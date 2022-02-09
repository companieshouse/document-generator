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
import uk.gov.companieshouse.api.handler.insolvency.InsolvencyResourceHandler;
import uk.gov.companieshouse.api.handler.insolvency.request.InsolvencyGet;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.insolvency.CaseApi;
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InsolvencyServiceTest {

    @InjectMocks
    private InsolvencyService insolvencyService;

    @Mock
    private ApiClient mockApiClient;

    @Mock
    private InsolvencyResourceHandler mockInsolvencyResourceHandler;

    @Mock
    private InsolvencyGet mockInsolvencyGet;

    @Mock
    private CompanyReportApiClientService mockCompanyReportApiClientService;

    @Mock
    private ApiResponse<InsolvencyApi> responseWithData;

    private static final String INSOLVENCY_URI = "/company/00000000/insolvency";
    private static final String COMPANY_NUMBER = "00000000";

    @BeforeEach
    void init() {
        when(mockCompanyReportApiClientService.getApiClient()).thenReturn(mockApiClient);
        when(mockApiClient.insolvency()).thenReturn(mockInsolvencyResourceHandler);
        when(mockInsolvencyResourceHandler.get(INSOLVENCY_URI)).thenReturn(mockInsolvencyGet);
    }

    @Test
    @DisplayName("Test get insolvency api response is not null")
    void testGetInsolvencySuccessful() throws Exception {

        when(mockInsolvencyGet.execute()).thenReturn(responseWithData);
        when(responseWithData.getData()).thenReturn(createInsolvencyApi());

        InsolvencyApi insolvencyApi = insolvencyService.getInsolvency(COMPANY_NUMBER);

        assertNotNull(insolvencyApi);
        assertEquals(1, insolvencyApi.getCases().size());
    }

    @Test
    @DisplayName("Test get insolveny api throws service exception with api error exception")
    void testGetInsolvencyApiErrorResponse() throws Exception {

        when(mockInsolvencyGet.execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ServiceException.class, () ->
            insolvencyService.getInsolvency(COMPANY_NUMBER));
    }

    @Test
    @DisplayName("Test get insolvency api throws service exception with uri validation exception")
    void testGetInsolvencyURIValidation() throws Exception {

        when(mockInsolvencyGet.execute()).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () ->
            insolvencyService.getInsolvency(COMPANY_NUMBER));
    }

    private InsolvencyApi createInsolvencyApi() {
        InsolvencyApi insolvencyApi = new InsolvencyApi();
        insolvencyApi.setEtag("etag");

        List<CaseApi> cases = new ArrayList<>();
        CaseApi caseApi = new CaseApi();
        caseApi.setNumber(12345678L);
        cases.add(caseApi);

        insolvencyApi.setCases(cases);
        return insolvencyApi;
    }
}
