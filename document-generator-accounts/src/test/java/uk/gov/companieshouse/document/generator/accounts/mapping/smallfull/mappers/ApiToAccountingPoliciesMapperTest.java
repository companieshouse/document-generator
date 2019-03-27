package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.smallfull.AccountingPoliciesApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.accountingpolicies.AccountingPolicies;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToAccountingPoliciesMapperTest {

    private ApiToAccountingPoliciesMapper apiToAccountingPoliciesMapper = new ApiToAccountingPoliciesMapperImpl();

    private static final String BASIS_OF_PREPARATION = "basisOfPreparation";

    private static final String TURNOVER_POLICY = "turnoverPolicy";

    private static final String TANGIBLE_DEPRECIATION_POLICY = "tangibleDepreciationPolicy";

    private static final String INTANGIBLE_AMORTISATION_POLICY = "intangibleAmortisationPolicy";

    private static final String VALUATION_INFORMATION_POLICY = "valuationInformationPolicy";

    private static final String OTHER_ACCOUNTING_POLICIES = "otherAccountingPolicies";

    @Test
    @DisplayName("tests accounting policies API values map to accounting policies IXBRL model")
    void testApiToCompanyMaps() {

        AccountingPolicies accountingPolicies = apiToAccountingPoliciesMapper.apiToAccountingPolicies(createAccountingPolicies());

        assertNotNull(accountingPolicies);
        assertEquals(BASIS_OF_PREPARATION, accountingPolicies.getBasisOfMeasurementAndPreparation());
        assertEquals(TURNOVER_POLICY, accountingPolicies.getTurnoverPolicy());
        assertEquals(TANGIBLE_DEPRECIATION_POLICY, accountingPolicies.getTangibleFixedAssetsDepreciationPolicy());
        assertEquals(INTANGIBLE_AMORTISATION_POLICY, accountingPolicies.getIntangibleFixedAssetsAmortisationPolicy());
        assertEquals(VALUATION_INFORMATION_POLICY, accountingPolicies.getValuationInformationPolicy());
        assertEquals(OTHER_ACCOUNTING_POLICIES, accountingPolicies.getOtherAccountingPolicy());
    }

    private AccountingPoliciesApi createAccountingPolicies() {

        AccountingPoliciesApi accountingPolicies = new AccountingPoliciesApi();
        accountingPolicies.setBasisOfMeasurementAndPreparation(BASIS_OF_PREPARATION);
        accountingPolicies.setTurnoverPolicy(TURNOVER_POLICY);
        accountingPolicies.setTangibleFixedAssetsDepreciationPolicy(TANGIBLE_DEPRECIATION_POLICY);
        accountingPolicies.setIntangibleFixedAssetsAmortisationPolicy(INTANGIBLE_AMORTISATION_POLICY);
        accountingPolicies.setValuationInformationAndPolicy(VALUATION_INFORMATION_POLICY);
        accountingPolicies.setOtherAccountingPolicy(OTHER_ACCOUNTING_POLICIES);

        return accountingPolicies;
    }
}
