package uk.gov.companieshouse.document.generator.api.document.description;

import java.io.IOException;
import java.util.Map;

public interface RetrieveApiEnumerationDescription {

    String getApiEnumerationDescription(String fileName, String identifier, String accountType,
                                        Map<String, String> descriptionValues, String requestId, String resourceUri,
                                        String resourceId) throws IOException;
}
