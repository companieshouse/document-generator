package uk.gov.companieshouse.document.generator.api.document.description.impl;

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

    private static final String RESOURCE_URI = "resource_uri";

    private static final String REQUEST_ID = "request_id";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApiEnumerationDescription(String fileName, String identifier, String accountType,
                                               Map<String, String> requestParameters)
            throws IOException {


        Yaml yaml = new Yaml();
        File descriptionsFile = new File(fileName);

        String description = "";

        LOG.infoContext(requestParameters.get(REQUEST_ID),"obtaining file for api enumerations with file name: " +
                descriptionsFile, setDebugMap(requestParameters));
        try (InputStream inputStream = new FileInputStream(descriptionsFile)) {

            LOG.infoContext(requestParameters.get(REQUEST_ID),"The file: " + descriptionsFile + " has been found, obtaining descriptions",
                    setDebugMap(requestParameters));
            Map<String, Object> descriptions = (Map<String, Object>) yaml.load(inputStream);
            Map<String, Object> filteredDescriptions = (Map<String, Object>) getDescriptionsValue(descriptions,
                    identifier, fileName, requestParameters);
            if(filteredDescriptions != null) {
                description =  String.valueOf(getDescriptionsValue(filteredDescriptions, accountType, fileName,
                        requestParameters));
            }
        } catch (FileNotFoundException e) {
            LOG.errorContext(requestParameters.get(REQUEST_ID),"file not found when obtain api enumeration " +
                    "descriptions for file name: " + descriptionsFile, e, setDebugMap(requestParameters));
        }

        return description;
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
                                               Map<String, String> requestParameters) {

        LOG.infoContext(requestParameters.get(REQUEST_ID),"getting value from the file descriptions file: "
                + fileName + " using key: " + key, setDebugMap(requestParameters));
        return descriptions.entrySet().stream()
                .filter(descriptionsEntrySet -> descriptionsEntrySet.getKey().equals(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(() -> {
                    LOG.infoContext(requestParameters.get(REQUEST_ID),"Value not found in file descriptions file: "
                                    + fileName + " for key: " + key,
                            setDebugMap(requestParameters));
                    return null;
                });
    }

    private Map<String, Object> setDebugMap(Map<String, String> requestParameters) {

        Map <String, Object> debugMap = new HashMap <>();
        debugMap.put(RESOURCE_URI, requestParameters.get(RESOURCE_URI));

        return debugMap;
    }
}
