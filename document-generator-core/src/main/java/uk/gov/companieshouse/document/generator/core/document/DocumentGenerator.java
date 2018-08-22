package uk.gov.companieshouse.document.generator.core.document;

import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.companieshouse.document.generator.core.avro.AvroDeserializer;
import uk.gov.companieshouse.document.generator.core.document.models.RenderSubmittedDataDocument;
import uk.gov.companieshouse.document.generator.core.kafka.ConsumerGroupHandler;
import uk.gov.companieshouse.document.generator.core.kafka.configurationhelper.KafkaConfigHelper;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfo;
import uk.gov.companieshouse.kafka.consumer.CHKafkaConsumerGroup;
import uk.gov.companieshouse.kafka.message.Message;

import java.util.Arrays;
import java.util.List;

public class DocumentGenerator implements Runnable {

    private static final List<String> CONSUMER_TOPICS = Arrays.asList("render-submitted-data-document");

    private static final String GROUP_NAME = "document-generator";
    
    private CHKafkaConsumerGroup documentGeneratorConsumerGroup;

    private KafkaConfigHelper kafkaConfigHelper;

    private DocumentInfoService documentInfoService;

    private ConsumerGroupHandler consumerGroupHandler;

    private AvroDeserializer<RenderSubmittedDataDocument> avroDeserializer;

    @Autowired
    public DocumentGenerator(KafkaConfigHelper kafkaConfigHelper, DocumentInfoService documentInfoService,
                             ConsumerGroupHandler consumerGroupHandler,
                             AvroDeserializer<RenderSubmittedDataDocument> avroDeserializer) {

        this.kafkaConfigHelper = kafkaConfigHelper;
        this.documentInfoService = documentInfoService;
        this.consumerGroupHandler = consumerGroupHandler;
        this.avroDeserializer = avroDeserializer;

        documentGeneratorConsumerGroup =
                consumerGroupHandler.getConsumerGroup(CONSUMER_TOPICS, GROUP_NAME);
    }

    @Override
    public void run() {

        try {
            pollAndGenerate(documentGeneratorConsumerGroup);
        } catch (Exception e) {

        }
    }

    /**
     * Poll kafka messages and obtain documentInfo data
     *
     * @param documentGeneratorConsumerGroup
     */
    private void pollAndGenerate(CHKafkaConsumerGroup documentGeneratorConsumerGroup) {

        for (Message message : documentGeneratorConsumerGroup.consume()) {

            RenderSubmittedDataDocument renderSubmittedDataDocument;

            try {
                renderSubmittedDataDocument = avroDeserializer
                        .deserialize(message, RenderSubmittedDataDocument.getClassSchema());

                DocumentInfo documentInfo = documentInfoService.getDocumentInfo();

                documentGeneratorConsumerGroup.commit();
            } catch (Exception e) {
                documentGeneratorConsumerGroup.commit();
            }
        }
    }
}