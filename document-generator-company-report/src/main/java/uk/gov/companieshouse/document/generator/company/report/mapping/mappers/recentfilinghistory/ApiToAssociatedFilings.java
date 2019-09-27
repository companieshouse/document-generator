package uk.gov.companieshouse.document.generator.company.report.mapping.mappers.recentfilinghistory;

import org.apache.commons.lang.text.StrSubstitutor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;
import uk.gov.companieshouse.api.model.filinghistory.AssociatedFilingsApi;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.company.report.mapping.model.document.items.recentfilinghistory.items.AssociatedFilings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScope
@Mapper(componentModel = "spring")
public abstract class ApiToAssociatedFilings {

    @Autowired
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    private static final String FILING_HISTORY_DESCRIPTIONS = "FILING_HISTORY_DESCRIPTIONS";
    private static final String STATEMENT_OF_CAPITAL = "statement-of-capital";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    public abstract AssociatedFilings apiToAssociatedFilings(AssociatedFilingsApi associatedFilingsApi);

    public abstract List<AssociatedFilings> apiToAssociatedFilings(List<AssociatedFilingsApi> associatedFilingsApi);


    @AfterMapping
    protected void convertAssociatedFilingsDescription(AssociatedFilingsApi associatedFilingsApi,
                                                       @MappingTarget AssociatedFilings associatedFilings){

        associatedFilings.setDescription(setAssociatedFilingsDescription(associatedFilingsApi.getDescription(),
            associatedFilingsApi.getDescriptionValues()));
    }

    private String setAssociatedFilingsDescription(String description, Map<String, Object> descriptionValues) {

        if (!description.equals(STATEMENT_OF_CAPITAL)) {
            return descriptionValues.get("description").toString();
        } else {

        String associatedFilingsDescription = (retrieveApiEnumerationDescription
            .getApiEnumerationDescription(FILING_HISTORY_DESCRIPTIONS, "description",
                description, getDebugMap(description))) + "\n" +
                getStatementOfCapitalDescriptionValues(descriptionValues);

        if (descriptionValues != null) {
            return populateParameters(associatedFilingsDescription, descriptionValues);
        } else
            return associatedFilingsDescription;
        }
    }

    private String populateParameters(Object description, Map<String, Object> parameters) {

        formatDateParameters(parameters);
        StrSubstitutor sub = new StrSubstitutor(parameters, "{", "}");

        return sub.replace(description);
    }

    private String getStatementOfCapitalDescriptionValues(Map<String, Object> descriptionValues) {

        List<Map<String, Object>> list = (List) descriptionValues.get("capital");
        String valueSentence = "";

//        for (Map<String, Object> entry : list) {
//            for (String key : entry.keySet()) {
//                Object value = entry.get(key);
//            }
            valueSentence = list.get(0).get("currency").toString() + " " + list.get(0).get("figure").toString();
//        }
        return valueSentence;
    }

    private void formatDateParameters(Map<String, Object> parameters) {

        for (String parameterKey : parameters.keySet()) {
            if (parameterKey.equals("date") ||
                parameterKey.contains("_date") &&
                    parameters.get(parameterKey) != null) {

                LocalDate localDate = LocalDate.parse(parameters.get(parameterKey).toString());

                parameters.replace(parameterKey,
                    parameters.get(parameterKey), localDate.format(getDateFormatter()));
            }
        }
    }

    private DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern(YYYY_MM_DD);
    }

    private Map<String, String> getDebugMap(String debugString) {

        Map<String, String> debugMap = new HashMap<>();
        debugMap.put("Enumeration mapping :", debugString);

        return debugMap;
    }
}
