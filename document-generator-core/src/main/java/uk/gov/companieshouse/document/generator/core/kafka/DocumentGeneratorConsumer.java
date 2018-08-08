package uk.gov.companieshouse.document.generator.core.kafka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.gov.companieshouse.kafka.consumer.CHKafkaConsumerGroup;
import uk.gov.companieshouse.kafka.consumer.ConsumerConfig;
import uk.gov.companieshouse.kafka.consumer.ConsumerConfigHelper;
import uk.gov.companieshouse.kafka.message.Message;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

public class DocumentGeneratorConsumer {
    
    private static final Logger LOG = LoggerFactory.getLogger("document-generator-core");

    private CHKafkaConsumerGroup consumerGroup;

    /**
     * configure the DocumentGeneratorConsumerGroup to assign the broker address and connect
     * 
     * @param consumerTopics
     * @param groupName
     */
    public DocumentGeneratorConsumer(List < String > consumerTopics, String groupName) {
        
        ConsumerConfig config = constructConsumerConfig(consumerTopics, groupName);
        ConsumerConfigHelper.assignBrokerAddresses(config);
        generateLog(consumerTopics, groupName, config);

        consumerGroup = new CHKafkaConsumerGroup(config);
        consumerGroup.connect();
    }

    /**
     * poll the consumer group to consume messages from a List
     * 
     * @return
     */
    public List < Message > pollConsumerGroup() {
        return consumerGroup.consume();
    }

    /**
     * close the consumer group
     */
    public void closeConsumerGroup() {
        consumerGroup.close();
    }

    /**
     * commit the consumer group
     */
    public void commit() {
        consumerGroup.commit();
    }

    /**
     * process the message again
     * 
     * @param message
     */
    public void reprocess(Message message) {
        consumerGroup.reprocess(message);
    }
    
    /**
     * generate log for structured logging
     * 
     * @param consumerTopics
     * @param groupName
     * @param config
     */
    private void generateLog(List<String> consumerTopics, String groupName, ConsumerConfig config) {
        
        Map < String, Object > logData = new HashMap < > ();
        
        logData.put("kafka_broker_addresses", Arrays.toString(config.getBrokerAddresses()));
        logData.put("topic", consumerTopics);
        logData.put("group name", groupName);
        
        LOG.trace("Connecting to Kafka consumer group", logData);
    }
    
    /**
     * create the consumer configuration
     * 
     * @param consumerTopics
     * @param groupName
     * @return
     */
    private ConsumerConfig constructConsumerConfig(List<String> consumerTopics, String groupName) {
        
        ConsumerConfig config = new ConsumerConfig();
        
        config.setTopics(consumerTopics);
        config.setGroupName(groupName);
        config.setResetOffset(false);
        
        return config;
    }
}
