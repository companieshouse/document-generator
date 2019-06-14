package uk.gov.companieshouse.document.generator.common.descriptions;



import uk.gov.companieshouse.document.generator.common.descriptions.yml.DescriptionsFactory;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.FilingDescriptions;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.Constants;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.FilingHistoryDescriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.MortgageDescriptions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class RetrieveApiEnumerationDescription {

    @Autowired
    private Constants constants;

    @Autowired
    private FilingHistoryDescriptions filingHistoryDescriptions;

    @Autowired
    private FilingDescriptions filingDescriptions;

    @Autowired
    private MortgageDescriptions mortgageDescriptions;

    private static final String RESOURCE_URI = "resource_uri";

    private static final String REQUEST_ID = "request_id";

    public static final String MODULE_NAME_SPACE = "document-generator-commons";

    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

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
            "getting value from the file descriptions file: " + descriptionType + " using key: " + key,
            setDebugMap(requestParameters));

        return descriptions.entrySet().stream()
            .filter(descriptionsEntrySet -> descriptionsEntrySet.getKey().equals(key))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElseGet(() -> {
                LOG.infoContext(requestParameters.get(REQUEST_ID),
                    "Value not found in file descriptions file: "
                    + descriptionType + " for key: " + key, setDebugMap(requestParameters));
                return null;
            });
    }

    private Map<String, Object> getApiEnumerationDescriptions(String descriptionType) {

        Supplier<DescriptionsFactory> descriptionsFactory =  DescriptionsFactory::new;

        return descriptionsFactory.get().createDescription(descriptionType).getData();
    }

    private Map<String, Object> setDebugMap(Map<String, String> requestParameters) {

        Map <String, Object> debugMap = new HashMap<>();
        debugMap.put(RESOURCE_URI, requestParameters.get(RESOURCE_URI));

        return debugMap;
    }
}
