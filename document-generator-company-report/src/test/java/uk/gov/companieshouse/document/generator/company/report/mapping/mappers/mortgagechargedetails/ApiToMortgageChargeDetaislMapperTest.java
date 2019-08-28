package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.ChargesApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.MortgageChargeDetails;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToMortgageChargeDetaislMapperTest {

    @Mock
    private ApiToChargesMapper mockApiToChargesMapper;

    @InjectMocks
    private ApiToMortgageChargeDetailsMapper apiToMortgageChargeDetailsMapper = new ApiToMortgageChargeDetailsMapperImpl();

    @Test
    @DisplayName("Test charges api data maps to charges model")
    void testApiToChargesMaps() {

        ChargesApi chargesApi = createChargesApi();

        List<Charge> chargeList = createChargeList();

        when(mockApiToChargesMapper.apiToCharge(chargesApi.getItems())).thenReturn(chargeList);

        MortgageChargeDetails mortgageChargeDetails = apiToMortgageChargeDetailsMapper.apiToMortgageChargeDetails(chargesApi);

        assertNotNull(mortgageChargeDetails);
        assertEquals(1L, mortgageChargeDetails.getTotalCount().longValue());
        assertEquals(1L, mortgageChargeDetails.getSatisfiedCount().longValue());
        assertEquals(1L, mortgageChargeDetails.getPartSatisfiedCount().longValue());
    }

    private ChargesApi createChargesApi() {

        ChargesApi chargesApi = new ChargesApi();

        chargesApi.setTotalCount(1L);
        chargesApi.setSatisfiedCount(1L);
        chargesApi.setPartSatisfiedCount(1L);

        return chargesApi;
    }

    private List<Charge> createChargeList() {
        List<Charge> charges = new ArrayList<>();
        Charge charge1 = new Charge();
        charge1.setStatus("status1");

        Charge charge2 = new Charge();
        charge2.setStatus("status2");

        charges.add(charge1);
        charges.add(charge2);

        return charges;
    }
}
