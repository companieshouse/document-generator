package uk.gov.companieshouse.document.generator.descriptions;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.common.descriptions.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.Descriptions;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.DescriptionsFactory;
import uk.gov.companieshouse.document.generator.common.descriptions.yml.impl.FilingDescriptions;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RetrieveApiEnumerationDescriptionTest {

    @InjectMocks
    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    @Mock
    private DescriptionsFactory mockDescriptionsFactory;

    @Mock
    private Descriptions mockDescriptions;

    private static final String FILING_DESCRIPTIONS_FILE_NAME_VALID = "filing_descriptions";

    private static final String DESCRIPTION_IDENTIFIERS_KEY_VALID = "description_identifiers";

    private static final String RESPONSE_WITH_PLACEHOLDER = "Abridged accounts made up to {period_end_on}";

    private static final String RESOURCE_URI = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";

    private static final String REQUEST_ID = "requestId";

    private static Map<String, String> requestParameters;

    @Disabled
    @Test
    @DisplayName("test that a valid response is received when retrieving api enumerations from filing_descriptions.yml")
    public void testValidReturnValueForFilingDescriptions() {

        requestParameters = new HashMap<>();
        requestParameters.put("resource_uri", RESOURCE_URI);
        requestParameters.put("request_id", REQUEST_ID);

        String result = retrieveApiEnumerationDescription.getApiEnumerationDescription(FILING_DESCRIPTIONS_FILE_NAME_VALID,
            DESCRIPTION_IDENTIFIERS_KEY_VALID,"abridged-accounts", requestParameters);

        assertEquals(RESPONSE_WITH_PLACEHOLDER, result);
    }

    private Map<String, Object> loadedYamlTestData() {

        Map<String, Object> testDataMap = new HashMap<>();
        Map<String, String> testData = new HashMap<>();

        testData.put("abridged-accounts", "Abridged accounts made up to {period_end_on}");
        testDataMap.put("description_identifiers", testData);

        return testDataMap;
    }
}
