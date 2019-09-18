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
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.RecentFilingHistory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private static final String D_MMMMM_UUU = "d MMMM uuu";

    @Mappings({
            @Mapping(source = "type", target = "form")
    })
    public abstract RecentFilingHistory apiToRecentFilingHistoryMapper(FilingApi filingApi);

    public abstract List<RecentFilingHistory> apiToRecentFilingHistoryMapper(List<FilingApi> filingApi);

    @AfterMapping
    protected void convertFilingDescription(FilingApi filingApi,
            @MappingTarget RecentFilingHistory recentFilingHistory) {

        recentFilingHistory.setDescription(setFilingDescription(filingApi.getDescription(),
                filingApi.getDescriptionValues()));
    }

    private String setFilingDescription(String description, Map<String, Object> descriptionValues) {

        if (description.equals("legacy")) {

            return descriptionValues.get("description").toString();
        }

        String filingDescription = regexAsteriskRemoved(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(FILING_HISTORY_DESCRIPTIONS, "description",
                        description, getDebugMap(description)));

        if (descriptionValues != null) {

            return populateParameters(filingDescription, descriptionValues);
        } else
            return filingDescription;
    }

    @AfterMapping
    protected void formatFilingDate(FilingApi filingApi,
            @MappingTarget RecentFilingHistory recentFilingHistory) {
        if (filingApi != null && filingApi.getDate() != null) {
            LocalDate filingDate = filingApi.getDate();
            recentFilingHistory.setDate(filingDate.format(getDateFormatter()));
        }
    }

    private String populateParameters(Object description, Map<String, Object> parameters) {

        formatDateParameters(parameters);
        StrSubstitutor sub = new StrSubstitutor(parameters, "{", "}");

        return sub.replace(description);
    }

    private void formatDateParameters(Map<String, Object> parameters) {

        for (String parameterKey : parameters.keySet()) {
            if (parameterKey.equals("date") ||
                parameterKey.contains("_date") &&
                parameters.get(parameterKey) != null) {

                    LocalDate localDate = LocalDate.parse(parameters.get(parameterKey).toString());

                    parameters.replace(parameterKey,
                        parameters.get(parameterKey), localDate.format(getParamDateFormatter()));
            }
        }
    }

    private String regexAsteriskRemoved(String filingDescription) {
        return filingDescription.replaceAll("[*]", "");
    }

    private DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMM_UUU);
    }

    private DateTimeFormatter getParamDateFormatter() {
        return DateTimeFormatter.ofPattern(D_MMMMM_UUU);
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }
}
