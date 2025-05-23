package Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import uk.gov.companieshouse.api.handler.officers.OfficersResourceHandler;
import uk.gov.companieshouse.api.handler.officers.request.OfficersList;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.service.OracleQueryApiClientService;
import uk.gov.companieshouse.document.generator.company.report.service.oracle.OfficerDetailsServiceOracle;

@ExtendWith(MockitoExtension.class)
class OfficerDetailsOracleTest {

    private static final String COMPANY_NUMBER = "00000000";

    @Mock
    private OracleQueryApiClientService oracleQueryApiClientService;

    @InjectMocks
    private OfficerDetailsServiceOracle officerDetailsServiceOracle;

    @Mock
    private InternalApiClient internalApiClient;

    @Mock
    private OfficersResourceHandler officersResourceHandler;

    @Mock
    OfficersList officersList;

    @Mock
    ApiResponse<OfficersApi> officersApiApiResponse;

    @BeforeEach
    void setUp() {
        when(oracleQueryApiClientService.getInternalApiClient()).thenReturn(internalApiClient);
        when(internalApiClient.officers()).thenReturn(officersResourceHandler);
        when(officersResourceHandler.list(Mockito.anyString())).thenReturn(officersList);
    }

    @Test
    @DisplayName("Test officers api response is not null")
    void testOfficerDetailsServiceOracle() throws ApiErrorResponseException, URIValidationException {
        OfficersApi officersApi = new OfficersApi();

        when(officersList.execute()).thenReturn(officersApiApiResponse);
        when(officersApiApiResponse.getData()).thenReturn(officersApi);

        OfficersApi response = officerDetailsServiceOracle.getOfficerDetails(COMPANY_NUMBER);

        assertNotNull(response);

    }

    @Test
    @DisplayName("Test get officer details throws an API Exception")
    void testGetOfficerDetailsException() throws ApiErrorResponseException, URIValidationException {

        when(officersList.execute()).thenThrow(new URIValidationException("Invalid URI"));

        OfficersApi response = officerDetailsServiceOracle.getOfficerDetails(COMPANY_NUMBER);

        assertNull(response);
    }

}
