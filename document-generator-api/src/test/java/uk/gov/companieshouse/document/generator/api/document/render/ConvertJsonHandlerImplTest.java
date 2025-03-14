package uk.gov.companieshouse.document.generator.api.document.render;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.api.document.render.impl.ConvertJsonHandlerImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConvertJsonHandlerImplTest {

    @InjectMocks
    ConvertJsonHandlerImpl convertJsonHandler;

    private static final String JSON = "{\"document_size\":12345}";

    @Test
    @DisplayName("test valid convert Json")
    public void testConvert() throws JSONException {

        String covertedData = convertJsonHandler.convert(JSON);
        assertNotNull(covertedData);
        assertEquals("12345", covertedData);
    }

    @Test
    @DisplayName("test convert null Json")
    public void testConvertNullJson() throws JSONException {
        String covertedData = convertJsonHandler.convert(null);
        assertNull(covertedData);
    }

    @Test
    @DisplayName("test covert empty Json")
    public void testConvertEmptyJson() throws JSONException {
        String covertedData = convertJsonHandler.convert("");
        assertNull(covertedData);
    }
}
