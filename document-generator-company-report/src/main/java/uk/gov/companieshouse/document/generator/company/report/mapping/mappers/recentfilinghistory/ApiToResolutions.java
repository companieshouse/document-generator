package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.text.StrSubstitutor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.filinghistory.ResolutionsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.Resolutions;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToResolutions {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String FILING_HISTORY_DESCRIPTIONS = "FILING_HISTORY_DESCRIPTIONS";
    private static final String D_MMMM_UUU = "d MMM uuu";
    private static final String D_MMMMM_UUU = "d MMMM uuu";

    public abstract Resolutions apiToResolutions(ResolutionsApi resolutionsApi);

    public abstract List<Resolutions> apiToResolutions(List<ResolutionsApi> resolutionsApi);

    @Mappings({
        @Mapping(source = "type", target = "form"),
        @Mapping(source = "receive_date", target = "date")
    })

    @AfterMapping
    protected void convertResolutionsDescription(ResolutionsApi resolutionsApi,
                                                 @MappingTarget Resolutions resolutions) {
        resolutions.setDescription(setResolutionsDescription(resolutionsApi.getDescription(),
            resolutionsApi.getDescriptionValues()));
    }

    private String setResolutionsDescription(String description, Map<String, Object> descriptionValues) {

        if (description != null && descriptionValues != null && description.equals("legacy") ||
            description.equals("resolution")) {
            return descriptionValues.get("description").toString();
        }

        String filingDescription = "";

        if( description != null ) {
            filingDescription = regexAsteriskRemoved(retrieveApiEnumerationDescription
                .getApiEnumerationDescription(FILING_HISTORY_DESCRIPTIONS, "description",
                    description, getDebugMap(description)));
        }

        if (descriptionValues != null) {

            return populateParameters(filingDescription, descriptionValues);
        } else
            return filingDescription;
    }

    @AfterMapping
    protected void formatFilingDate(ResolutionsApi resolutionsApi,
                                    @MappingTarget Resolutions resolutions) {
        if (resolutionsApi != null && resolutionsApi.getReceiveDate() != null) {
            LocalDate filingDate = resolutionsApi.getReceiveDate();
            resolutions.setDate(filingDate.format(getDateFormatter()));
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
