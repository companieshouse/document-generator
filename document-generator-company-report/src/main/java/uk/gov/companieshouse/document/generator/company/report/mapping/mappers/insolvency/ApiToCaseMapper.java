package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.insolvency.CaseApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items.InsolvencyCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring", uses = {ApiToPractitionerMapper.class, ApiToInsolvencyDateMapper.class})
public abstract class ApiToCaseMapper {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String CONSTANTS = "constants";
    private static final String INSOLVENCY_CASE_TYPE = "insolvency_case_type";
    private static final String ENUMERATION_MAPPING = "Enumeration mapping :";


    @Mappings({
        @Mapping(target = "type", ignore = true)
    })
    public abstract InsolvencyCase apiToCaseMapper(CaseApi caseApi);

    public abstract List<InsolvencyCase> apiToCaseMapper(List<CaseApi> caseApis);

    @AfterMapping
    protected void setCaseType(CaseApi caseApi, @MappingTarget InsolvencyCase insolvencyCase) {

        if (caseApi != null && caseApi.getType() != null) {
            insolvencyCase.setType(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(CONSTANTS, INSOLVENCY_CASE_TYPE,
                    caseApi.getType().getType(), getDebugMap(caseApi.getType().getType())));
        }
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put(ENUMERATION_MAPPING, debugString);

        return debugMap;
    }
}
