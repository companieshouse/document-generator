package uk.gov.companieshouse.document.generator.core.kafka.configurationHelper;

import uk.gov.companieshouse.kafka.consumer.ConsumerConfig;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;

public interface KafkaConsumerProducerConfigHelper {

    /**
     * Assign broker address to the ConsumerConfigHelper
     *
     * @param consumerConfig
     */
    public void configureConsumerBrokerAddress (ConsumerConfig consumerConfig);

    /**
     * Assign broker address to the ProducerConfigHelper
     *
     * @param producerConfig
     */
    public void configureProducerBrokerAddress (ProducerConfig producerConfig);
}
