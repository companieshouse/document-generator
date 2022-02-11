package uk.gov.companieshouse.document.generator.company.report.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.ApiClient;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.api.model.charges.ChargesApi;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChargesServiceTest {

    private static final String COMPANY_NUMBER = "00000000";
    private static final String CHARGES_URI = "/company/00000000/charges";

    @InjectMocks
    private ChargesService chargesService;

    @Mock
    private ApiClient mockApiClient;

    @Mock
    private  CompanyReportApiClientService mockCompanyReportApiClientService;

    @Mock
    private PageRetrieverService<ChargesApi> pageRetrieverService;

    @Test
    @DisplayName("Test get charges api response is not null")
    void testGetChargesSuccessful() throws Exception {

        // Given
        when(mockCompanyReportApiClientService.getApiClient()).thenReturn(mockApiClient);
        when(pageRetrieverService.retrieveAllPages(eq(chargesService), eq(CHARGES_URI), eq(mockApiClient), anyInt()))
                .thenReturn(createChargesApi());

        // When
        final ChargesApi chargesApi =  chargesService.getCharges(COMPANY_NUMBER);

        // Then
        assertNotNull(chargesApi);
        assertEquals(2L, (long) chargesApi.getSatisfiedCount());
    }

    private ChargesApi createChargesApi() {

        ChargesApi chargesApi = new ChargesApi();

        chargesApi.setEtag("etag");
        chargesApi.setPartSatisfiedCount(1L);
        chargesApi.setSatisfiedCount(2L);
        chargesApi.setTotalCount(2L);

        List<ChargeApi> items = new ArrayList<>();
        ChargeApi chargeApi = new ChargeApi();

        items.add(chargeApi);

        chargesApi.setItems(items);

        return chargesApi;
    }
}
