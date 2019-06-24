package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.currentassetsinvestments.CurrentAssetsInvestmentsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.currentassetsinvestments.CurrentAssetsInvestments;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCurrentAssetsInvestmentsTest {

    private ApiToCurrentAssetsInvestmentsMapper apiToCurrentAssetsInvestmentsMapper =
            new ApiToCurrentAssetsInvestmentsMapperImpl();

    private static final String DETAILS = "details";

    @Test
    @DisplayName("tests current assets investments API values map to current investments IXBRL model")
    void testPopulatedApiMapsToIxbrlModel() {

        CurrentAssetsInvestments currentAssetsInvestments = apiToCurrentAssetsInvestmentsMapper
                .apiToCurrentAssetsInvestments(createCurrentAssetsInvestmentsApi());

        assertNotNull(currentAssetsInvestments);
        assertEquals(DETAILS, currentAssetsInvestments.getDetails());
    }

    @Test
    @DisplayName("tests null current assets investments API values map to null IXBRL model")
    void testNullApiMapsToIxbrlModel() {

        CurrentAssetsInvestments currentAssetsInvestments = apiToCurrentAssetsInvestmentsMapper
                .apiToCurrentAssetsInvestments(null);

        assertNull(currentAssetsInvestments);
    }

    private CurrentAssetsInvestmentsApi createCurrentAssetsInvestmentsApi() {
        CurrentAssetsInvestmentsApi currentAssetsInvestmentsApi = new CurrentAssetsInvestmentsApi();
        currentAssetsInvestmentsApi.setDetails(DETAILS);
        return currentAssetsInvestmentsApi;
    }
}
