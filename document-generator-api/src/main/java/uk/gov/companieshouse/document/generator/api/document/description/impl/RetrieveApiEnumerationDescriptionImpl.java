package uk.gov.companieshouse.document.generator.api.document.description.impl;

import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import uk.gov.companieshouse.document.generator.api.document.description.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static uk.gov.companieshouse.document.generator.api.DocumentGeneratorApplication.APPLICATION_NAME_SPACE;

@Service
public class RetrieveApiEnumerationDescriptionImpl implements RetrieveApiEnumerationDescription {

    private static final Logger LOG = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    @Override
    public String getApiEnumerationDescription(String fileName, String identifier, String accountType,
                                               Map<String, String> parameters) throws IOException {

        Yaml yaml = new Yaml();
        File descriptionsFile = new File(fileName);

        String description = "";

        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            Map<String, Object> descriptions = (Map<String, Object>) yaml.load(inputStream);
            Map<String, Object> filteredDescriptions = (Map<String, Object>) getDescriptionsValue(descriptions,
                    identifier, fileName);
            if(filteredDescriptions != null) {
                description =  String.valueOf(getDescriptionsValue(filteredDescriptions, accountType, fileName));
            }
        } catch (FileNotFoundException e) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("file", fileName);
            dataMap.put("identifier", identifier);
            LOG.trace("file not found when obtain api enumeration descriptions for file name: "
                    + descriptionsFile, dataMap);
        }

        return populateParameters(description, parameters);
    }

    /**
     * Get a value from the file descriptions using the key. Log a message if the key is missing.
     *
     * @param descriptions
     * @param key
     * @param fileName
     * @return
     */
    private static Object getDescriptionsValue(Map<String, Object> descriptions, String key, String fileName) {
        return descriptions.entrySet().stream()
                .filter(map -> descriptions.containsKey(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(() -> {
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("file", fileName);
                    dataMap.put("key", key);
                    LOG.trace("Value not found in file descriptions", dataMap);
                    return null;
                });
    }

    /**
     * Populate the parameters in the description
     *
     * @param description
     * @param parameters
     * @return description
     */
    private static String populateParameters(Object description, Map<String, String> parameters) {
        StrSubstitutor sub = new StrSubstitutor(parameters, "{", "}");
        return sub.replace(description);
    }
}
