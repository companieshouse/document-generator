package uk.gov.companieshouse.document.generator.company.report.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
import uk.gov.companieshouse.api.handler.charges.ChargesResourceHandler;
import uk.gov.companieshouse.api.handler.charges.request.ChargesGet;
import uk.gov.companieshouse.api.handler.exception.URIValidationException;
import uk.gov.companieshouse.api.model.ApiResponse;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.document.generator.company.report.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChargesServiceTest {

    @InjectMocks
    private  ChargesService chargesService;

    @Mock
    private ApiClient mockApiClient;

    @Mock
    private ChargesResourceHandler mockChargesResourceHandler;

    @Mock
    private ChargesGet mockChargesGet;

    @Mock
    private  CompanyReportApiClientService mockCompanyReportApiClientService;

    @Mock
    private ApiResponse<ChargesApi> responseWithData;

    private static final String COMPANY_NUMBER = "00000000";
    private static final String CHARGES_URI = "/company/00000000/charges";


    @BeforeEach
    void init() {
        when(mockCompanyReportApiClientService.getApiClient()).thenReturn(mockApiClient);
        when(mockApiClient.charges()).thenReturn(mockChargesResourceHandler);
        when(mockChargesResourceHandler.get(CHARGES_URI)).thenReturn(mockChargesGet);
    }

    @Test
    @DisplayName("Test get charges api response is not null")
    void testGetChargesSuccessful() throws Exception {

        when(mockChargesGet.execute()).thenReturn(responseWithData);
        when(responseWithData.getData()).thenReturn(createChargesApi());

        ChargesApi chargesApi = chargesService.getCharges(COMPANY_NUMBER);

        assertNotNull(chargesApi);
        assertEquals(2, chargesApi.getItems().size());
    }

    @Test
    @DisplayName("Test get charges api throws service exception with api error exception")
    void testGetChargesApiErrorResponse() throws Exception {

        when(mockChargesGet.execute()).thenThrow(ApiErrorResponseException.class);

        assertThrows(ServiceException.class, () ->
                chargesService.getCharges(COMPANY_NUMBER));
    }

    @Test
    @DisplayName("Test get charges api throws service exception with uri validation exception")
    void testGetChargesURIValidation() throws Exception {

        when(mockChargesGet.execute()).thenThrow(URIValidationException.class);

        assertThrows(ServiceException.class, () ->
                chargesService.getCharges(COMPANY_NUMBER));
    }

    private ChargesApi createChargesApi() {

        ChargesApi chargesApi = new ChargesApi();

        chargesApi.setEtag("etag");
        chargesApi.setPartSatisfiedCount(1L);
        chargesApi.setSatisfiedCount(1L);
        chargesApi.setTotalCount(2L);

        List<ChargeApi> items = new ArrayList<>();
        ChargeApi chargeApi = new ChargeApi();

        items.add(chargeApi);

        chargesApi.setItems(items);

        return chargesApi;
    }
}
