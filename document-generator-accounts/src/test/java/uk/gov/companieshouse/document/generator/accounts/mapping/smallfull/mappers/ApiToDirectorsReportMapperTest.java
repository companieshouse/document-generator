package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.accounts.directorsreport.ApprovalApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.SecretaryApi;
import uk.gov.companieshouse.api.model.accounts.directorsreport.StatementsApi;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Approval;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.DirectorsReportStatements;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.directorsreport.Secretary;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiToDirectorsReportMapperTest {

    private ApiToDirectorsReportMapper apiToDirectorsReportMapper = new ApiToDirectorsReportMapperImpl();


    private static final String ADDITIONAL_INFORMATION = "additionalInformation";

    private static final String COMPANY_POLICY_ON_DISABLE_EMPLOYEES = "companyPolicyOnDisabledEmployees";

    private static final String POLITICAL_AND_CHARITABLE_DONATIONS = "politicalAndCharitableDonations";

    private static final String PRINCIPAL_ACTIVITIES = "principalActivities";

    private static final String SECRETARY_NAME = "secretaryName";

    private static final String APPROVAL_NAME = "approvalName";

    private static final LocalDate DATE = LocalDate.of(2019, 6, 1);


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

    @Test
    @DisplayName("tests that the directors report secretary map to directors report statements IXBRL model")
    void testApiToSecretaryMapsCorrectly() {

        Secretary secretary =
                apiToDirectorsReportMapper.apiToSecretary(createSecretary());

        assertNotNull(secretary);
       }

    @Test
    @DisplayName("tests that the directors report approval map to directors report statements IXBRL model")
    void testApiToApprovalMapsCorrectly() {

        Approval approval =
                apiToDirectorsReportMapper.apiToApproval(createApproval());

        assertNotNull(approval);
    }

    private ApprovalApi createApproval() {

        ApprovalApi approval =  new ApprovalApi();

        approval.setName("approvalName");
        approval.setDate( LocalDate.of(2019, 6, 1));

        return approval;
    }


    private SecretaryApi createSecretary() {

        SecretaryApi secretary = new SecretaryApi();
        secretary.setName(SECRETARY_NAME);

        return secretary;
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