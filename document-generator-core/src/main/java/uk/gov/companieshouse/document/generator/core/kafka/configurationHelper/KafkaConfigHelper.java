package uk.gov.companieshouse.document.generator.core.kafka.configurationHelper;

import uk.gov.companieshouse.kafka.consumer.ConsumerConfig;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;

import java.util.List;

public interface KafkaConfigHelper {

    /**
     * Configure the kafka consumer
     *
     * @param consumerTopics
     * @param groupName
     * @return consumerConfig
     */
    public ConsumerConfig configureKafkaConsumer(List<String> consumerTopics, String groupName);

    /**
     * Configure the kafka producer
     *
     * @return producerConfig
     */
    public ProducerConfig configureKafkaProducer();
}
