package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.corporateannotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.company.CorporateAnnotationApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.corporateannotation.CorporateAnnotation;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToCorporateAnnotationMapper {

    private static final String CONSTANTS = "constants";
    private static final String CORPORATE_ANNOTATION_TYPE = "corporate_annotation_type";
    private static final String ENUMERATION_MAPPING = "Enumeration mapping :";

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    public abstract CorporateAnnotation apiToCorporateAnnotation(CorporateAnnotationApi corporateAnnotationApi);

    public abstract List<CorporateAnnotation> apiToCorporateAnnotations(List<CorporateAnnotationApi> corporateAnnotationApi);

    @AfterMapping
    protected void convertDescription(CorporateAnnotationApi corporateAnnotationApi,
                                      @MappingTarget CorporateAnnotation corporateAnnotation) {

        if (corporateAnnotationApi != null && corporateAnnotationApi.getType() != null) {
            corporateAnnotation.setType(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(CONSTANTS, CORPORATE_ANNOTATION_TYPE,
                    corporateAnnotationApi.getType(), getDebugMap(corporateAnnotationApi.getType())));
        }

    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put(ENUMERATION_MAPPING, debugString);

        return debugMap;
    }
}
