package uk.gov.companieshouse.document.generator.core.avro;

import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationStarted;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvroSerializerTest {

    /** Binary data representing a document generation started message */
    private static final String ENCODED_AVRO_STRING = "requester-idID";

    @Test
    public void testSerialize() throws IOException {
        AvroSerializer<DocumentGenerationStarted> serializer = new AvroSerializer<>();
        byte[] result = serializer.serialize(createTestData());
        assertEquals(ENCODED_AVRO_STRING, new String(result));
    }

    /**
     * Create a sample document generation started to use as test data
     *
     * @return documentGenerationStarted Data for the start of the document generation process
     */
    private DocumentGenerationStarted createTestData() {
        DocumentGenerationStarted started = new DocumentGenerationStarted();
        started.setId("ID");
        started.setRequesterId("requester-id");

        return started;
    }
}
