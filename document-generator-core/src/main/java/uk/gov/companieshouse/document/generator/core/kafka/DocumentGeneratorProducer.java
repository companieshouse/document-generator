package uk.gov.companieshouse.document.generator.core.kafka;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import uk.gov.companieshouse.kafka.message.Message;
import uk.gov.companieshouse.kafka.producer.Acks;
import uk.gov.companieshouse.kafka.producer.CHKafkaProducer;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;
import uk.gov.companieshouse.kafka.producer.ProducerConfigHelper;

public class DocumentGeneratorProducer {
    
    private CHKafkaProducer producer;

    /**
     * Create a producer
     */
    public DocumentGeneratorProducer() {
        ProducerConfig config = new ProducerConfig();
        ProducerConfigHelper.assignBrokerAddresses(config);
        config.setRoundRobinPartitioner(true);
        config.setAcks(Acks.WAIT_FOR_ALL);
        config.setRetries(10);

        producer = new CHKafkaProducer(config);
    }

    /**
     * write the document data to Apache Kafka from producer
     * 
     * @param data
     * @param topic
     */
    public void sendDocumentGenerationMessage(byte[] data, String topic) throws ExecutionException, InterruptedException {
        Message message = new Message();
        message.setValue(data);
        message.setTopic(topic);
        message.setTimestamp(new Date().getTime());

        producer.send(message);
    }
}
