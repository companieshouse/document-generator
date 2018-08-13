package uk.gov.companieshouse.document.generator.core.kafka.configurationHelper;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.core.kafka.configurationHelper.impl.KafkaConfigHelperImpl;
import uk.gov.companieshouse.kafka.consumer.ConsumerConfig;
import uk.gov.companieshouse.kafka.producer.ProducerConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class KafkaConfigHelperImplTest {

    @Mock
    private KafkaConsumerProducerConfigHelper kafkaConsumerProducerConfigHelper;

    @InjectMocks
    private KafkaConfigHelperImpl kafkaConfigHelperImpl;

    private static final String GROUP_NAME = "test-group";

    private static final String TOPIC = "test-topic";


    @Test
    @DisplayName("Get the consumer configs successfully")
    public void getConsumerConfig() {

        MockitoAnnotations.initMocks(this);
        List<String> consumerTopics = new ArrayList<>();
        consumerTopics.add(TOPIC);

        doNothing().when(kafkaConsumerProducerConfigHelper).configureConsumerBrokerAddress(any(ConsumerConfig.class));
        ConsumerConfig consumerConfig = kafkaConfigHelperImpl.configureKafkaConsumer(consumerTopics, GROUP_NAME);
        assertNotNull(consumerConfig);
    }

    @Test
    @DisplayName("Get the producer configs successfully")
    public void getProducerConfig() {

        MockitoAnnotations.initMocks(this);
        doNothing().when(kafkaConsumerProducerConfigHelper).configureProducerBrokerAddress(any(ProducerConfig.class));
        ProducerConfig producerConfig = kafkaConfigHelperImpl.configureKafkaProducer();
        assertNotNull(producerConfig);
    }
}
