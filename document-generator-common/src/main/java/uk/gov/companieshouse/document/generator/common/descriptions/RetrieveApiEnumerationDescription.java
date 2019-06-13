package uk.gov.companieshouse.document.generator.common.descriptions;



import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Constants;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.FilingHistoryDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.MortgageDescriptions;

import java.util.HashMap;
import java.util.Map;

@Component
public class RetrieveApiEnumerationDescription {

    @Autowired
    private Constants constants;

    @Autowired
    private FilingHistoryDescriptions filingHistoryDescriptions;

    @Autowired
    private MortgageDescriptions mortgageDescriptions;

    private static final String RESOURCE_URI = "resource_uri";

    private static final String REQUEST_ID = "request_id";

    public static final String MODULE_NAME_SPACE = "document-generator-commons";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    public String getApiEnumerationDescription(String fileName, String identifier, String descriptionValue,
        Map<String, String> requestParameters) {

        String description = "";

        Map<String, Object> apiEnumeration =  getApiEnumeration(fileName);

        Map<String, Object> filteredDescriptions = (Map<String, Object>) getDescriptionsValue(apiEnumeration,
            identifier, fileName, requestParameters);

        if (filteredDescriptions != null) {
            description =  String.valueOf(getDescriptionsValue(filteredDescriptions,
                descriptionValue, fileName, requestParameters));
        }

        return description;
    }

    private Object getDescriptionsValue(Map<String, Object> descriptions, String key, String fileName,
        Map<String, String> requestParameters) {

        LOG.infoContext(requestParameters.get(REQUEST_ID),
            "getting value from the file descriptions file: " + fileName + " using key: " + key,
            setDebugMap(requestParameters));

        return descriptions.entrySet().stream()
            .filter(descriptionsEntrySet -> descriptionsEntrySet.getKey().equals(key))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElseGet(() -> {
                LOG.infoContext(requestParameters.get(REQUEST_ID),
                    "Value not found in file descriptions file: "
                    + fileName + " for key: " + key, setDebugMap(requestParameters));
                return null;
            });
    }

    private Map<String, Object> getApiEnumeration(String fileName) {

        if (fileName == "constants.yml") {
            return constants.getConstants();
        }

        if (fileName == "mortgage_descriptions.yml") {
            return mortgageDescriptions.getMortgageDescriptions();
        }

        if (fileName == "filing_history_descriptions.yml") {
            return filingHistoryDescriptions.getFilingHistoryDescriptions();
        }

        return null;
    }

    private Map<String, Object> setDebugMap(Map<String, String> requestParameters) {

        Map <String, Object> debugMap = new HashMap<>();
        debugMap.put(RESOURCE_URI, requestParameters.get(RESOURCE_URI));

        return debugMap;
    }
}
