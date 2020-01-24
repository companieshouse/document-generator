package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.directorsreport.StatementsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReportStatements;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiToDirectorsReportMapperTest {

    private ApiToDirectorsReportMapper apiToDirectorsReportMapper = new ApiToDirectorsReportMapperImpl();


    private static final String ADDITIONAL_INFORMATION = "additionalInformation";

    private static final String COMPANY_POLICY_ON_DISABLE_EMPLOYEES = "companyPolicyOnDisabledEmployees";

    private static final String POLITICAL_AND_CHARITABLE_DONATIONS = "politicalAndCharitableDonations";

    private static final String PRINCIPAL_ACTIVITIES = "principalActivities";

    @Test
    @DisplayName("tests that the directors report statements map to directors report statements IXBRL model")
    void testApiToDirectorsReportStatementsMapsCorrectly() {

        DirectorsReportStatements directorsReportStatements =
                apiToDirectorsReportMapper.apiToStatements(createDirectorsReportStatements());

        assertNotNull(directorsReportStatements);
        assertEquals(ADDITIONAL_INFORMATION, directorsReportStatements.getAdditionalInformation());
        assertEquals(COMPANY_POLICY_ON_DISABLE_EMPLOYEES, directorsReportStatements.getCompanyPolicyOnDisabledEmployees());
        assertEquals(PRINCIPAL_ACTIVITIES, directorsReportStatements.getPrincipalActivities());
        assertEquals(POLITICAL_AND_CHARITABLE_DONATIONS, directorsReportStatements.getPoliticalAndCharitableDonations());
    }
    private StatementsApi createDirectorsReportStatements() {

        StatementsApi directorsStatements = new StatementsApi();
        directorsStatements.setAdditionalInformation(ADDITIONAL_INFORMATION);
        directorsStatements.setCompanyPolicyOnDisabledEmployees(COMPANY_POLICY_ON_DISABLE_EMPLOYEES);
        directorsStatements.setPoliticalAndCharitableDonations(POLITICAL_AND_CHARITABLE_DONATIONS);
        directorsStatements.setPrincipalActivities(PRINCIPAL_ACTIVITIES);

        return directorsStatements;
    }

}