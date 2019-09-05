package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.ChargeApi;
import uk.gov.companieshouse.api.model.charges.ParticularsApi;
import uk.gov.companieshouse.api.model.charges.ParticularsTypeApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Charge;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.Particulars;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToParticularsMapperTest {

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @InjectMocks
    private ApiToParticularsMapper apiToParticularsMapper = new ApiToParticularsMapperImpl();

    private static final String PARTICULARS_DESCRIPTIONS = "particulars description";
    private static final String API_ENUMERATION_DESCRIPTION = "api enumeration description";
    private static final String CHARGOR_ACTING = "chargor_acting_as_bare_trustee";
    private static final String CONTAINS_FIXED = "contains_fixed_charge";
    private static final String CONTAINS_FLOATING = "contains_floating_charge";
    private static final String CONTAINS_NEGATIVE = "contains_negative_pledge";
    private static final String FLOATING_CHARGE = "floating_charge_covers_all";

    @Test
    @DisplayName("tests ParticularsApi data maps to Particulars model")
    void testParticularsMaps() {

        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(), anyString(), anyString(), anyMap()))
            .thenReturn(API_ENUMERATION_DESCRIPTION);

        Particulars particulars = apiToParticularsMapper.apiToParticularsMapper(createParticularsApi());

        assertNotNull(particulars);
        assertEquals(API_ENUMERATION_DESCRIPTION, particulars.getExtraParticularStatements().get(CHARGOR_ACTING));
        assertEquals(API_ENUMERATION_DESCRIPTION, particulars.getExtraParticularStatements().get(CONTAINS_FIXED));
        assertEquals(API_ENUMERATION_DESCRIPTION, particulars.getExtraParticularStatements().get(CONTAINS_FLOATING));
        assertEquals(API_ENUMERATION_DESCRIPTION, particulars.getExtraParticularStatements().get(CONTAINS_NEGATIVE));
        assertEquals(API_ENUMERATION_DESCRIPTION, particulars.getExtraParticularStatements().get(FLOATING_CHARGE));
        assertEquals(API_ENUMERATION_DESCRIPTION, particulars.getType());
        assertEquals(PARTICULARS_DESCRIPTIONS, particulars.getDescription());
    }

    @Test
    @DisplayName("test Null ParticularsApi returns Null")
    void TestNull() {

       ParticularsApi particularsApi = null;
       assertNull(apiToParticularsMapper.apiToParticularsMapper(particularsApi));
    }

    private ParticularsApi createParticularsApi() {

        ParticularsApi particularsApi = new ParticularsApi();
        particularsApi.setChargorActingAsBareTrustee(true);
        particularsApi.setContainsFixedCharge(true);
        particularsApi.setContainsFloatingCharge(true);
        particularsApi.setContainsNegativePledge(true);
        particularsApi.setFloatingChargeCoversAll(true);
        particularsApi.setType(ParticularsTypeApi.BRIEF_DESCRIPTION);
        particularsApi.setDescription(PARTICULARS_DESCRIPTIONS);

        return particularsApi;

    }
}
