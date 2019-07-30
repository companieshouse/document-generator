package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.pscs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import uk.gov.companieshouse.api.model.psc.PscApi;
import uk.gov.companieshouse.api.model.psc.PscsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.Pscs;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.pscs.items.Psc;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToPscsMapperTest {

    @Mock
    private ApiToPscMapper mockApiToPscMapper;

    @InjectMocks
    private ApiToPscsMapper apiToPscsMapper = new ApiToPscsMapperImpl();
    
    @Test
    @DisplayName("tests pscs api data maps to pscs model")
    void testApiToCurrentAppointmentsMaps() throws Exception {

        PscsApi pscsApi = createPscsApi();

        List<Psc> pscList = generatePscList();

        when(mockApiToPscMapper.apiToPsc(pscsApi.getItems())).thenReturn(pscList);
        Pscs pscs = apiToPscsMapper.apiToPscsMapper(pscsApi);

        assertNotNull(pscs);
        assertEquals(1L, pscs.getActiveCount().longValue());
        assertEquals(2L, pscs.getCeasedCount().longValue());
        assertEquals(pscList, pscs.getItems());
    }

    private List<Psc> generatePscList() {
        List<Psc> pscList = new ArrayList<>();
        Psc psc1 = new Psc();
        psc1.setName("TEST1");
        pscList.add(psc1);

        Psc psc2 = new Psc();
        psc2.setName("test2");
        pscList.add(psc2);
        return pscList;
    }

    private PscsApi createPscsApi() {

        List<PscApi> pscApiList = new ArrayList<>();

        PscApi pscApi1 = new PscApi();
        pscApi1.setName("psc1");

        PscApi pscApi2 = new PscApi();
        pscApi2.setName("psc2");

        pscApiList.add(pscApi1);
        pscApiList.add(pscApi2);

        PscsApi pscsApi = new PscsApi();
        pscsApi.setItems(pscApiList);
        pscsApi.setActiveCount(1L);
        pscsApi.setCeasedCount(2L);
        pscsApi.setKind("kind");

        return pscsApi;
    }
}
