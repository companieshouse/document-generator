package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory;

import org.apache.commons.lang.text.StrSubstitutor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.filinghistory.FilingApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.exception.MapperException;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.RecentFilingHistory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToRecentFilingHistoryMapper {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String FILING_HISTORY_DESCRIPTIONS = "FILING_HISTORY_DESCRIPTIONS";
    private static final String D_MMMM_UUU = "d MMM uuu";

    @Mappings({
        @Mapping(source = "type", target = "form")
    })
    public abstract RecentFilingHistory apiToRecentFilingHistoryMapper(FilingApi filingApi) throws MapperException;
    public abstract List<RecentFilingHistory> apiToRecentFilingHistoryMapperList(List<FilingApi> filingApi) throws MapperException;

    @AfterMapping
    protected void convertFilingDescription(FilingApi filingApi, @MappingTarget RecentFilingHistory recentFilingHistory) {

        recentFilingHistory.setDescription(setFilingDescripton(filingApi.getDescription(), filingApi.getDescriptionValues()));
    }

    private String setFilingDescripton(String description, Map<String, Object> descriptionValues) {

        //TODO refactor for better implementation
        if (description.equals("legacy")) {
            return descriptionValues.get("description").toString();
        }

        String filingDescription = retrieveApiEnumerationDescription
            .getApiEnumerationDescription(FILING_HISTORY_DESCRIPTIONS, "description", description, getDebugMap(description) );

        return populateParameters(filingDescription, descriptionValues);
    }

    @AfterMapping
    protected void formatFilingDate(FilingApi filingApi, @MappingTarget RecentFilingHistory recentFilingHistory) {
        if (filingApi != null && filingApi.getDate() != null) {
            Date filingDate = filingApi.getDate();
            LocalDate date = filingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            recentFilingHistory.setDate(date.format(getFormatter()));
        }
    }

    private DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUU);
    }

    private String populateParameters(Object description, Map<String, Object> parameters) {
        StrSubstitutor sub = new StrSubstitutor(parameters, "{", "}");
        return sub.replace(description);
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }

}
