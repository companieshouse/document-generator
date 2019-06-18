package uk.gov.companieshouse.document.generator.descriptions;

import org.junit.jupiter.api.BeforeEach;
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
    private DescriptionsFactory descriptionsFactory;

    @Mock
    Descriptions descriptions;

    private static final String RESOURCE_URI = "/transactions/091174-913515-326060/accounts/xU-6Vebn7F8AgLwa2QHBUL2yRpk=";

    private static final String REQUEST_ID = "requestId";

    private static final String DESCRIPTIONS = "descriptions";
    private static final String DESCRIPTION_ONE = "description_one";
    private static final String DESC_ONE_DATA = "Data from description one";
    private static final String DESCRIPTION_TWO = "description_two";
    private static final String DESC_TWO_DATA = "Data from description two";

    private static final String ITEMS = "items";
    private static final String ITEM_ONE = "items_one";
    private static final String ITEM_ONE_DATA = "Data from item one";
    private static final String ITEM_TWO = "item_two";
    private static final String ITEM_TWO_DATA = "Data from item two";

    private static Map<String, String> requestParameters;

    @BeforeEach()
    public void setUp() {

        requestParameters = new HashMap<>();
        requestParameters.put("resource_uri", RESOURCE_URI);
        requestParameters.put("request_id", REQUEST_ID);

        when(descriptionsFactory.createDescription(anyString())).thenReturn(descriptions);
        when(descriptions.getData()).thenReturn(yamlTestData());
    }

    @Test
    @DisplayName("test that a valid response is received when retrieving api enumerations for description two")
    public void testValidReturnValue() {

        String result = retrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(),
            DESCRIPTIONS, DESCRIPTION_TWO, requestParameters);

        assertEquals(DESC_TWO_DATA, result);
    }

    @Test
    @DisplayName("test that an empty value is returned when identifier not present in api enumerations")
    public void testEmptyValueReturned() {

        String result = retrieveApiEnumerationDescription.getApiEnumerationDescription(anyString(),
            "invalid_tag", DESCRIPTION_TWO, requestParameters);

        assertEquals("", result);
    }

    private Map<String, Object> yamlTestData() {

        Map<String, Object> testDataMap = new HashMap<>();
        Map<String, String> testDataOne = new HashMap<>();
        Map<String, String> testDataTwo = new HashMap<>();

        testDataOne.put(DESCRIPTION_ONE, DESC_ONE_DATA);
        testDataOne.put(DESCRIPTION_TWO, DESC_TWO_DATA);
        testDataMap.put(DESCRIPTIONS, testDataOne);

        testDataTwo.put(ITEM_ONE, ITEM_ONE_DATA);
        testDataTwo.put(ITEM_TWO, ITEM_TWO_DATA);
        testDataMap.put(ITEMS, testDataTwo);

        return testDataMap;
    }
}
