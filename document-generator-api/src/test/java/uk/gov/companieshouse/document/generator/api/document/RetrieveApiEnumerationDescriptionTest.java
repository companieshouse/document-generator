package uk.gov.companieshouse.document.generator.api.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.document.generator.api.document.description.RetrieveApiEnumerationDescription;
import uk.gov.companieshouse.document.generator.api.document.description.impl.RetrieveApiEnumerationDescriptionImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetrieveApiEnumerationDescriptionTest {

    private static final String FILING_DESCRIPTIONS_FILE_NAME_VALID = "api-enumerations/filing_descriptions.yml";

    private static final String FILING_DESCRIPTIONS_FILE_NAME_INVALID = "api-enum";

    private static final String DESCRIPTION_IDENTIFIERS_KEY_VALID = "description_identifiers";

    private static final String DESCRIPTION_IDENTIFIERS_KEY_INVALID = "desc";

    private static final String POPULATED_RESPONSE = "Abridged accounts made up to 01 October 2018";

    private static final String EMPTY_RESPONSE = "";

    private static final String RESOURCE_ID = "/transactions/091174-913515-326060";

    private static final String RESOURCE_URI = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";

    private static final String REQUEST_ID = "requestId";

    private RetrieveApiEnumerationDescription retrieveApiEnumerationDescription;

    @BeforeEach
    public void setUp() {
        retrieveApiEnumerationDescription = new RetrieveApiEnumerationDescriptionImpl();
    }

    @Test
    @DisplayName("test that a valid response is received when retrieving api enumerations with a valid file name")
    public void testValidReturnValue() throws IOException {

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("period_end_on", "01 October 2018");

        String result = retrieveApiEnumerationDescription.getApiEnumerationDescription(FILING_DESCRIPTIONS_FILE_NAME_VALID,
                DESCRIPTION_IDENTIFIERS_KEY_VALID,"abridged-accounts", descriptionValues,
                REQUEST_ID, RESOURCE_URI, RESOURCE_ID);

        assertEquals(POPULATED_RESPONSE, result);
    }

    @Test
    @DisplayName("test Null is returned when file not found")
    public void testNullReturnedWhenFileNotFound() throws IOException {

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("period_end_on", "01 October 2018");

        String result = retrieveApiEnumerationDescription.getApiEnumerationDescription(FILING_DESCRIPTIONS_FILE_NAME_INVALID,
                DESCRIPTION_IDENTIFIERS_KEY_VALID,"abridged-accounts", descriptionValues,
                REQUEST_ID, RESOURCE_URI, RESOURCE_ID);

        assertEquals(EMPTY_RESPONSE, result);
    }

    @Test
    @DisplayName("test Null is returned when no description found")
    public void testNullReturnedWhenDescriptionNotFound() throws IOException {

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("period_end_on", "01 October 2018");

        String result = retrieveApiEnumerationDescription.getApiEnumerationDescription(FILING_DESCRIPTIONS_FILE_NAME_VALID,
                DESCRIPTION_IDENTIFIERS_KEY_INVALID,"abridged-accounts", descriptionValues,
                REQUEST_ID, RESOURCE_URI, RESOURCE_ID);

        assertEquals(EMPTY_RESPONSE, result);
    }
}
