package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.AssetsCeasedReleasedApi;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.api.model.charges.ClassificationApi;
import uk.gov.companieshouse.api.model.charges.ParticularsApi;
import uk.gov.companieshouse.api.model.charges.SecuredDetailsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Particulars;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.SecuredDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToChargesMapperTest {

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @Mock
    private ApiToParticularsMapper mockApiToParticularsMapper;

    @Mock
    private ApiToPersonsEntitledMapper mockApiToPersonsEntitledMapper;

    @Mock
    private ApiToSecuredDetailsMapper mockApiToSecuredDetailsMapper;

    @InjectMocks
    private ApiToChargesMapper apiToChargesMapper = new ApiToChargesMapperImpl();

    private static final String DESCRIPTION_WITH_ACQUIRED_ON = "Charge code 00112233";
    private static final String DESCRIPTION_WITHOUT_ACQUIRED_ON = "classification description";
    private static final String STATUS = "status";
    private static final LocalDate DATE = LocalDate.of(2019,01,01);
    private static final String DATE_CONVERTED = "1 January 2019";
    private static final String CHARGE_CODE = "00112233";
    private static final String CLASSIFICATION_DESCRIPTION = "classification description";
    private static final String API_ENUMERATION_DESCRIPTION = "api enumeration description";
    private static final Boolean WITH_ACQUIRED_ON = true;
    private static final Boolean WITHOUT_ACQUIRED_ON = false;

    @BeforeEach
    public void init(TestInfo testInfo) {

        if(testInfo.getTags().contains("SkipBeforeEach")) {
            return;
        }

        when(mockApiToParticularsMapper.apiToParticularsMapper(any(ParticularsApi.class)))
            .thenReturn(new Particulars());
        when(mockApiToPersonsEntitledMapper.apiToPersonsEntitledMapper(anyList()))
            .thenReturn(new ArrayList<>());
        when(mockApiToSecuredDetailsMapper.apiToSecuredDetailsMapper(any(SecuredDetailsApi.class)))
            .thenReturn(new SecuredDetails());
        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(), anyString(), anyString(), anyMap()))
            .thenReturn(API_ENUMERATION_DESCRIPTION);
    }

    @Test
    @DisplayName("tests Charge data maps to Charge model with acquired on date")
    void testChargeMapsWithAcquiredOnDateSet() {

        List<Charge> charges = apiToChargesMapper.apiToCharge(createChargeApi(WITH_ACQUIRED_ON));
        assertTest(charges, WITH_ACQUIRED_ON);
    }

    @Test
    @DisplayName("tests Charge data maps to Charge model without acquired on date")
    void testChargeMapsWithoutAcquiredOnDateSet() {

        List<Charge> charges = apiToChargesMapper.apiToCharge(createChargeApi(WITHOUT_ACQUIRED_ON));
        assertTest(charges, WITHOUT_ACQUIRED_ON);
    }

    @Test
    @Tag("SkipBeforeEach")
    @DisplayName("test Null ChargeApi returns Null")
    void TestNull() {

        List<ChargeApi> chargeApi = null;
        assertNull(apiToChargesMapper.apiToCharge(chargeApi));
    }

    private void assertTest(List<Charge> charges, boolean acquiredOnRequired) {
        assertNotNull(charges);
        assertNotNull(charges.get(0).getParticulars());
        assertNotNull(charges.get(0).getPersonsEntitled());
        assertNotNull(charges.get(0).getSecuredDetails());
        assertEquals(DATE_CONVERTED, charges.get(0).getCreated());
        assertEquals(DATE_CONVERTED, charges.get(0).getDelivered());
        assertEquals(DATE_CONVERTED, charges.get(0).getSatisfiedOn());
        if (acquiredOnRequired == WITH_ACQUIRED_ON) {
            assertEquals(DESCRIPTION_WITH_ACQUIRED_ON, charges.get(0).getChargeDescription());
        } else {
            assertEquals(DESCRIPTION_WITHOUT_ACQUIRED_ON, charges.get(0).getChargeDescription());
        }
        assertEquals(API_ENUMERATION_DESCRIPTION, charges.get(0).getStatus());
        assertEquals(true, charges.get(0).isMoreThanFourPersonsEntitled());
        assertEquals(API_ENUMERATION_DESCRIPTION, charges.get(0).getAssetsCeasedReleased());
    }


    private List<ChargeApi> createChargeApi(boolean acquiredOnRequired) {

        List<ChargeApi> chargeApiList = new ArrayList<>();
        ChargeApi chargeApi = new ChargeApi();
        chargeApi.setParticulars(new ParticularsApi());
        chargeApi.setSecuredDetails(new SecuredDetailsApi());
        chargeApi.setPersonsEntitled(new ArrayList<>());
        chargeApi.setDeliveredOn(DATE);
        chargeApi.setCreatedOn(DATE);
        chargeApi.setSatisfiedOn(DATE);
        if(acquiredOnRequired == WITH_ACQUIRED_ON) {
            chargeApi.setAcquiredOn(DATE);
            chargeApi.setChargeCode(CHARGE_CODE);
        } else {
            ClassificationApi classificationApi = new ClassificationApi();
            classificationApi.setDescription(CLASSIFICATION_DESCRIPTION);
            chargeApi.setClassification(classificationApi);
        }
        chargeApi.setAssetsCeasedReleased(AssetsCeasedReleasedApi.PART_PROPERTY_RELEASED);
        chargeApi.setStatus(STATUS);
        chargeApi.setMoreThanFourPersonsEntitled(true);

        chargeApiList.add(chargeApi);

        return chargeApiList;
    }
}

