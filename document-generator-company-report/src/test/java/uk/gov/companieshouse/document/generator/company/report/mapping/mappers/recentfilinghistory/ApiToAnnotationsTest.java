package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.filinghistory.AnnotationsApi;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.Annotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToAnnotationsTest {

    public static final String MAPPED_VALUE = "Mapped Value";

    @InjectMocks ApiToAnnotations apiToAnnotations = new ApiToAnnotationsImpl();

    @Test
    @DisplayName("test annotations api data maps to annotations")
    void testAnnotationsToAnnotationsFilingsMaps() {

        AnnotationsApi annotationsApi = createAnnotationsApiData();

        Annotations annotations = apiToAnnotations.apiToAnnotations(annotationsApi);

        assertNotNull(annotations);
        assertEquals(MAPPED_VALUE, annotations.getDescription());
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
    @DisplayName("test annotations api data maps correctly to annotations")
    void testAnnotationsToAnnotationsFilingsWithNullValue() {

        AnnotationsApi annotationsApi = null;

        Annotations annotations = apiToAnnotations.apiToAnnotations(annotationsApi);

        assertNull(annotations);
    }

    private AnnotationsApi createAnnotationsApiData() {

        HashMap<String, Object> annotationsDescription = new HashMap<>();
        annotationsDescription.put("description", MAPPED_VALUE);

        AnnotationsApi annotationsApi = new AnnotationsApi();
        annotationsApi.setDescriptionValues(annotationsDescription);

        return annotationsApi;
    }

    private List<AnnotationsApi> createAnnotationsApiDataList() {

        HashMap<String, Object> annotationsDescription = new HashMap<>();
        annotationsDescription.put("description", MAPPED_VALUE);

        List<AnnotationsApi> annotationsApiList = new ArrayList<>();

        AnnotationsApi annotations = new AnnotationsApi();
        annotations.setDescriptionValues(annotationsDescription);

        annotationsApiList.add(annotations);
        annotationsApiList.add(annotations);

        return annotationsApiList;
    }

}
