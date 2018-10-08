package uk.gov.companieshouse.document.generator.api.document;

import java.io.IOException;
import java.util.Map;

public interface RetrieveApiEnumerationDescription {

    String getApiEnumerationDescription(String fileName, String identifier, String accountType,
                                        Map<String, String> descriptionValues) throws IOException;
}
