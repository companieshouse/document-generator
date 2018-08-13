package uk.gov.companieshouse.document.generator.core.kafka.configurationHelper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.companieshouse.document.generator.core.kafka.configurationHelper.KafkaConfigHelper;
import uk.gov.companieshouse.document.generator.core.kafka.configurationHelper.KafkaBrokerConfigHelper;
import uk.gov.companieshouse.kafka.consumer.ConsumerConfig;
import uk.gov.companieshouse.kafka.producer.Acks;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;

import java.util.List;

public class KafkaConfigHelperImpl implements KafkaConfigHelper {

    @Autowired
    private KafkaBrokerConfigHelper kafkaBrokerConfigHelper;

    /**
     * Configure the kafka consumer
     *
     * @param consumerTopics
     * @param groupName
     * @return consumerConfig
     */
    @Override
    public ConsumerConfig configureKafkaConsumer(List<String> consumerTopics, String groupName) {

        ConsumerConfig consumerConfig = new ConsumerConfig();

        consumerConfig.setTopics(consumerTopics);
        consumerConfig.setGroupName(groupName);
        consumerConfig.setResetOffset(false);

        kafkaBrokerConfigHelper.configureConsumerBrokerAddress(consumerConfig);

        return consumerConfig;
    }

    /**
     * Configure the kafka producer
     *
     * @return producerConfig
     */
    @Override
    public ProducerConfig configureKafkaProducer() {

        ProducerConfig producerConfig = new ProducerConfig();

        producerConfig.setRoundRobinPartitioner(true);
        producerConfig.setAcks(Acks.WAIT_FOR_ALL);
        producerConfig.setRetries(10);

        kafkaBrokerConfigHelper.configureProducerBrokerAddress(producerConfig);

        return producerConfig;
    }

}
