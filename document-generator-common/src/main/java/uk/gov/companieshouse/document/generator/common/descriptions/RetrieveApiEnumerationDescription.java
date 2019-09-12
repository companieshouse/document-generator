package uk.gov.companieshouse.document.generator.common.descriptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.DescriptionsFactory;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component
public class RetrieveApiEnumerationDescription {

    private static final String RESOURCE_URI = "resource_uri";

    private static final String REQUEST_ID = "request_id";

    private static final String ENUMERATION_MAPPING = "enumeration mapping";

    public static final String DESCRIPTIONS_MODULE_NAME_SPACE = "document-generator-commons-descriptions";

    private static final Logger LOG = LoggerFactory.getLogger(DESCRIPTIONS_MODULE_NAME_SPACE);

    @Autowired
    private DescriptionsFactory descriptionsFactory;

    public String getApiEnumerationDescription(String descriptionType, String identifier, String descriptionValue,
        Map<String, String> requestParameters) {

        String description = "";

        Map<String, Object> apiEnumeration =  getApiEnumerationDescriptions(descriptionType);

        Map<String, Object> filteredDescriptions = (Map<String, Object>) getDescriptionsValue(apiEnumeration,
            identifier, descriptionType, requestParameters);

        if (filteredDescriptions != null) {
            description =  String.valueOf(getDescriptionsValue(filteredDescriptions,
                descriptionValue, descriptionType, requestParameters));
        }

        return description;
    }

    private Object getDescriptionsValue(Map<String, Object> descriptions, String key, String descriptionType,
        Map<String, String> requestParameters) {

        LOG.infoContext(requestParameters.get(REQUEST_ID),
            "getting descriptions value from: " + descriptionType + " using key: " + key,
            setDebugMap(requestParameters));

        return descriptions.entrySet().stream()
            .filter(descriptionsEntrySet -> descriptionsEntrySet.getKey().equals(key))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElseGet(() -> {
                LOG.infoContext(requestParameters.get(REQUEST_ID),
                    "Descriptions value not found for: "
                    + descriptionType + " for key: " + key, setDebugMap(requestParameters));
                return null;
            });
    }

    private Map<String, Object> getApiEnumerationDescriptions(String descriptionType) {
        return descriptionsFactory.createDescription(descriptionType).getData();
    }

    private Map<String, Object> setDebugMap(Map<String, String> requestParameters) {

        Map <String, Object> debugMap = new HashMap<>();
        if(requestParameters.containsKey(RESOURCE_URI)) {
            debugMap.put(RESOURCE_URI, requestParameters.get(RESOURCE_URI));
            // Temporary solution as need to think about the information stored here, as we
            // cannot obtain the resource id/company number during the mapstruct phase.
        } else if (requestParameters.containsKey(ENUMERATION_MAPPING))
            debugMap.put(ENUMERATION_MAPPING, requestParameters.get(ENUMERATION_MAPPING));

        return debugMap;
    }
}
