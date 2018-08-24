package uk.gov.companieshouse.document.generator.core.kafka;

import uk.gov.companieshouse.kafka.consumer.CHKafkaConsumerGroup;

import java.util.List;

public interface ConsumerGroupHandler {
    /**
     * get the consumerGroup
     *
     * @param consumerTopics
     * @param groupName
     * @return
     */
    CHKafkaConsumerGroup getConsumerGroup(List<String> consumerTopics, String groupName);
}
