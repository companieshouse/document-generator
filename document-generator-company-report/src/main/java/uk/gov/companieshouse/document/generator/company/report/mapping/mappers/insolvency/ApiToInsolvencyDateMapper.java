package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.insolvency;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.insolvency.DatesApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.insolvency.items.InsolvencyDate;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToInsolvencyDateMapper {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String CONSTANTS = "constants";
    private static final String INSOLVENCY_CASE_DATE_TYPE = "insolvency_case_date_type";
    private static final String ENUMERATION_MAPPING = "Enumeration mapping :";

    private static final String D_MMMM_UUUU = "d MMMM uuuu";

    @Mappings({
        @Mapping(target = "date", ignore = true),
        @Mapping(target = "type", ignore = true)
    })
    public abstract InsolvencyDate apiToInsolvencyDateMapper(DatesApi datesApi);

    public abstract List<InsolvencyDate> apiToInsolvencyDateMapper(List<DatesApi> datesApis);

    @AfterMapping
    protected void formatDate(DatesApi datesApi, @MappingTarget InsolvencyDate insolvencyDate) {

        if(datesApi != null && datesApi.getDate() != null) {
            insolvencyDate.setDate(datesApi.getDate().format(getFormatter()));
        }
    }

    @AfterMapping
    protected void setDateType(DatesApi datesApi, @MappingTarget InsolvencyDate insolvencyDate) {

        if (datesApi != null && datesApi.getType() != null) {
            insolvencyDate.setType(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(CONSTANTS, INSOLVENCY_CASE_DATE_TYPE,
                    datesApi.getType().getType(), getDebugMap(datesApi.getType().name())));
        }
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUUU);
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put(ENUMERATION_MAPPING, debugString);

        return debugMap;
    }
}