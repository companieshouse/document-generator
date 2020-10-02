package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.handler.smallfull.financialcommitments.FinancialCommitmentsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.FinancialCommitments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiToFinancialCommitmentsMapperImplTest {

    private ApiToFinancialCommitmentsMapper apiToFinancialCommitmentsMapper =
            new ApiToFinancialCommitmentsMapperImpl();

    private static final String DETAILS = "details";

    @Test
    @DisplayName("tests off balance sheet arrangements API values map to current investments IXBRL model")
    void testPopulatedApiMapsToIxbrlModel() {

        FinancialCommitments financialCommitments = apiToFinancialCommitmentsMapper
                .apiToFinancialCommitments(createOffBalanceSheetApi());

        assertNotNull(financialCommitments);
        assertEquals(DETAILS, financialCommitments.getDetails());
    }

    @Test
    @DisplayName("tests null off balance sheet arrangements API values map to null IXBRL model")
    void testNullApiMapsToIxbrlModel() {

        FinancialCommitments financialCommitments = apiToFinancialCommitmentsMapper
                .apiToFinancialCommitments(null);

        assertNull(financialCommitments);
    }

    private FinancialCommitmentsApi createOffBalanceSheetApi() {
        FinancialCommitmentsApi financialCommitmentsApi = new FinancialCommitmentsApi();
        financialCommitmentsApi.setDetails(DETAILS);
        return financialCommitmentsApi;
    }
}