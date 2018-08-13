package uk.gov.companieshouse.document.generator.core.kafka.configurationhelper;

import uk.gov.companieshouse.kafka.consumer.ConsumerConfig;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;

import java.util.List;

public interface KafkaConfigHelper {

    /**
     * Generate the config for the  kafka consumer
     *
     * @param consumerTopics
     * @param groupName
     * @return consumerConfig
     */
    ConsumerConfig configureKafkaConsumer(List<String> consumerTopics, String groupName);

    /**
     * Generate the config for the kafka producer
     *
     * @return producerConfig
     */
    ProducerConfig configureKafkaProducer();
}
