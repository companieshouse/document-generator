package uk.gov.companieshouse.document.generator.core.kafka.configurationhelper;


import uk.gov.companieshouse.kafka.consumer.ConsumerConfig;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;

public interface KafkaBrokerConfigHelper {

    /**
     * Assign broker address to the ConsumerConfigHelper
     *
     * @param consumerConfig
     */
    void configureConsumerBrokerAddress (ConsumerConfig consumerConfig);

    /**
     * Assign broker address to the ProducerConfigHelper
     *
     * @param producerConfig
     */
    void configureProducerBrokerAddress (ProducerConfig producerConfig);
}
