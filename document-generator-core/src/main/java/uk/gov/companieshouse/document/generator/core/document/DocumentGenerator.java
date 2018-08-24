package uk.gov.companieshouse.document.generator.core.document;

import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.companieshouse.document.generator.core.avro.AvroDeserializer;
import uk.gov.companieshouse.document.generator.core.document.models.RenderSubmittedDataDocument;
import uk.gov.companieshouse.document.generator.core.kafka.ConsumerGroupHandler;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfo;
import uk.gov.companieshouse.kafka.consumer.CHKafkaConsumerGroup;
import uk.gov.companieshouse.kafka.message.Message;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class DocumentGenerator implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger("document-generator");

    private static final List<String> CONSUMER_TOPICS = Arrays.asList("render-submitted-data-document");

    private static final String GROUP_NAME = "document-generator";
    
    private CHKafkaConsumerGroup documentGeneratorConsumerGroup;

    private DocumentInfoService documentInfoService;

    private AvroDeserializer<RenderSubmittedDataDocument> avroDeserializer;

    @Autowired
    public DocumentGenerator(DocumentInfoService documentInfoService, ConsumerGroupHandler consumerGroupHandler,
                             AvroDeserializer<RenderSubmittedDataDocument> avroDeserializer) {

        this.documentInfoService = documentInfoService;
        this.avroDeserializer = avroDeserializer;

        documentGeneratorConsumerGroup =
                consumerGroupHandler.getConsumerGroup(CONSUMER_TOPICS, GROUP_NAME);
    }

    @Override
    public void run() {

        try {
            pollAndGenerate();
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    /**
     * Poll kafka messages and obtain documentInfo data
     */
    private void pollAndGenerate() {

        for (Message message : documentGeneratorConsumerGroup.consume()) {

            RenderSubmittedDataDocument renderSubmittedDataDocument;

            try {
                //TODO to be used when sending to render service in SFA - 585 sub task
                renderSubmittedDataDocument = avroDeserializer
                        .deserialize(message, RenderSubmittedDataDocument.getClassSchema());

                DocumentInfo documentInfo = documentInfoService.getDocumentInfo();

                documentGeneratorConsumerGroup.commit();
            } catch (Exception e) {
                documentGeneratorConsumerGroup.commit();
                LOG.error(e);
            }
        }
    }
}