package uk.gov.companieshouse.document.generator.core.avro;

import org.junit.jupiter.api.Test;
import uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationFailed;
import uk.gov.companieshouse.document.generator.core.document.models.DocumentGenerationStarted;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentGenerationStateAvroSerializerTest {

    private static final String STARTED_ENCODED_AVRO_STRING = "\f123456test guid";
    private static final String COMPLETED_ENCODED_AVRO_STRING = "\f123456test guid test description,description identifier22017-05-22T00:00:00+01:0001/01/1980test-location\n1234L";
    private static final String FAILED_ENCODED_AVRO_STRING = "\f123456 test description,description identifier\nan-id01/01/1980";

    @Test
    public void testSerializeDocumentGenerationStarted() throws IOException {
        DocumentGenerationStarted document = new DocumentGenerationStarted();
        document.setId("test guid");
        document.setRequesterId("123456");

        DocumentGenerationStateAvroSerializer serializer = new DocumentGenerationStateAvroSerializer();
        byte[] result = serializer.serialize(document);
        assertEquals(STARTED_ENCODED_AVRO_STRING, new String(result));
    }

    @Test
    public void testSerializeDocumentGenerationCompleted() throws IOException{
        DocumentGenerationCompleted document = new DocumentGenerationCompleted();
        document.setLocation("test-location");
        document.setId("test guid");
        document.setDescription("test description");
        document.setDescriptionIdentifier("description identifier");
        document.setRequesterId("123456");
        document.setDocumentCreatedAt("2017-05-22T00:00:00+01:00");
        document.setDocumentSize("1234L");

        DescriptionValues descriptionValues = new DescriptionValues();
        descriptionValues.setDate("01/01/1980");
        document.setDescriptionValues(descriptionValues);

        DocumentGenerationStateAvroSerializer serializer = new DocumentGenerationStateAvroSerializer();
        byte[] result = serializer.serialize(document);
        assertEquals(COMPLETED_ENCODED_AVRO_STRING, new String(result));
    }

    @Test
    public void testSerializeDocumentGenerationFailed() throws IOException {
        DocumentGenerationFailed document = new DocumentGenerationFailed();
        document.setDescription("test description");
        document.setDescriptionIdentifier("description identifier");
        document.setRequesterId("123456");
        document.setId("an-id");

        DescriptionValues descriptionValues = new DescriptionValues();
        descriptionValues.setDate("01/01/1980");
        document.setDescriptionValues(descriptionValues);

        DocumentGenerationStateAvroSerializer serializer = new DocumentGenerationStateAvroSerializer();
        byte[] result = serializer.serialize(document);
        String data = new String(result);
        assertEquals(FAILED_ENCODED_AVRO_STRING, new String(result));
    }
}
