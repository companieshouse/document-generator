package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
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
import uk.gov.companieshouse.api.model.insolvency.InsolvencyApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.Insolvency;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToInsolvencyMapperTest {

    @Mock
    private ApiToCaseMapper mockApiToCaseMapper;

    @InjectMocks
    private ApiToInsolvencyMapper apiToInsolvencyMapper = new ApiToInsolvencyMapperImpl();

    @Test
    @DisplayName("tests Insolvency api data maps to Insolvency model")
    void testApiToInsolvencyMaps() {


        when(mockApiToCaseMapper.apiToCaseMapper(anyList())).thenReturn(new ArrayList<>());
        Insolvency insolvency = apiToInsolvencyMapper.apiToInsolvencyMapper(createInsolvencyApi());

        assertNotNull(insolvency);
        assertEquals(insolvency.getTotalInsolvencyCases(), Integer.valueOf(2));
    }

    private InsolvencyApi createInsolvencyApi() {

        InsolvencyApi insolvencyApi = new InsolvencyApi();
        insolvencyApi.setCases(createCases());
        insolvencyApi.setStatus(new ArrayList<>());

        return insolvencyApi;
    }

    private List<CaseApi> createCases() {

        List<CaseApi> caseApiList = new ArrayList<>();
        Long number = 0L;

        for(int i = 0; i < 2; i++) {

            CaseApi caseApi = new CaseApi();
            caseApi.setNumber(++number);
            caseApi.setPractitioners(new ArrayList<>());
            caseApi.setDates(new ArrayList<>());
            caseApi.setType(CaseTypeApi.ADMINISTRATION_ORDER);

            caseApiList.add(caseApi);
        }

        return caseApiList;
    }
}
