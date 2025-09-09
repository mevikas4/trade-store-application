package com.example.tradestore.producer.config;

import com.example.tradestore.model.Trade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTest {

    // We use a constant for the topic name to avoid hardcoding it in the test
    private final String TOPIC_NAME = "my_first_topic1";
    // The KafkaTemplate dependency is mocked to prevent actual Kafka broker communication
    @Mock
    private KafkaTemplate<String, Trade> kafkaTemplate;
    // The KafkaProducer class is injected with the mocked KafkaTemplate
    @InjectMocks
    private KafkaProducer kafkaProducer;

    /**
     * Test case for the successful sending of a message.
     * This test verifies that the `send` method on the mocked KafkaTemplate is
     * called exactly once with the correct topic name and Trade object.
     */
    @Test
    void whenSendMessage_thenKafkaTemplateSendIsCalled() {
        // Arrange
        // Create a sample Trade object for testing
        Trade testTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now(), LocalDate.now(), Boolean.FALSE);

        // Act
        // Call the method to be tested
        kafkaProducer.sendMessage(TOPIC_NAME,testTrade);

        // Assert
        // Verify that the kafkaTemplate's send() method was called with the
        // expected topic and trade object
        verify(kafkaTemplate).send(TOPIC_NAME, testTrade);
    }
}


