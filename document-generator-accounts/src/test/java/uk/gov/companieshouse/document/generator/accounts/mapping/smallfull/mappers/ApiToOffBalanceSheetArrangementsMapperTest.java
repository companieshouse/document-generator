package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.offBalanceSheet.OffBalanceSheetApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.offbalancesheetarrangements.OffBalanceSheetArrangements;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToOffBalanceSheetArrangementsMapperTest {

    private ApiToOffBalanceSheetArrangementsMapper apiToOffBalanceSheetArrangementsMapper =
            new ApiToOffBalanceSheetArrangementsMapperImpl();

    private static final String DETAILS = "details";

    @Test
    @DisplayName("tests off balance sheet arrangements API values map to current investments IXBRL model")
    void testPopulatedApiMapsToIxbrlModel() {

        OffBalanceSheetArrangements offBalanceSheetArrangements = apiToOffBalanceSheetArrangementsMapper
                .apiToOffBalanceSheetArrangements(createOffBalanceSheetApi());

        assertNotNull(offBalanceSheetArrangements);
        assertEquals(DETAILS, offBalanceSheetArrangements.getDetails());
    }

    @Test
    @DisplayName("tests null off balance sheet arrangements API values map to null IXBRL model")
    void testNullApiMapsToIxbrlModel() {

        OffBalanceSheetArrangements offBalanceSheetArrangements = apiToOffBalanceSheetArrangementsMapper
                .apiToOffBalanceSheetArrangements(null);

        assertNull(offBalanceSheetArrangements);
    }

    private OffBalanceSheetApi createOffBalanceSheetApi() {
        OffBalanceSheetApi offBalanceSheetApi = new OffBalanceSheetApi();
        offBalanceSheetApi.setDetails(DETAILS);
        return offBalanceSheetApi;
    }
}
