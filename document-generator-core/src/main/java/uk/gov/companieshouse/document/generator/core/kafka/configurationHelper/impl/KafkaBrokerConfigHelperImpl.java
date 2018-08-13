package uk.gov.companieshouse.document.generator.core.kafka.configurationHelper.impl;

import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.core.kafka.configurationHelper.KafkaBrokerConfigHelper;
import uk.gov.companieshouse.kafka.consumer.ConsumerConfig;
import uk.gov.companieshouse.kafka.consumer.ConsumerConfigHelper;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;
import uk.gov.companieshouse.kafka.producer.ProducerConfigHelper;

@Component
public class KafkaBrokerConfigHelperImpl implements KafkaBrokerConfigHelper {

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
