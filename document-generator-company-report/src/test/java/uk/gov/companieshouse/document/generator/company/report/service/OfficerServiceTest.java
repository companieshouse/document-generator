package uk.gov.companieshouse.document.generator.company.report.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import uk.gov.companieshouse.api.handler.officers.OfficersResourceHandler;
import uk.gov.companieshouse.api.handler.officers.request.OfficersList;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.officers.CompanyOfficerApi;
import uk.gov.companieshouse.api.model.officers.OfficersApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OfficerServiceTest {

    @InjectMocks
    private OfficerService officerService;

    @Mock
    private ApiClient mockApiClient;

    @Mock
    private CompanyReportApiClientService mockCompanyReportApiClientService;

    @Mock
    private OfficersList mockOfficersList;

    @Mock
    private OfficersResourceHandler mockResourceHandler;

    @Mock
    private ApiResponse<OfficersApi> mockApiResponse;

    private static final String COMPANY_NUMBER = "00000000";
    private static final String OFFICERS_URI = "/company/00000000/officers";

    @BeforeEach
    void init() {
        when(mockCompanyReportApiClientService.getApiClient()).thenReturn(mockApiClient);
        when(mockApiClient.officers()).thenReturn(mockResourceHandler);
        when(mockResourceHandler.list(OFFICERS_URI)).thenReturn(mockOfficersList);
    }

    @Test
    @DisplayName("Test is successful when it returns up to 100 appointments")
    void testGetOfficersSuccessful() throws ApiErrorResponseException, URIValidationException, ServiceException {
        when(mockOfficersList.execute()).thenReturn(mockApiResponse);
        OfficersApi officersApi = createOfficersApi();
        addOfficerItems(officersApi, 10);
        when(mockApiResponse.getData()).thenReturn(officersApi);

        OfficersApi returnedOfficers = officerService.getOfficers(COMPANY_NUMBER);

        assertNotNull(returnedOfficers);
        assertEquals(10, returnedOfficers.getItems().size());
    }

    @Test
    @DisplayName("Test is successful when it returns more than 100 appointments")
    void testGetOfficersSuccessfulMoreThanOneHundredAppointments()
            throws ApiErrorResponseException, URIValidationException, ServiceException {
        when(mockOfficersList.execute()).thenReturn(mockApiResponse);
        OfficersApi officersApi1 = createOfficersApi();
        OfficersApi officersApi2 = createOfficersApi();
        officersApi1.setTotalResults(110);
        addOfficerItems(officersApi1, 100);
        officersApi2.setTotalResults(110);
        addOfficerItems(officersApi2, 10);
        when(mockApiResponse.getData()).thenReturn(officersApi1).thenReturn(officersApi2);

        OfficersApi returnedOfficers = officerService.getOfficers(COMPANY_NUMBER);

        assertNotNull(returnedOfficers);
        assertEquals(110, returnedOfficers.getItems().size());
    }

    @Test
    @DisplayName("Test is successful when more than 100 appointments then an ApiErrorResponseException")
    void testGetOfficersSuccessfulFollowingApiErrorResponseException()
            throws ApiErrorResponseException, URIValidationException, ServiceException {
        when(mockOfficersList.execute()).thenReturn(mockApiResponse).thenThrow(ApiErrorResponseException.class);
        OfficersApi officersApi = createOfficersApi();
        officersApi.setTotalResults(110);
        addOfficerItems(officersApi, 100);
        when(mockApiResponse.getData()).thenReturn(officersApi);

        OfficersApi returnedOfficers = officerService.getOfficers(COMPANY_NUMBER);

        assertNotNull(returnedOfficers);
        assertEquals(100, returnedOfficers.getItems().size());
    }

    @Test
    @DisplayName("Test returns a ServiceException when an APIErrorResponseException is returned")
    void testGetOfficersApiErrorResponseExceptionReturnsServiceException()
            throws ApiErrorResponseException, URIValidationException {
        when(mockOfficersList.execute()).thenThrow(ApiErrorResponseException.class);
        assertThrows(ServiceException.class, () -> officerService.getOfficers(COMPANY_NUMBER));
    }

    @Test
    @DisplayName("Test returns a ServiceException when a URIValidationException is returned")
    void testGetOfficersURIValidationExceptionReturnsServiceException()
            throws ApiErrorResponseException, URIValidationException {
        when(mockOfficersList.execute()).thenThrow(URIValidationException.class);
        assertThrows(ServiceException.class, () -> officerService.getOfficers(COMPANY_NUMBER));
    }
    
    @Test
    @DisplayName("Test returns a ServiceException following an empty items response")
    void testGetOfficersServiceExceptionFollowingEmptyItems() throws ApiErrorResponseException, URIValidationException {
        when(mockOfficersList.execute()).thenReturn(mockApiResponse).thenThrow(ApiErrorResponseException.class);
        OfficersApi officersApi = createOfficersApi();
        officersApi.setTotalResults(110);
        when(mockApiResponse.getData()).thenReturn(officersApi);
        
        assertThrows(ServiceException.class, () -> officerService.getOfficers(COMPANY_NUMBER));
    }

    private OfficersApi createOfficersApi() {
        OfficersApi officersApi = new OfficersApi();
        officersApi.setActiveCount(20L);
        officersApi.setInactiveCount(20L);
        officersApi.setItemsPerPage(100L);
        officersApi.setKind("kind");
        officersApi.setEtag("etag");
        officersApi.setResignedCount(20);
        officersApi.setStartIndex(0);
        officersApi.setTotalResults(0);

        officersApi.setItems(new ArrayList<>());
        return officersApi;
    }

    private void addOfficerItems(OfficersApi officersApi, int numberOfOfficers) {
        for (int i = 0; i < numberOfOfficers; i++) {
            officersApi.getItems().add(new CompanyOfficerApi());
        }
    }

}
