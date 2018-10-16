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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApiEnumerationDescription(String fileName, String identifier, String accountType,
                                               Map<String, String> parameters, String requestId, String resourceUri,
                                               String resourceId) throws IOException {


        Yaml yaml = new Yaml();
        File descriptionsFile = new File(fileName);

        String description = "";

        LOG.infoContext(requestId,"obtaining file for api enumerations with file name: " +
                descriptionsFile, setDebugMap(resourceUri, resourceId));
        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            LOG.infoContext(requestId,"The file: " + descriptionsFile + " has been found, obtaining descriptions",
                    setDebugMap(resourceUri, resourceId));
            Map<String, Object> descriptions = (Map<String, Object>) yaml.load(inputStream);
            Map<String, Object> filteredDescriptions = (Map<String, Object>) getDescriptionsValue(descriptions,
                    identifier, fileName, requestId, resourceUri, resourceId);
            if(filteredDescriptions != null) {
                description =  String.valueOf(getDescriptionsValue(filteredDescriptions, accountType, fileName,
                        requestId, resourceUri, resourceId));
            }
        } catch (FileNotFoundException e) {
            LOG.trace("file not found when obtain api enumeration descriptions for file name: "
                    + descriptionsFile, setDebugMap(resourceUri, resourceId));
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
    private Object getDescriptionsValue(Map<String, Object> descriptions, String key, String fileName,
                                               String requestId, String resourceUri, String resourceId) {
        LOG.infoContext(requestId,"getting value from the file descriptions file: " + fileName
                        + " using key: " + key, setDebugMap(resourceUri, resourceId));
        return descriptions.entrySet().stream()
                .filter(map -> descriptions.containsKey(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(() -> {
                    LOG.infoContext(requestId,"Value not found in file descriptions file: " + fileName
                                    + " for key: " + key,
                            setDebugMap(resourceUri, resourceId));
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

    private Map<String, Object> setDebugMap(String resourceUri, String resourceId) {

        Map <String, Object> debugMap = new HashMap <>();
        debugMap.put("resource_uri", resourceUri);
        debugMap.put("resource_id",resourceId);

        return debugMap;
    }
}
