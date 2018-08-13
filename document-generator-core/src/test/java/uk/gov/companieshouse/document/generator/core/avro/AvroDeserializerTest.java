package uk.gov.companieshouse.document.generator.core.avro;

import org.apache.avro.AvroRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationStarted;
import uk.gov.companieshouse.kafka.message.Message;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AvroDeserializerTest {

    /** Binary data representing a document generation started message */
    private static final String ENCODED_AVRO_STRING = "requester-idID";

    private static final String ID = "ID";
    private static final String REQUESTER_ID = "requester-id";

    @Test
    public void testDeserialize() throws IOException {
        Message message = new Message();
        message.setValue(ENCODED_AVRO_STRING.getBytes());

        AvroDeserializer<DocumentGenerationStarted> deserializer = new AvroDeserializer<>();
        DocumentGenerationStarted record = deserializer.deserialize(message, DocumentGenerationStarted.getClassSchema());
        assertNotNull(record);
        assertEquals(ID, record.getId());
        assertEquals(REQUESTER_ID, record.getRequesterId());
    }

    @Test
    public void testDeserializeInvalidData() {
        Message message = new Message();
        message.setValue("invalid".getBytes());
        AvroDeserializer<DocumentGenerationStarted> deserializer = new AvroDeserializer<>();

        AvroRuntimeException ex = assertThrows(AvroRuntimeException.class, () ->  deserializer.deserialize(
                message, DocumentGenerationStarted.getClassSchema()));

        assertEquals("Malformed data. Length is negative: -53", ex.getMessage());
    }
}
