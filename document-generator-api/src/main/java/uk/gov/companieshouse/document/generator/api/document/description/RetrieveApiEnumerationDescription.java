package uk.gov.companieshouse.document.generator.api.document.description;

import java.io.IOException;
import java.util.Map;

public interface RetrieveApiEnumerationDescription {

    /**
     * Get a description from an api enumeration file
     *
     * @param fileName The name of the file to be loaded
     * @param identifier The identifier/key used to select the description
     * @param accountType The type of account
     * @param descriptionValues A map of the description values used to obtain the description
     * @param requestParameters Map containing requestId, resourceId and resourceUri as a key/value pair
     * @return String containing the description
     * @throws IOException
     */
    String getApiEnumerationDescription(String fileName, String identifier, String accountType,
                                        Map<String, String> descriptionValues, Map<String, String> requestParameters) throws IOException;
}
