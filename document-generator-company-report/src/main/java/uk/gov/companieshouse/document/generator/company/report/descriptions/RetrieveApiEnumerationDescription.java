package uk.gov.companieshouse.document.generator.company.report.descriptions;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.company.report.descriptions.config.ConstantsApiEnumeration;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import static uk.gov.companieshouse.document.generator.company.report.CompanyReportDocumentInfoServiceImpl.MODULE_NAME_SPACE;

@Component
public class RetrieveApiEnumerationDescription {

    @Autowired
    private ConstantsApiEnumeration constantsApiEnumeration;


    private static final Logger LOG = LoggerFactory.getLogger(MODULE_NAME_SPACE);

    public String getApiEnumerationDescription(String fileName, String identifier, String descriptionValue) {

        String description = "";

        Map<String, Object> apiEnumeration =  getApiEnumeration(fileName);

        Map<String, Object> filteredDescriptions = (Map<String, Object>) getDescriptionsValue(apiEnumeration,
                identifier, fileName);

        if (filteredDescriptions != null) {
            description =  String.valueOf(getDescriptionsValue(filteredDescriptions, descriptionValue, fileName));
        }

        return description;
    }

    private Object getDescriptionsValue(Map<String, Object> descriptions, String key, String fileName) {

        LOG.info("getting value from the file descriptions file: " + fileName + " using key: " + key);
        return descriptions.entrySet().stream()
                .filter(descriptionsEntrySet -> descriptionsEntrySet.getKey().equals(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(() -> {
                    LOG.info("Value not found in file descriptions file: "
                            + fileName + " for key: " + key);
                    return null;
                });
    }

    private Map<String, Object> getApiEnumeration(String apiEnumerationFile) {

        if (apiEnumerationFile == "constants.yml") {
            return constantsApiEnumeration.getConstants();
        }

        return null;
    }
}
