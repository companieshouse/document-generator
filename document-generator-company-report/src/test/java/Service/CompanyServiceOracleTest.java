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
import uk.gov.companieshouse.api.handler.company.CompanyResourceHandler;
import uk.gov.companieshouse.api.handler.company.request.CompanyGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.company.CompanyProfileApi;
import uk.gov.companieshouse.document.generator.company.report.exception.OracleQueryApiException;
import uk.gov.companieshouse.document.generator.company.report.service.ApiClientService;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.CompanyServiceOracle;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class CompanyServiceOracleTest {

    @Mock
    private ApiClientService apiClientService;

    @InjectMocks
    private CompanyServiceOracle companyServiceOracle;

    @Mock
    private ApiResponse<CompanyProfileApi> apiGetResponseCompanyProfile;

    @Mock
    private CompanyResourceHandler company;

    @Mock
    private CompanyGet companyGet;

    @Mock
    private InternalApiClient internalApiClient;

    private static final String COMPANY_NUMBER = "00000000";

    @BeforeEach
    void setUp() {
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.company()).thenReturn(company);
        when(company.get(Mockito.anyString())).thenReturn(companyGet);
    }

    @Test
    @DisplayName("Test company service oracle success")
    void testCompanyServiceOracleSuccess() throws ApiErrorResponseException, URIValidationException {
        CompanyProfileApi companyProfileApi = new CompanyProfileApi();

        when(companyGet.execute()).thenReturn(apiGetResponseCompanyProfile);
        when(apiGetResponseCompanyProfile.getData()).thenReturn(companyProfileApi);

        CompanyProfileApi response = companyServiceOracle.getCompanyProfile(COMPANY_NUMBER);

        assertNotNull(response);

    }

    @Test
    @DisplayName("Test company service oracle failure due to URIValidationException")
    void testCompanyServiceOracleUriValidationException() throws ApiErrorResponseException, URIValidationException {

        when(companyGet.execute()).thenThrow(new URIValidationException("Invalid URI"));

        OracleQueryApiException exception = assertThrows(
                OracleQueryApiException.class,
                () -> companyServiceOracle.getCompanyProfile(COMPANY_NUMBER)
        );

        assertEquals("Error Retrieving Company Profile data for 00000000", exception.getMessage());
    }

    @Test
    @DisplayName("Test company service oracle failure due to ApiErrorResponseException")
    void testCompanyServiceOracleApiErrorResponseException() throws ApiErrorResponseException, URIValidationException {

        when(companyGet.execute()).thenThrow( ApiErrorResponseException.fromIOException(new IOException("IO error")));

        OracleQueryApiException exception = assertThrows(
                OracleQueryApiException.class,
                () -> companyServiceOracle.getCompanyProfile(COMPANY_NUMBER)
        );

        assertEquals("Error Retrieving Company Profile data for 00000000", exception.getMessage());
    }

}
