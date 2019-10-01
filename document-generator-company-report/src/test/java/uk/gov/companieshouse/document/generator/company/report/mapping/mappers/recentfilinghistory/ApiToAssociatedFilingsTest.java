package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.filinghistory.AssociatedFilingsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.AssociatedFilings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToAssociatedFilingsTest {

    public static final String MAPPED_VALUE = "Mapped Value";
    public static final String TYPE = "type";
    private static final String STATEMENT_OF_CAPITAL = "statement-of-capital";
    private static final String CAPITAL_DESCRIPTION = "GBP 111";

    @InjectMocks ApiToAssociatedFilings apiToAssociatedFilings = new ApiToAssociatedFilingsImpl();

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;

    @Test
    @DisplayName("test associated filings api data maps to model")
    void testAssociatedFilingsApiDataMaps() {

        AssociatedFilingsApi associatedFilingsApi = createAssociatedFilingsApiData();

        AssociatedFilings associatedFilings = apiToAssociatedFilings.apiToAssociatedFilings(associatedFilingsApi);

        assertNotNull(associatedFilings);
        assertEquals(MAPPED_VALUE, associatedFilings.getDescription());
    }

    @Test
    @DisplayName("test a list of associated filings api data maps to model")
    void testListOfAssociatedFilingsApiDataMaps() {

        List<AssociatedFilingsApi> associatedFilingsApi = createListOfAssociatedFilingsApiData();

        List<AssociatedFilings> associatedFilings = apiToAssociatedFilings.apiToAssociatedFilings(associatedFilingsApi);

        assertNotNull(associatedFilings);
        assertEquals(MAPPED_VALUE, associatedFilings.get(0).getDescription());
    }

    @Test
    @DisplayName("test associated filings api data with description of statement of capital maps to model")
    void testAssociatedFilingsApiDataMapsStatementOfCapital() {

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(STATEMENT_OF_CAPITAL);

        AssociatedFilingsApi associatedFilingsApi = createAssociatedFilingsApiDataWithStatementOfCapitalDescription();

        AssociatedFilings associatedFilings = apiToAssociatedFilings.apiToAssociatedFilings(associatedFilingsApi);

        assertNotNull(associatedFilings);
        assertEquals(STATEMENT_OF_CAPITAL + "\r" + CAPITAL_DESCRIPTION, associatedFilings.getDescription());
    }

    @Test
    @DisplayName("test associated filings api data with description of statement of capital maps to model when null")
    void testAssociatedFilingsApiDataMapsStatementOfCapitalWhenNull() {

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(STATEMENT_OF_CAPITAL);

        AssociatedFilingsApi associatedFilingsApi = createAssociatedFilingsApiDataWithStatementOfCapitalDescriptionNull();

        AssociatedFilings associatedFilings = apiToAssociatedFilings.apiToAssociatedFilings(associatedFilingsApi);

        assertNotNull(associatedFilings);
        assertEquals(null, associatedFilings.getCapitalDescription());
    }

    @Test
    @DisplayName("test annotations api data maps correctly to annotations")
    void testAnnotationsToAnnotationsFilingsWithNullValue() {

        AssociatedFilingsApi associatedFilingsApi = null;

        AssociatedFilings associatedFilings = apiToAssociatedFilings.apiToAssociatedFilings(associatedFilingsApi);

        assertNull(associatedFilings);
    }

    private AssociatedFilingsApi createAssociatedFilingsApiData() {

        HashMap<String, Object> associatedFilingsDescription = new HashMap<>();
        associatedFilingsDescription.put("description", MAPPED_VALUE);

        AssociatedFilingsApi associatedFilingsApi = new AssociatedFilingsApi();
        associatedFilingsApi.setDescriptionValues(associatedFilingsDescription);
        associatedFilingsApi.setType(TYPE);
        associatedFilingsApi.setDescription("description");

        return associatedFilingsApi;
    }

    private List<AssociatedFilingsApi> createListOfAssociatedFilingsApiData() {

        List<AssociatedFilingsApi> associatedFilingsApiList = new ArrayList<>();

        HashMap<String, Object> associatedFilingsDescription = new HashMap<>();
        associatedFilingsDescription.put("description", MAPPED_VALUE);

        AssociatedFilingsApi associatedFilingsApi = new AssociatedFilingsApi();
        associatedFilingsApi.setDescriptionValues(associatedFilingsDescription);
        associatedFilingsApi.setType(TYPE);
        associatedFilingsApi.setDescription("description");

        associatedFilingsApiList.add(associatedFilingsApi);
        associatedFilingsApiList.add(associatedFilingsApi);

        return associatedFilingsApiList;
    }

    private AssociatedFilingsApi createAssociatedFilingsApiDataWithStatementOfCapitalDescription() {

        Map<String, Object> values = new HashMap<>();
        values.put("currency", "GBP");
        values.put("figure", "111");

        List<Map<String, Object>> capitalList = new ArrayList<>();
        capitalList.add(values);

        HashMap<String, Object> associatedFilingsDescription = new HashMap<>();
        associatedFilingsDescription.put("capital", capitalList);

        LocalDate descriptionDate = LocalDate.of(
            2019, 05, 05);
        associatedFilingsDescription.put("date", descriptionDate);

        AssociatedFilingsApi associatedFilingsApi = new AssociatedFilingsApi();
        associatedFilingsApi.setDescriptionValues(associatedFilingsDescription);
        associatedFilingsApi.setType(TYPE);
        associatedFilingsApi.setDescription(STATEMENT_OF_CAPITAL);

        return associatedFilingsApi;
    }

    private AssociatedFilingsApi createAssociatedFilingsApiDataWithStatementOfCapitalDescriptionNull() {

        AssociatedFilingsApi associatedFilingsApi = new AssociatedFilingsApi();
        associatedFilingsApi.setDescriptionValues(null);
        associatedFilingsApi.setType(TYPE);
        associatedFilingsApi.setDescription(STATEMENT_OF_CAPITAL);

        return associatedFilingsApi;
    }
}
