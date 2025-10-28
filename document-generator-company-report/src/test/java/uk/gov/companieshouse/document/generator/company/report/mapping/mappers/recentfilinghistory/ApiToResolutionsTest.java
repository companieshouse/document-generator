package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.filinghistory.ResolutionsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.Resolutions;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToResolutionsTest {

    public static final String MAPPED_VALUE = "Mapped Value";
    private static final LocalDate DATE = LocalDate.of(2015, 04, 04);
    private static final String DATE_FORMATTED = "4 Apr 2015";

    @InjectMocks ApiToResolutions apiToResolutions = new ApiToResolutionsImpl();

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;

    @Test
    @DisplayName("test resolutions api data maps to model with legacy as description")
    void testResolutionsToResolutionsFilingMapsWithLegacyAsDescription() {

        ResolutionsApi resolutionsApi = createResolutionsApiDataWithLegacyDescription();

        Resolutions resolutions = apiToResolutions.apiToResolutions(resolutionsApi);

        assertNotNull(resolutions);
        assertEquals(MAPPED_VALUE, resolutions.getDescription());
    }

    @Test
    @DisplayName("test annotations api data maps to annotations with other as description")
    void testAnnotationsToAnnotationsFilingsMapsWithOtherDescription() {

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(MAPPED_VALUE);

        ResolutionsApi resolutionsApi = createResolutionsApiDataWithOtherDescription();

        Resolutions resolutions = apiToResolutions.apiToResolutions(resolutionsApi);

        assertNotNull(resolutions);
        assertEquals(MAPPED_VALUE, resolutions.getDescription());
        assertEquals(DATE_FORMATTED, resolutions.getDate());
    }

    @Test
    @DisplayName("test a list of resolutions api data maps correctly to resolutions")
    void testListOfResolutionsToResolutionsFilingMaps() {

        List<ResolutionsApi> resolutionsApi = createResolutionsApiDataList();

        List<Resolutions> resolutions = apiToResolutions.apiToResolutions(resolutionsApi);

        assertNotNull(resolutions);
        assertEquals(MAPPED_VALUE, resolutions.get(0).getDescription());
        assertEquals(MAPPED_VALUE, resolutions.get(1).getDescription());
    }

    @Test
    @DisplayName("test resolutions api with null value to resolutions model")
    void testApitoResolutionsMapsWithNullApiModel() {

        ResolutionsApi resolutionsApi = null;

        Resolutions resolutions = apiToResolutions.apiToResolutions(resolutionsApi);

        assertNull(resolutions);
    }

    private ResolutionsApi createResolutionsApiDataWithLegacyDescription() {

        HashMap<String, Object> resolutionDescription = new HashMap<>();
        resolutionDescription.put("description", MAPPED_VALUE);

        ResolutionsApi resolutionsApi = new ResolutionsApi();
        resolutionsApi.setDescriptionValues(resolutionDescription);

        resolutionsApi.setDescription("legacy");

        return resolutionsApi;
    }

    private ResolutionsApi createResolutionsApiDataWithOtherDescription() {

        HashMap<String, Object> resolutionDescription = new HashMap<>();
        resolutionDescription.put("description", MAPPED_VALUE);

        LocalDate descriptionDate = LocalDate.of(
            2019, 05, 05);
        resolutionDescription.put("date", descriptionDate);

        ResolutionsApi resolutionsApi = new ResolutionsApi();
        resolutionsApi.setDescriptionValues(resolutionDescription);
        resolutionsApi.setReceiveDate(DATE);
        resolutionsApi.setDescription("other");

        return resolutionsApi;
    }

    private List<ResolutionsApi> createResolutionsApiDataList() {

        HashMap<String, Object> resolutionDescription = new HashMap<>();
        resolutionDescription.put("description", MAPPED_VALUE);

        List<ResolutionsApi> resolutionsApiList = new ArrayList<>();

        ResolutionsApi resolutions = new ResolutionsApi();
        resolutions.setDescriptionValues(resolutionDescription);
        resolutions.setDescription("legacy");

        resolutionsApiList.add(resolutions);
        resolutionsApiList.add(resolutions);

        return resolutionsApiList;
    }
}
