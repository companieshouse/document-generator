package uk.gov.companieshouse.document.generator.prosecution.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseStatus;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToProsecutionCaseMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToProsecutionCaseMapperImpl;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToProsecutionCaseMapperTest {

    private ApiToProsecutionCaseMapper apiToProsecutionCaseMapper =
            new ApiToProsecutionCaseMapperImpl();

    private static final String KIND = "kind";
    private static final ProsecutionCaseStatus STATUS = ProsecutionCaseStatus.ACCEPTED;
    private static final String COMPANY_INCORPORATION_NUMBER = "companyIncorporationNumber";
    private static final String COMPANY_NAME = "companyName";
    private static final String COMPLIANCE_CASE_ID = "complianceCaseId";
    private static final String COMPLIANCE_USER_ID = "complianceUserId";
    private static final LocalDateTime SUBMITTED_AT = LocalDateTime.now();
    private static final Map<String, String> LINKS = new HashMap<>();

    @Test
    @DisplayName("Tests prosecution case API values map to prosecution case DocGen model")
    void testApiToProsecutionCaseMaps() {
        ProsecutionCase prosecutionCase =
                apiToProsecutionCaseMapper.apiToProsecutionCase(createProsecutionCase());

        assertNotNull(prosecutionCase);
        assertEquals(KIND, prosecutionCase.getKind());
        assertEquals(
                uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCaseStatus.ACCEPTED,
                prosecutionCase.getStatus());
        assertEquals(COMPANY_INCORPORATION_NUMBER, prosecutionCase.getCompanyIncorporationNumber());
        assertEquals(COMPANY_NAME, prosecutionCase.getCompanyName());
        assertEquals(COMPLIANCE_CASE_ID, prosecutionCase.getComplianceCaseId());
        assertEquals(COMPLIANCE_USER_ID, prosecutionCase.getComplianceUserId());
        assertEquals(SUBMITTED_AT, prosecutionCase.getSubmittedAt());
        assertEquals(LINKS, prosecutionCase.getLinks());
    }

    private ProsecutionCaseApi createProsecutionCase() {
        ProsecutionCaseApi prosecutionCase = new ProsecutionCaseApi();
        prosecutionCase.setKind(KIND);
        prosecutionCase.setStatus(STATUS);
        prosecutionCase.setCompanyIncorporationNumber(COMPANY_INCORPORATION_NUMBER);
        prosecutionCase.setCompanyName(COMPANY_NAME);
        prosecutionCase.setComplianceCaseId(COMPLIANCE_CASE_ID);
        prosecutionCase.setComplianceUserId(COMPLIANCE_USER_ID);
        prosecutionCase.setSubmittedAt(SUBMITTED_AT);
        prosecutionCase.setLinks(LINKS);

        return prosecutionCase;
    }
}
