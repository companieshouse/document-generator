package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.insolvency.DatesApi;
import uk.gov.companieshouse.api.model.insolvency.DatesTypeApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items.InsolvencyDate;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToInsolvencyDateMapperTest {

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @InjectMocks
    private ApiToInsolvencyDateMapper apiToInsolvencyDateMapper = new ApiToInsolvencyDateMapperImpl();

    private LocalDate TEST_DATE = LocalDate.of(2019,01,01);
    private String FORMATTED_DATE = "1 January 2019";
    private String ENUMERATION_DESCRIPTION = "enumeration-description";

    @Test
    @DisplayName("tests dates api data maps to dates model")
    void testApiToDatesMaps() {

        when(mockRetrieveApiEnumerationDescription
            .getApiEnumerationDescription(anyString(), anyString(), anyString(), anyMap()))
            .thenReturn(ENUMERATION_DESCRIPTION);

        List<InsolvencyDate> insolvencyDates = apiToInsolvencyDateMapper.apiToInsolvencyDateMapper(createDatesApi());

        assertNotNull(insolvencyDates);
        assertEquals(insolvencyDates.get(0).getDate(), FORMATTED_DATE);
        assertEquals(insolvencyDates.get(0).getType(), ENUMERATION_DESCRIPTION);

    }

    private List<DatesApi> createDatesApi() {

        List<DatesApi> datesApiList = new ArrayList<>();
        DatesApi datesApi = new DatesApi();
        datesApi.setDate(TEST_DATE);
        datesApi.setType(DatesTypeApi.ADMINISTRATION_DISCHARGED_ON);
        datesApiList.add(datesApi);

        return datesApiList;
    }
}
