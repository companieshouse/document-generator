package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.MortgageChargeDetails;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToMortgageChargeDetailsMapperTest {

    @Mock
    private ApiToChargesMapper mockApiToChargesMapper;

    @InjectMocks
    private ApiToMortgageChargeDetailsMapper apiToMortgageChargeDetailsMapper = new ApiToMortgageChargeDetailsMapperImpl();

    @Test
    @DisplayName("Test ChargesApi data maps to charges model")
    void testChargesMaps() {

        when(mockApiToChargesMapper.apiToCharge(anyList())).thenReturn(new ArrayList<>());

        MortgageChargeDetails mortgageChargeDetails = apiToMortgageChargeDetailsMapper.apiToMortgageChargeDetails(createChargesApi());

        assertNotNull(mortgageChargeDetails);
        assertEquals(5L, mortgageChargeDetails.getTotalCount().longValue());
        assertEquals(1L, mortgageChargeDetails.getSatisfiedCount().longValue());
        assertEquals(2L, mortgageChargeDetails.getPartSatisfiedCount().longValue());
        assertEquals(2L, mortgageChargeDetails.getOutstanding().longValue());
    }

    private ChargesApi createChargesApi() {

        ChargesApi chargesApi = new ChargesApi();
        chargesApi.setPartSatisfiedCount(2L);
        chargesApi.setSatisfiedCount(1L);
        chargesApi.setTotalCount(5L);
        chargesApi.setItems(new ArrayList<>());

        return chargesApi;
    }
}
