package uk.gov.companieshouse.document.generator.core.document.render;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.core.document.render.impl.ConvertJsonHandlerImpl;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConvertJsonHandlerImplTest {

    @InjectMocks
    ConvertJsonHandlerImpl convertJsonHandler;

    private static final String JSON = "{\"document_size\":12345}";

    @Test
    @DisplayName("test valid convert Json")
    public void testConvert() {

        DocumentGenerationCompleted generatedDocument = convertJsonHandler.convert(JSON);
        assertNotNull(generatedDocument);
        assertEquals("12345", generatedDocument.getDocumentSize());
    }

    @Test
    @DisplayName("test convert null Json")
    public void testConvertNullJson() {
        DocumentGenerationCompleted generatedDocument = convertJsonHandler.convert(null);
        assertNull(generatedDocument);
    }

    @Test
    @DisplayName("test covert empty Json")
    public void testConvertEmptyJson() {
        DocumentGenerationCompleted generatedDocument = convertJsonHandler.convert("");
        assertNull(generatedDocument);
    }
}
