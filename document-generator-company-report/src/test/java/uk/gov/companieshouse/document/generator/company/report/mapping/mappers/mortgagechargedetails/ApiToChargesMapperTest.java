package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.api.model.charges.ClassificationApi;
import uk.gov.companieshouse.api.model.charges.ParticularsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToChargesMapperTest {

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @InjectMocks
    private ApiToChargesMapper apiToChargesMapper = new ApiToChargesMapperImpl();

    private static final String DESCRIPTION = "description";
    private static final String STATUS = "status";
    private static final String PARTICULARS = "particulars";
    private static final LocalDate CREATED_ON = LocalDate.of(2016,01,01);
    private static final String CREATED_ON_CONVERTED = "1 January 2016";
    private static final LocalDate DELIVERED_ON = LocalDate.of(2017,02,02);
    private static final String DELIVERED_ON_CONVERTED = "2 February 2017";
    private static final LocalDate SATISFIED_ON = LocalDate.of(2018,03,03);
    private static final String SATISFIED_ON_CONVERTED = "3 March 2018";

    @BeforeEach
    void init() {
        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(), anyString(),
            anyString(), any())).thenReturn(STATUS);
    }

    @Test
    @DisplayName("tests multiple Charge data maps to Charge model")
    void testMultipleChargesMaps() {
        List<ChargeApi> chargeList = createChargeList();

        List<Charge> charges = apiToChargesMapper.apiToCharge(chargeList);
        assertNotNull(charges);
        assertEquals(DESCRIPTION, charges.get(0).getDescription());
        assertEquals(STATUS, charges.get(0).getStatus());
        assertEquals(PARTICULARS, charges.get(0).getParticularsDescription());
        assertEquals(CREATED_ON_CONVERTED.toString(), charges.get(0).getCreatedDate());
        assertEquals(DELIVERED_ON_CONVERTED, charges.get(0).getDelivered());
        assertEquals(SATISFIED_ON_CONVERTED, charges.get(0).getSatisfiedOn());

        assertEquals(DESCRIPTION, charges.get(1).getDescription());
        assertEquals(STATUS, charges.get(1).getStatus());
        assertEquals(PARTICULARS, charges.get(1).getParticularsDescription());
        assertEquals(CREATED_ON_CONVERTED.toString(), charges.get(1).getCreatedDate());
        assertEquals(DELIVERED_ON_CONVERTED, charges.get(1).getDelivered());
        assertEquals(SATISFIED_ON_CONVERTED, charges.get(1).getSatisfiedOn());
    }

    @Test
    @DisplayName("tests single Charge data maps to Charge model")
    void testSingleChargeMaps() {
        ChargeApi chargeApi = createChargeApi();

        Charge charge = apiToChargesMapper.apiToCharge(chargeApi);

        assertNotNull(charge);
        assertEquals(DESCRIPTION, charge.getDescription());
        assertEquals(STATUS, charge.getStatus());
        assertEquals(PARTICULARS, charge.getParticularsDescription());
        assertEquals(CREATED_ON_CONVERTED, charge.getCreatedDate());
        assertEquals(DELIVERED_ON_CONVERTED, charge.getDelivered());
        assertEquals(SATISFIED_ON_CONVERTED, charge.getSatisfiedOn());
    }

    private List<ChargeApi> createChargeList() {
        List<ChargeApi> chargeApiList = new ArrayList<>();

        ChargeApi chargeApi1 = createChargeApi();
        ChargeApi chargeApi2 = createChargeApi();

        chargeApiList.add(chargeApi1);
        chargeApiList.add(chargeApi2);

        return chargeApiList;
    }

    private ChargeApi createChargeApi() {
        ChargeApi chargeApi = new ChargeApi();

        ClassificationApi classificationApi = new ClassificationApi();
        classificationApi.setDescription(DESCRIPTION);
        chargeApi.setClassification(classificationApi);

        chargeApi.setCreatedOn(CREATED_ON);
        chargeApi.setDeliveredOn(DELIVERED_ON);
        chargeApi.setSatisfiedOn(SATISFIED_ON);
        chargeApi.setStatus(STATUS);

        ParticularsApi particularsApi = new ParticularsApi();
        particularsApi.setDescription(PARTICULARS);
        chargeApi.setParticulars(particularsApi);

        return chargeApi;
    }
}
