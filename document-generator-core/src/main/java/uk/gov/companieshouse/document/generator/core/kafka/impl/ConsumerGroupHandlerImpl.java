package uk.gov.companieshouse.document.generator.core.kafka.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.companieshouse.document.generator.core.kafka.ConsumerGroupHandler;
import uk.gov.companieshouse.document.generator.core.kafka.configurationhelper.KafkaConfigHelper;
import uk.gov.companieshouse.kafka.consumer.CHKafkaConsumerGroup;

import java.util.List;

@Component
public class ConsumerGroupHandlerImpl implements ConsumerGroupHandler {

    @Autowired
    private KafkaConfigHelper kafkaConfigHelper;

    /**
     * get the consumer group
     *
     * @param cosumerTopics
     * @param groupName
     * @return
     */
    @Override
    public CHKafkaConsumerGroup getConsumerGroup(List<String> cosumerTopics, String groupName) {
        return new CHKafkaConsumerGroup(kafkaConfigHelper
                .configureKafkaConsumer(cosumerTopics, groupName));
    }
}
