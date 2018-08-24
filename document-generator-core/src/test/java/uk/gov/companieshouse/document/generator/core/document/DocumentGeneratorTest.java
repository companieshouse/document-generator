package uk.gov.companieshouse.document.generator.core.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.core.avro.AvroDeserializer;
import uk.gov.companieshouse.document.generator.core.document.models.RenderSubmittedDataDocument;
import uk.gov.companieshouse.document.generator.core.kafka.ConsumerGroupHandler;
import uk.gov.companieshouse.document.generator.interfaces.DocumentInfoService;
import uk.gov.companieshouse.document.generator.interfaces.model.DocumentInfo;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.kafka.consumer.CHKafkaConsumerGroup;
import uk.gov.companieshouse.kafka.message.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentGeneratorTest {

    private static final String ID = UUID.randomUUID().toString();
    private static final String TRANSACTIONS_RESOURCE = "transactions";
    private static final String REQUESTER_ID = "requester-id";

    private List<Message> messages;

    private Message message;

    private RenderSubmittedDataDocument renderSubmittedDataDocument;

    private DocumentGenerator documentGenerator;

    @Mock
    private CHKafkaConsumerGroup mockConsumerGroup;

    @Mock
    private DocumentInfoService mockDocumentInfoService;

    @Mock
    private ConsumerGroupHandler mockConsumerGroupHandler;

    @Mock
    private EnvironmentReader mockEnvironmentReader;

    @Mock
    private AvroDeserializer<RenderSubmittedDataDocument> mockAvroDeserializer;

    @BeforeEach
    public void setUp() {

        createTestMessageList();
        createTestRenderSubmittedDataDocument();
    }

    @Test
    @DisplayName("test the polling of Kafka and the consumer group commit")
    public void testPollAndConsumerGroupCommit() throws IOException {

        mockEnvironmentReader();
        mockConsumerGroupWithMessages();
        when(mockAvroDeserializer.deserialize(message, renderSubmittedDataDocument.getSchema())).
                thenReturn(renderSubmittedDataDocument);
        when(mockDocumentInfoService.getDocumentInfo()).thenReturn(populatedDocumentInfo());

        documentGenerator = new DocumentGenerator(mockDocumentInfoService,
                mockConsumerGroupHandler, mockAvroDeserializer, mockEnvironmentReader);

        documentGenerator.run();

        verifyConsumerGroupCommit();
    }

    @Test
    @DisplayName("test the polling to kafka and consumer group commit when exception caught")
    public void testPollAndConsumerGroupCommitWhenExceptionCaught() throws IOException {

        mockEnvironmentReader();
        mockConsumerGroupWithMessages();
        when(mockAvroDeserializer.deserialize(message, renderSubmittedDataDocument.getSchema())).
               thenThrow(new IOException());

        documentGenerator = new DocumentGenerator(mockDocumentInfoService,
                mockConsumerGroupHandler, mockAvroDeserializer, mockEnvironmentReader);

        documentGenerator.run();

        verifyConsumerGroupCommit();
    }

    /**
     * mock the environment reader return
     */
    private void mockEnvironmentReader() {

        when(mockEnvironmentReader.getMandatoryString(any(String.class)))
                .thenReturn("render-submitted-data-document")
                .thenReturn("document-generator");
    }

    /**
     * mock the consumer group with test message data
     */
    private void mockConsumerGroupWithMessages() {

        when(mockConsumerGroupHandler.getConsumerGroup(anyList(), any(String.class))).
                thenReturn(mockConsumerGroup);
        when(mockConsumerGroup.consume()).thenReturn(messages);
    }

    /**
     * verify that a commit occurred to consumer group
     */
    private void verifyConsumerGroupCommit() {
        verify(mockConsumerGroup).commit();
    }

    /**
     * Create test data for documentInfo return
     *
     * @return
     */
    private DocumentInfo populatedDocumentInfo() {

        DocumentInfo documentInfo = new DocumentInfo();
        documentInfo.setAssetId("assetId");
        documentInfo.setTemplateId("TemplateId");
        documentInfo.setData("test-data");

        return documentInfo;
    }

    /**
     * Create test data for RenderSubmittedDataDocument model
     */
    private void createTestRenderSubmittedDataDocument() {

        renderSubmittedDataDocument = new RenderSubmittedDataDocument();
        renderSubmittedDataDocument.setId(ID);
        renderSubmittedDataDocument.setResource(TRANSACTIONS_RESOURCE);
        renderSubmittedDataDocument.setResourceId("/transactions/1/accounts/1");
        renderSubmittedDataDocument.setContentType("content-type");
        renderSubmittedDataDocument.setDocumentType("document-type");
        renderSubmittedDataDocument.setUserId(REQUESTER_ID);
    }

    /**
     * Create message list for test data
     *
     * @return
     */
    private List<Message> createTestMessageList() {

        messages = new ArrayList< >();
        message = new Message();
        message.setKey("test key");
        message.setOffset(100L);
        message.setPartition(123);
        message.setTimestamp(new Date().getTime());
        message.setTopic("topic 1");
        message.setValue("value 1".getBytes());
        messages.add(message);

        return messages;
    }
}
