package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.insolvency.CaseApi;
import uk.gov.companieshouse.api.model.insolvency.CaseTypeApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items.InsolvencyCase;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCaseMapperTest {

    @Mock
    private ApiToPractitionerMapper mockApiToPractitionerMapper;

    @Mock
    private ApiToInsolvencyDateMapper mockApiToInsolvencyDateMapper;

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @InjectMocks
    private ApiToCaseMapper apiToCaseMapper = new ApiToCaseMapperImpl();

    private String ENUMERATION_DESCRIPTION = "enumeration-description";

    @Test
    @DisplayName("tests case api data maps to case model")
    void testApiToCaseMaps() {

        when(mockApiToInsolvencyDateMapper.apiToInsolvencyDateMapper(anyList())).thenReturn(new ArrayList<>());
        when(mockApiToPractitionerMapper.apiToPractitionerMapper(anyList())).thenReturn(new ArrayList<>());
        when(mockRetrieveApiEnumerationDescription
            .getApiEnumerationDescription(anyString(), anyString(), anyString(), anyMap()))
            .thenReturn(ENUMERATION_DESCRIPTION);

        List<InsolvencyCase> insolvencyCase = apiToCaseMapper.apiToCaseMapper(createCasesApi());

        assertNotNull(insolvencyCase);
        assertNotNull(insolvencyCase.get(0).getDates());
        assertNotNull(insolvencyCase.get(0).getPractitioners());
        assertEquals(insolvencyCase.get(0).getType(), ENUMERATION_DESCRIPTION);
    }

    private List<CaseApi> createCasesApi() {

        List<CaseApi> caseApiList = new ArrayList<>();
        CaseApi caseApi = new CaseApi();
        caseApi.setNumber(1L);
        caseApi.setType(CaseTypeApi.ADMINISTRATION_ORDER);
        caseApi.setDates(new ArrayList<>());
        caseApi.setPractitioners(new ArrayList<>());

        caseApiList.add(caseApi);

        return caseApiList;
    }
}
