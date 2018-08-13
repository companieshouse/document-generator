package uk.gov.companieshouse.document.generator.core.kafka.configurationHelper.impl;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.core.kafka.configurationHelper.KafkaConsumerProducerConfigHelper;
import uk.gov.companieshouse.kafka.consumer.ConsumerConfig;
import uk.gov.companieshouse.kafka.consumer.ConsumerConfigHelper;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;
import uk.gov.companieshouse.kafka.producer.ProducerConfigHelper;

@Component
public class KafkaConsumerProducerConfigHelperImpl implements KafkaConsumerProducerConfigHelper {

    /**
     * Assign broker address to the ConsumerConfigHelper
     *
     * @param consumerConfig
     */
    @Override
    public void configureConsumerBrokerAddress (ConsumerConfig consumerConfig) {
        ConsumerConfigHelper.assignBrokerAddresses(consumerConfig);
    }

    /**
     * Assign broker address to the ProducerConfigHelper
     *
     * @param producerConfig
     */
    @Override
    public void configureProducerBrokerAddress (ProducerConfig producerConfig) {
        ProducerConfigHelper.assignBrokerAddresses(producerConfig);
    }
}
