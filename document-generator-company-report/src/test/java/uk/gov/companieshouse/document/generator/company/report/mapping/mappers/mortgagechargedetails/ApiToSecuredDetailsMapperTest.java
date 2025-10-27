package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.mortgagechargedetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.charges.SecuredDetailsApi;
import uk.gov.companieshouse.api.model.charges.SecuredDetailsTypeApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.mortgagechargedetails.items.SecuredDetails;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToSecuredDetailsMapperTest {

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @InjectMocks
    private ApiToSecuredDetailsMapper apiToSecuredDetailsMapper = new ApiToSecuredDetailsMapperImpl();

    private static final String API_ENUMERATION_DESCRIPTION = "api enumeration description";
    private static final String SECURED_DETAILS_DESCRIPTION = "secured details description";

    @Test
    @DisplayName("tests SecuredDetailsApi data maps to SecuredDetails model")
    void testSecuredDetailsMaps() {

        when(mockRetrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(), anyString(), anyString(), anyMap()))
            .thenReturn(API_ENUMERATION_DESCRIPTION);

        SecuredDetails securedDetails = apiToSecuredDetailsMapper.apiToSecuredDetailsMapper(createSecuredDetailsApi());

        assertNotNull(securedDetails);
        assertEquals(API_ENUMERATION_DESCRIPTION, securedDetails.getType());
        assertEquals(SECURED_DETAILS_DESCRIPTION, securedDetails.getDescription());
    }

    @Test
    @DisplayName("test Null SecuredDetails returns Null")
    void TestNull() {

        SecuredDetailsApi securedDetailsApi = null;
        assertNull(apiToSecuredDetailsMapper.apiToSecuredDetailsMapper(securedDetailsApi));
    }

    private SecuredDetailsApi createSecuredDetailsApi() {

        SecuredDetailsApi securedDetailsApi = new SecuredDetailsApi();
        securedDetailsApi.setType(SecuredDetailsTypeApi.AMOUNT_SECURED);
        securedDetailsApi.setDescription("secured details description");

        return securedDetailsApi;
    }

}
