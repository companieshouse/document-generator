package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.corporateannotation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.api.model.company.CorporateAnnotationApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.corporateannotation.CorporateAnnotation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiToCorporateAnnotationMapperTest {

    private static final String ENUMERATION_DESCRIPTION = "enumeration-description";

    @Mock
    private RetrieveApiEnumerationDescription mockRetrieveApiEnumerationDescription;

    @InjectMocks
    private ApiToCorporateAnnotationMapper apiToCorporateAnnotationMapper = new ApiToCorporateAnnotationMapperImpl();

    @Test
    @DisplayName("tests corporate annotation api data maps to corporate annotation  model")
    void testApiToCorporateAnnotationMaps() {

        when(mockRetrieveApiEnumerationDescription
            .getApiEnumerationDescription(anyString(), anyString(), anyString(), anyMap()))
            .thenReturn(ENUMERATION_DESCRIPTION);

        List<CorporateAnnotation> corporateAnnotationList = apiToCorporateAnnotationMapper.apiToCorporateAnnotations(createCorporateAnnotationApi());

        assertNotNull(corporateAnnotationList);
        assertEquals(corporateAnnotationList.get(0).getDescription(), "this company done something bad");
        assertEquals(corporateAnnotationList.get(0).getType(), ENUMERATION_DESCRIPTION);

    }

    private List<CorporateAnnotationApi> createCorporateAnnotationApi() {

        List<CorporateAnnotationApi> corporateAnnotationApiList = new ArrayList<>();
        CorporateAnnotationApi corporateAnnotationApi = new CorporateAnnotationApi();
        corporateAnnotationApi.setType("other");
        corporateAnnotationApi.setDescription("this company done something bad");
        corporateAnnotationApi.setCreatedOn(LocalDate.of(1999, 1, 1));

        corporateAnnotationApiList.add(corporateAnnotationApi);

        return corporateAnnotationApiList;
    }
}
