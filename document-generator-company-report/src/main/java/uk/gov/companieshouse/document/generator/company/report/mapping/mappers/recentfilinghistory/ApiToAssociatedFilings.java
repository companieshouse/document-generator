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
    private static final String STATEMENT_OF_CAPITAL ="statement-of-capital";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    public abstract AssociatedFilings apiToAssociatedFilings(AssociatedFilingsApi associatedFilingsApi);

    public abstract List<AssociatedFilings> apiToAssociatedFilings(List<AssociatedFilingsApi> associatedFilingsApi);

    @AfterMapping
    protected void convertAssociatedFilingsDescription(AssociatedFilingsApi associatedFilingsApi,
                                                       @MappingTarget AssociatedFilings associatedFilings){

        associatedFilings.setDescription(setAssociatedFilingsDescription(associatedFilingsApi.getDescription(), associatedFilingsApi.getDescriptionValues()));
    }

    private String setAssociatedFilingsDescription(String description, Map<String, Object> descriptionValues) {

        Object test =
        descriptionValues.entrySet().stream()
            .filter(descriptionsEntrySet -> descriptionsEntrySet.getKey().equals("capital"))
            .map(Map.Entry::getValue)
            .findFirst();

        return test.toString();

//
//
//       return test2.get(1).toString();
//        if (!description.equals(STATEMENT_OF_CAPITAL)) {
//
//            return descriptionValues.get("description").toString();
//        } else {

//            Object test =  descriptionValues.get("capital");
//
//            descriptionValues.entrySet()

//            Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
//
//            test = map;
//
//            String figure = ((HashMap) test).get("figure").toString();

//            List test2 = new ArrayList();
//
//        List<HashMap<String, String>> listofmaps = new ArrayList<HashMap<String, String>>();
//
//            for (int i = 0; i < 10; i++) {
//                HashMap<String, String> mMap = new HashMap<String, String>();
//                mMap.put((test2.get(i).toString()), (test2.get(i).toString()));
//                listofmaps.add(mMap);
//            }

//            ObjectMapper objectMapper = new ObjectMapper();
//
//            Map<String, Object> map = objectMapper.convertValue(test2, Map.class);
//
//            String currency = map.get("currency").toString();
//            String figure = map.get("figure").toString();
//            System.out.println(currency + figure);
//        }


//        String associatedFilingsDescription = (retrieveApiEnumerationDescription
//            .getApiEnumerationDescription(FILING_HISTORY_DESCRIPTIONS, "description",
//                description, getDebugMap(description)));
//
//        if (descriptionValues != null) {
//
//            return populateParameters(associatedFilingsDescription, descriptionValues);
//        } else
//            return associatedFilingsDescription;
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
