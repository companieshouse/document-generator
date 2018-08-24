package uk.gov.companieshouse.document.generator.core.avro;


import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationFailed;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationStarted;

import java.io.IOException;

@Component
public class DocumentGenerationStateAvroSerializer {

    /**
     * Serialize the message for the completion of the document generation.
     *
     * @return
     * @throws IOException
     */
    public byte[] serialize() throws IOException {
        return serialize();
    }

    /**
     * Serialize the message for the completion of the document generation.
     *
     * @param document
     * @return
     * @throws IOException
     */
    public byte[] serialize(DocumentGenerationCompleted document) throws IOException {
        AvroSerializer<DocumentGenerationCompleted> serializer = new AvroSerializer<>();
        return serializer.serialize(document);
    }

    /**
     * Serialize the message for the start of the document generation.
     *
     * @param started
     * @return
     * @throws IOException
     */
    public byte[] serialize(DocumentGenerationStarted started) throws IOException {
        AvroSerializer<DocumentGenerationStarted> serializer = new AvroSerializer<>();
        return serializer.serialize(started);
    }

    /**
     * Serialize the message for the document generation failure.
     *
     * @param failed
     * @return bytes
     * @throws IOException
     */
    public byte[] serialize(DocumentGenerationFailed failed) throws IOException {
        AvroSerializer<DocumentGenerationFailed> serializer = new AvroSerializer<>();
        return serializer.serialize(failed);
    }
}
