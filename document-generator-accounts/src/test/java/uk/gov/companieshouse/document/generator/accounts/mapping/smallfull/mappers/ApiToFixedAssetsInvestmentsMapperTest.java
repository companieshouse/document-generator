package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.fixedassetsinvestments.FixedAssetsInvestmentsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.fixedassetsinvestments.FixedAssetsInvestments;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToFixedAssetsInvestmentsMapperTest {

    private ApiToFixedAssetsInvestmentsMapper apiToFixedAssetsInvestmentsMapper = 
            new ApiToFixedAssetsInvestmentsMapperImpl();

    private static final String DETAILS = "details";

    @Test
    @DisplayName("tests fixed assets investments API values map to fixed assets investments IXBRL model")
    void testPopulatedApiMapsToIxbrlModel() {

        FixedAssetsInvestments fixedAssetsInvestments = apiToFixedAssetsInvestmentsMapper
                .apiToFixedAssetsInvestments(createfixedAssetsInvestmentsApi());

        assertNotNull(fixedAssetsInvestments);
        assertEquals(DETAILS, fixedAssetsInvestments.getDetails());
    }
    
    @Test
    @DisplayName("tests null fixed assets investments API values map to null IXBRL model")
    void testNullApiMapsToIxbrlModel() {

        FixedAssetsInvestments fixedAssetsInvestments = apiToFixedAssetsInvestmentsMapper
                .apiToFixedAssetsInvestments(null);

        assertNull(fixedAssetsInvestments);
    }
    
    private FixedAssetsInvestmentsApi createfixedAssetsInvestmentsApi() {
        FixedAssetsInvestmentsApi fixedAssetsInvestmentsApi = new FixedAssetsInvestmentsApi();
        fixedAssetsInvestmentsApi.setDetails(DETAILS);
        return fixedAssetsInvestmentsApi;
    }
}
