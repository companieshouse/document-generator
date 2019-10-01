package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.filinghistory.ResolutionsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.Resolutions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToResolutionsTest {

    public static final String MAPPED_VALUE = "Mapped Value";

    @InjectMocks ApiToResolutions apiToResolutions = new ApiToResolutionsImpl();

    @Test
    @DisplayName("test resolutions api data maps correctly to resolutions")
    void testResolutionsToResolutionsFilingMaps() {

        ResolutionsApi resolutionsApi = createResolutionsApiData();

        Resolutions resolutions = apiToResolutions.apiToResolutions(resolutionsApi);

        assertNotNull(resolutions);
        assertEquals(MAPPED_VALUE, resolutions.getDescription());
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

    private ResolutionsApi createResolutionsApiData() {

        HashMap<String, Object> resolutionDescription = new HashMap<>();
        resolutionDescription.put("description", MAPPED_VALUE);

        ResolutionsApi resolutionsApi = new ResolutionsApi();
        resolutionsApi.setDescriptionValues(resolutionDescription);

        return resolutionsApi;
    }

    private List<ResolutionsApi> createResolutionsApiDataList() {

        HashMap<String, Object> resolutionDescription = new HashMap<>();
        resolutionDescription.put("description", MAPPED_VALUE);

        List<ResolutionsApi> resolutionsApiList = new ArrayList<>();

        ResolutionsApi resolutions = new ResolutionsApi();
        resolutions.setDescriptionValues(resolutionDescription);

        resolutionsApiList.add(resolutions);
        resolutionsApiList.add(resolutions);

        return resolutionsApiList;
    }
}
