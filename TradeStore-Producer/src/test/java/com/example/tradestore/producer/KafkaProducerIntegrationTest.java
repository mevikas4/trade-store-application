package com.example.tradestore.producer;

import com.example.tradestore.model.Trade;
import com.example.tradestore.producer.config.KafkaProducer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
        partitions = 1,
        topics = {"custom-object-topic"}
)
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer",
        "spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer"
})
public class KafkaProducerIntegrationTest {

    private static final String TEST_TOPIC = "custom-object-topic";

    @Autowired
    private KafkaProducer producerService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private Consumer<String, Trade> consumer;

    @BeforeEach
    public void setUp() {
        // 1. Get default consumer properties from the embedded broker
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);

        // 2. IMPORTANT: Configure the JsonDeserializer for the consumer value
        // This tells the consumer to use the JsonDeserializer class
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        // 3. CRITICAL: Specify the target class for the JsonDeserializer to deserialize into
        // Without this, the deserializer defaults to String and the cast fails
        consumerProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Trade.class.getName());

        // CRITICAL: Set auto.offset.reset to "earliest"
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // Create and subscribe the consumer
        consumer = new DefaultKafkaConsumerFactory<String, Trade>(consumerProps).createConsumer();
        consumer.subscribe(Collections.singleton(TEST_TOPIC));
    }

    @AfterEach
    public void tearDown() {
        if (consumer != null) {
            consumer.close();
        }
    }

    @Test
    void whenMessageIsSent_thenItIsReceivedByConsumer() {
        // Arrange
        Trade testTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.now(), Boolean.FALSE);
        // Act
        producerService.sendMessage(TEST_TOPIC,testTrade);

        // Assert
        ConsumerRecord<String, Trade> receivedRecord = KafkaTestUtils.getSingleRecord(consumer, TEST_TOPIC, Duration.ofSeconds(5));
        assertNotNull(receivedRecord);
        Trade receivedObject = receivedRecord.value();
        assertNotNull(receivedObject);
        assertEquals(testTrade, receivedObject);
    }
}