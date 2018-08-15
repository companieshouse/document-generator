package uk.gov.companieshouse.document.generator.core.avro;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;

/**
 * Serializes an object into binary data using Avro.
 *
 * @param <T> Type of object to be serialized
 */
public class AvroSerializer<T extends SpecificRecordBase> {

    /**
     * Serializes the object provided into binary data
     *
     * @param data The data in an object
     * @return serializedData The binary data
     * @throws IOException
     */
    public byte[] serialize(T data) throws IOException {
        DatumWriter<T> datumWriter = new SpecificDatumWriter<>();

        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            datumWriter.setSchema(data.getSchema());
            datumWriter.write(data, encoder);
            encoder.flush();

            byte[] serializedData = out.toByteArray();
            encoder.flush();

            return serializedData;
        }
    }
}
