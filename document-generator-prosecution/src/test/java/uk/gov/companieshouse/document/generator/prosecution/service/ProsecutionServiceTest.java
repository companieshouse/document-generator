package uk.gov.companieshouse.document.generator.prosecution.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.InternalApiClient;
import uk.gov.companieshouse.api.error.ApiErrorResponseException;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.handler.prosecution.defendant.PrivateDefendantResourceHandler;
import uk.gov.companieshouse.api.handler.prosecution.defendant.request.PrivateDefendantGet;
import uk.gov.companieshouse.api.handler.prosecution.offence.PrivateOffenceResourceHandler;
import uk.gov.companieshouse.api.handler.prosecution.offence.request.PrivateOffenceList;
import uk.gov.companieshouse.api.handler.prosecution.prosecutioncase.PrivateProsecutionCaseResourceHandler;
import uk.gov.companieshouse.api.handler.prosecution.prosecutioncase.request.PrivateProsecutionCaseGet;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.prosecution.defendant.DefendantApi;
import uk.gov.companieshouse.api.model.prosecution.offence.OffenceApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseApi;
import uk.gov.companieshouse.document.generator.prosecution.exception.ProsecutionServiceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProsecutionServiceTest {

    private static final String PROSECUTION_CASE_URI = "/internal/company/1234/prosecution-cases/4321";
    private static final String DEFENDANT_URI = PROSECUTION_CASE_URI + "/defendants/1122";
    private static final String OFFENCES_URI = DEFENDANT_URI + "/offences";

    private DefendantApi defendantApi = new DefendantApi();
    private ProsecutionCaseApi prosecutionCaseApi = new ProsecutionCaseApi();
    private OffenceApi[] offenceApis = new OffenceApi[1];

    @Mock
    private InternalApiClient internalApiClient;

    @Mock
    private PrivateDefendantResourceHandler defendantHandler;

    @Mock
    private ApiResponse<DefendantApi> defendantResponse;

    @Mock
    private PrivateDefendantGet defendantGet;

    @Mock
    private PrivateProsecutionCaseResourceHandler prosecutionCaseHandler;

    @Mock
    private ApiResponse<ProsecutionCaseApi> prosecutionCaseApiResponse;

    @Mock
    private PrivateProsecutionCaseGet prosecutionCaseGet;

    @Mock
    private PrivateOffenceResourceHandler offenceHandler;

    @Mock
    private ApiResponse<OffenceApi[]> offenceApiResponse;

    @Mock
    private PrivateOffenceList offenceList;

    @Mock
    private ApiClientService apiClientService;

    @InjectMocks
    private ProsecutionService prosecutionService;

    @BeforeEach
    void setUp() {
        when(apiClientService.getInternalApiClient()).thenReturn(internalApiClient);
    }

    @Test
    @DisplayName("Tests successful get of DefendantApi")
    void getDefendantApiSuccessful() throws URIValidationException, ApiErrorResponseException, ProsecutionServiceException {
        setDefendantApiCall();
        when(internalApiClient.privateDefendant().get(DEFENDANT_URI).execute()).thenReturn(defendantResponse);
        when(defendantResponse.getData()).thenReturn(defendantApi);

        DefendantApi defendantApiResponse = prosecutionService.getDefendant(DEFENDANT_URI);
        assertNotNull(defendantApiResponse);
        assertEquals(defendantApi, defendantApiResponse);
    }

    @Test
    @DisplayName("Tests unsuccessful get of DefendantApi - ApiErrorResponseException thrown by SDK")
    void getDefendantApiUnsuccessfulApiErrorResponseException() throws URIValidationException, ApiErrorResponseException {
        setDefendantApiCall();
        when(internalApiClient.privateDefendant().get(DEFENDANT_URI).execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ProsecutionServiceException.class, () -> prosecutionService.getDefendant(DEFENDANT_URI));
    }

    @Test
    @DisplayName("Tests unsuccessful get of DefendantApi - URIValidationException thrown by SDK")
    void getDefendantApiUnsuccessfulURIValidationException() throws URIValidationException, ApiErrorResponseException {
        setDefendantApiCall();
        when(internalApiClient.privateDefendant().get(DEFENDANT_URI).execute()).thenThrow(URIValidationException.class);

        assertThrows(ProsecutionServiceException.class, () -> prosecutionService.getDefendant(DEFENDANT_URI));
    }

    @Test
    @DisplayName("Tests successful get of ProsecutionCaseApi")
    void getProsecutionCaseApiSuccessful() throws URIValidationException, ApiErrorResponseException, ProsecutionServiceException {
        setProsecutionCaseApiCall();
        when(internalApiClient.privateProsecutionCase().get(PROSECUTION_CASE_URI).execute()).thenReturn(prosecutionCaseApiResponse);
        when(prosecutionCaseApiResponse.getData()).thenReturn(prosecutionCaseApi);

        ProsecutionCaseApi prosecutionCaseApiResponse = prosecutionService.getProsecutionCase(PROSECUTION_CASE_URI);
        assertNotNull(prosecutionCaseApiResponse);
        assertEquals(prosecutionCaseApi, prosecutionCaseApiResponse);
    }

    @Test
    @DisplayName("Tests unsuccessful get of ProsecutionCaseApi - ApiErrorResponseException thrown by SDK")
    void getProsecutionCaseApiUnsuccessfulApiErrorResponseException() throws URIValidationException, ApiErrorResponseException {
        setProsecutionCaseApiCall();
        when(internalApiClient.privateProsecutionCase().get(PROSECUTION_CASE_URI).execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ProsecutionServiceException.class, () -> prosecutionService.getProsecutionCase(PROSECUTION_CASE_URI));
    }

    @Test
    @DisplayName("Tests unsuccessful get of ProsecutionCaseApi - URIValidationException thrown by SDK")
    void getProsecutionCaseApiUnsuccessfulURIValidationException() throws URIValidationException, ApiErrorResponseException {
        setProsecutionCaseApiCall();
        when(internalApiClient.privateProsecutionCase().get(PROSECUTION_CASE_URI).execute()).thenThrow(URIValidationException.class);

        assertThrows(ProsecutionServiceException.class, () -> prosecutionService.getProsecutionCase(PROSECUTION_CASE_URI));
    }

    @Test
    @DisplayName("Tests successful list of OffenceApis")
    void getOffenceApisSuccessful() throws URIValidationException, ApiErrorResponseException, ProsecutionServiceException {
        setOffenceApisCall();
        when(internalApiClient.privateOffence().list(OFFENCES_URI).execute()).thenReturn(offenceApiResponse);
        when(offenceApiResponse.getData()).thenReturn(offenceApis);

        OffenceApi[] offenceApisResponse = prosecutionService.getOffences(OFFENCES_URI);
        assertNotNull(offenceApisResponse);
        assertEquals(offenceApis, offenceApisResponse);
    }

    @Test
    @DisplayName("Tests unsuccessful list of OffenceApis - ApiErrorResponseException thrown by SDK")
    void getOffenceApisUnuccessfulApiErrorResponseException() throws URIValidationException, ApiErrorResponseException {
        setOffenceApisCall();
        when(internalApiClient.privateOffence().list(OFFENCES_URI).execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ProsecutionServiceException.class, () -> prosecutionService.getOffences(OFFENCES_URI));
    }

    @Test
    @DisplayName("Tests unsuccessful list of OffenceApis - ApiErrorResponseException thrown by SDK")
    void getOffenceApisUnuccessfulURIValidationException() throws URIValidationException, ApiErrorResponseException {
        setOffenceApisCall();
        when(internalApiClient.privateOffence().list(OFFENCES_URI).execute()).thenThrow(URIValidationException.class);

        assertThrows(ProsecutionServiceException.class, () -> prosecutionService.getOffences(OFFENCES_URI));
    }

    private void setDefendantApiCall() {
        when(internalApiClient.privateDefendant()).thenReturn(defendantHandler);
        when(defendantHandler.get(DEFENDANT_URI)).thenReturn(defendantGet);
    }

    private void setProsecutionCaseApiCall() {
        when(internalApiClient.privateProsecutionCase()).thenReturn(prosecutionCaseHandler);
        when(prosecutionCaseHandler.get(PROSECUTION_CASE_URI)).thenReturn(prosecutionCaseGet);
    }

    private void setOffenceApisCall() {
        when(internalApiClient.privateOffence()).thenReturn(offenceHandler);
        when(offenceHandler.list(OFFENCES_URI)).thenReturn(offenceList);
    }
}
