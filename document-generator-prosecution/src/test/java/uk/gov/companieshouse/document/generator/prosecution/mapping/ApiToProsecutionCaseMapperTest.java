package uk.gov.companieshouse.document.generator.prosecution.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseApi;
import uk.gov.companieshouse.api.model.prosecution.prosecutioncase.ProsecutionCaseStatusApi;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToProsecutionCaseMapper;
import uk.gov.companieshouse.document.generator.prosecution.mapping.mappers.ApiToProsecutionCaseMapperImpl;
import uk.gov.companieshouse.document.generator.prosecution.mapping.model.prosecutioncase.ProsecutionCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToProsecutionCaseMapperTest {

    private ApiToProsecutionCaseMapper apiToProsecutionCaseMapper =
            new ApiToProsecutionCaseMapperImpl();

    private static final ProsecutionCaseStatusApi STATUS = ProsecutionCaseStatusApi.ACCEPTED;
    private static final String COMPANY_INCORPORATION_NUMBER = "companyIncorporationNumber";
    private static final String COMPANY_NAME = "companyName";

    @Test
    @DisplayName("Tests prosecution case API values map to prosecution case DocGen model")
    void testApiToProsecutionCaseMaps() {
        ProsecutionCase prosecutionCase =
                apiToProsecutionCaseMapper.apiToProsecutionCase(createProsecutionCase());

        assertNotNull(prosecutionCase);
        assertEquals(COMPANY_INCORPORATION_NUMBER, prosecutionCase.getCompanyIncorporationNumber());
        assertEquals(COMPANY_NAME, prosecutionCase.getCompanyName());
    }

    private ProsecutionCaseApi createProsecutionCase() {
        ProsecutionCaseApi prosecutionCase = new ProsecutionCaseApi();
        prosecutionCase.setStatus(STATUS);
        prosecutionCase.setCompanyIncorporationNumber(COMPANY_INCORPORATION_NUMBER);
        prosecutionCase.setCompanyName(COMPANY_NAME);

        return prosecutionCase;
    }
}
