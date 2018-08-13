package uk.gov.companieshouse.document.generator.core.avro;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import uk.gov.companieshouse.kafka.message.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Deserializes binary data into an object using Avro.
 *
 * @param <T> Type of object to be deserialized
 */
public class AvroDeserializer<T extends SpecificRecordBase> {

    /**
     * Deserializes a message in binary format into T.
     *
     * @param message
     * @return T
     * @throws IOException
     */
    public T deserialize(Message message, Schema schema) throws IOException {
        DatumReader<T> reader = new SpecificDatumReader<>();
        reader.setSchema(schema);

        try(ByteArrayInputStream in = new ByteArrayInputStream(message.getValue())) {
            Decoder decoder = DecoderFactory.get().binaryDecoder(in, null);
            return reader.read(null, decoder);
        }
    }
}
