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
import uk.gov.companieshouse.api.model.filinghistory.AnnotationsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.Annotations;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToAnnotationsTest {


    public static final String MAPPED_VALUE = "Mapped Value";
    private static final LocalDate DATE = LocalDate.of(2015, 04, 04);
    private static final String DATE_FORMATTED = "4 Apr 2015";


    @InjectMocks ApiToAnnotations apiToAnnotations = new ApiToAnnotationsImpl();

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerations;

    @Test
    @DisplayName("test annotations api data maps to annotations with legacy as description")
    void testAnnotationsToAnnotationsFilingsMapsWithLegacyDescription() {

        AnnotationsApi annotationsApi = createAnnotationsApiDataWithLegacyValue();

        Annotations annotations = apiToAnnotations.apiToAnnotations(annotationsApi);

        assertNotNull(annotations);
        assertEquals(MAPPED_VALUE, annotations.getDescription());
    }

    @Test
    @DisplayName("test annotations api data maps to annotations with other as description")
    void testAnnotationsToAnnotationsFilingsMapsWithOtherDescription() {

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(MAPPED_VALUE);

        AnnotationsApi annotationsApi = createAnnotationsApiDataWithOtherValue();

        Annotations annotations = apiToAnnotations.apiToAnnotations(annotationsApi);

        assertNotNull(annotations);
        assertEquals(MAPPED_VALUE, annotations.getDescription());
        assertEquals(DATE_FORMATTED, annotations.getDate());

    }

    @Test
    @DisplayName("test annotations api data maps to annotations with other as description value")
    void testAnnotationsToAnnotationsFilingsMapsWithNullDescriptionValues() {

        when(mockRetrieveApiEnumerations.getApiEnumerationDescription(anyString(), anyString(), anyString(), any())).thenReturn(MAPPED_VALUE);

        AnnotationsApi annotationsApi = createAnnotationsApiDataWithNullDescriptionValues();

        Annotations annotations = apiToAnnotations.apiToAnnotations(annotationsApi);

        assertNotNull(annotations);
        assertEquals(MAPPED_VALUE, annotations.getDescription());
        assertEquals(DATE_FORMATTED, annotations.getDate());

    }

    @Test
    @DisplayName("test a list of annotations api data maps correctly to annotations")
    void testListOfAnnotationsToAnnotationsFilingsMaps() {

        List<AnnotationsApi> annotationsApi = createAnnotationsApiDataList();

        List<Annotations> annotations = apiToAnnotations.apiToAnnotations(annotationsApi);

        assertNotNull(annotations);
        assertEquals(MAPPED_VALUE, annotations.get(0).getDescription());
        assertEquals(MAPPED_VALUE, annotations.get(1).getDescription());
    }

    @Test
    @DisplayName("test annotations api data with null maps correctly to annotations")
    void testAnnotationsToAnnotationsFilingsWithNullValue() {

        AnnotationsApi annotationsApi = null;

        Annotations annotations = apiToAnnotations.apiToAnnotations(annotationsApi);

        assertNull(annotations);
    }

    private AnnotationsApi createAnnotationsApiDataWithLegacyValue() {

        HashMap<String, Object> annotationsDescription = new HashMap<>();
        annotationsDescription.put("description", MAPPED_VALUE);

        AnnotationsApi annotationsApi = new AnnotationsApi();
        annotationsApi.setDescriptionValues(annotationsDescription);
        annotationsApi.setDescription("legacy");

        return annotationsApi;
    }

    private AnnotationsApi createAnnotationsApiDataWithOtherValue() {

        HashMap<String, Object> annotationsDescription = new HashMap<>();
        annotationsDescription.put("description", MAPPED_VALUE);

        LocalDate descriptionDate = LocalDate.of(
            2019, 05, 05);
        annotationsDescription.put("date", descriptionDate);

        AnnotationsApi annotationsApi = new AnnotationsApi();
        annotationsApi.setDescriptionValues(annotationsDescription);
        annotationsApi.setDescription("other");
        annotationsApi.setDate(DATE);

        return annotationsApi;
    }

    private AnnotationsApi createAnnotationsApiDataWithNullDescriptionValues() {

        AnnotationsApi annotationsApi = new AnnotationsApi();
        annotationsApi.setDescriptionValues(null);
        annotationsApi.setDescription("other");
        annotationsApi.setDate(DATE);

        return annotationsApi;
    }

    private List<AnnotationsApi> createAnnotationsApiDataList() {

        HashMap<String, Object> annotationsDescription = new HashMap<>();
        annotationsDescription.put("description", MAPPED_VALUE);

        List<AnnotationsApi> annotationsApiList = new ArrayList<>();

        AnnotationsApi annotations = new AnnotationsApi();
        annotations.setDescriptionValues(annotationsDescription);
        annotations.setDescription("legacy");

        annotationsApiList.add(annotations);
        annotationsApiList.add(annotations);

        return annotationsApiList;
    }
}
