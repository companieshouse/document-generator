package uk.gov.companieshouse.document.generator.company.report.mapping.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory.ApiToRecentFilingHistoryMapper;
import uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory.ApiToRecentFilingHistoryMapperImpl;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.RecentFilingHistory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToRecentFilingHistoryMapperTest {

    public static final String MAPPED_VALUE = "Mapped Value";
    public static final String LEGACY_VALUE = "legacy";

    private static final LocalDate FILING_DATE =  LocalDate.of(1999, 01, 01);
    private static final String FILING_DATE_STRING = "1 Jan 1999";
    private static final String FILING_DESCRIPTION = "filing description 1";
    private static final String FORM_TYPE = "form type 1";

    @InjectMocks
    private ApiToRecentFilingHistoryMapper apiToRecentFilingHistoryMapper =
            new ApiToRecentFilingHistoryMapperImpl();

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;

    @Test
    @DisplayName("test filing api data maps correctly to recent filing history")
    void testApiToRecentFilingInformationMaps() throws MapperException {

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(MAPPED_VALUE);

        RecentFilingHistory recentFilingHistory =  apiToRecentFilingHistoryMapper
            .apiToRecentFilingHistoryMapper(createFiling());

        assertNotNull(recentFilingHistory);
        assertEquals(FILING_DATE_STRING, recentFilingHistory.getDate());
        assertEquals(MAPPED_VALUE, recentFilingHistory.getDescription());
        assertEquals(FORM_TYPE, recentFilingHistory.getForm());
    }

    @Test
    @DisplayName("test a list of filing api data maps correctly to recent filing history")
    void testApiListToRecentFilingInformation() throws MapperException {

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(MAPPED_VALUE);

        List<FilingApi> filingApiList = new ArrayList<>();

        filingApiList.add(createFiling());
        filingApiList.add(createFiling());
        filingApiList.add(createFiling());

        List<RecentFilingHistory> recentFilingHistoryList =
            apiToRecentFilingHistoryMapper.apiToRecentFilingHistoryMapper(filingApiList);

        assertNotNull(recentFilingHistoryList);
        assertEquals(3, recentFilingHistoryList.size());

        assertEquals(recentFilingHistoryList.get(0).getDate(), FILING_DATE_STRING);
        assertEquals(recentFilingHistoryList.get(0).getDescription(), MAPPED_VALUE);
        assertEquals(recentFilingHistoryList.get(0).getForm(), FORM_TYPE);

        assertEquals(recentFilingHistoryList.get(1).getDate(), FILING_DATE_STRING);
        assertEquals(recentFilingHistoryList.get(1).getDescription(), MAPPED_VALUE);
        assertEquals(recentFilingHistoryList.get(1).getForm(), FORM_TYPE);

        assertEquals(recentFilingHistoryList.get(2).getDate(), FILING_DATE_STRING);
        assertEquals(recentFilingHistoryList.get(2).getDescription(), MAPPED_VALUE);
        assertEquals(recentFilingHistoryList.get(2).getForm(), FORM_TYPE);
    }

    @Test
    @DisplayName("test filing api null value data maps to recent filing history model")
    void testApiToRecentFilingHistoryMapsWithNullValues() throws MapperException {

        FilingApi filingApi = null;

        RecentFilingHistory recentFilingHistory =  apiToRecentFilingHistoryMapper
            .apiToRecentFilingHistoryMapper(filingApi);


        assertEquals(null, recentFilingHistory);
    }

    @Test
    @DisplayName("test filing api with legacy as the filing description is handled")
    void testFilingApiWithDescriptionSetToLegacy() throws MapperException {

        RecentFilingHistory recentFilingHistory =  apiToRecentFilingHistoryMapper
            .apiToRecentFilingHistoryMapper(createFilingLegacy());

        assertNotNull(recentFilingHistory.getDescription());
        assertEquals(recentFilingHistory.getDescription(), LEGACY_VALUE);

    }

    @Test
    @DisplayName("test filing api with date description values is correctly mapped")
    void testFilingApiWithDateDescriptionValues() throws MapperException {

        createFilingWithDateDescription();
        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn("test description with {made_up_date}");

        RecentFilingHistory recentFilingHistory =  apiToRecentFilingHistoryMapper
                .apiToRecentFilingHistoryMapper(createFilingWithDateDescription());

     assertNotNull(recentFilingHistory.getDescription());
     assertEquals("test description with 1 Jan 1999", recentFilingHistory.getDescription());

    }

    private FilingApi createFiling(){

        FilingApi filingApi = new FilingApi();

        filingApi.setDate(FILING_DATE);
        filingApi.setDescription(FILING_DESCRIPTION);
        filingApi.setType(FORM_TYPE);

        return filingApi;
    }

    private FilingApi createFilingLegacy(){

        FilingApi filingApi = new FilingApi();
        HashMap<String, Object> filingDescription = new HashMap<>();
        filingDescription.put("description", "legacy");

        filingApi.setDate(FILING_DATE);
        filingApi.setDescription(LEGACY_VALUE);
        filingApi.setType(FORM_TYPE);
        filingApi.setDescriptionValues(filingDescription);

        return filingApi;
    }

    private FilingApi createFilingWithDateDescription(){

        FilingApi filingApi = new FilingApi();
        HashMap<String, Object> filingDescription = new HashMap<>();
        filingDescription.put("description", FILING_DESCRIPTION);

        HashMap<String, Object> descriptionValue = new HashMap<>();
        descriptionValue.put("made_up_date", FILING_DATE);

        filingApi.setDate(FILING_DATE);
        filingApi.setDescription("test description with {made_up_date}");
        filingApi.setType(FORM_TYPE);
        filingApi.setDescriptionValues(descriptionValue);

        return filingApi;
    }
}
