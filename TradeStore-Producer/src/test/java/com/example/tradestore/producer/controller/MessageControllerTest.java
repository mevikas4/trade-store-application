package com.example.tradestore.producer.controller;

import com.example.tradestore.model.Trade;
import com.example.tradestore.producer.config.KafkaProducer;
import com.example.tradestore.producer.controller.MessageController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private MessageController messageController;

    private Trade sampleTrade;
    private final String TOPIC_NAME = "my_first_topic1";
    @BeforeEach
    void setUp() {
        // Build the MockMvc instance for the controller under test
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();

        // Create a sample Trade object to be used in the test
        sampleTrade = new Trade(
                "T101",
                1,
                "C1",
                "B1",
               null,
                null,
                false
        );
    }

    @Test
    void testSendMessageToKafka() throws Exception {
        // Arrange
        // The setup method already provides the necessary mock and controller instance
        // Act
        // Perform a POST request to the /send endpoint with the Trade object as JSON
        mockMvc.perform(post("/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleTrade)))
                .andExpect(status().isOk());

        // Assert
        // Verify that the sendMessage method on the mocked KafkaProducer was called
        // exactly once, with the sampleTrade object as an argument.
        verify(kafkaProducer, times(1)).sendMessage(TOPIC_NAME,sampleTrade);
    }

    @Test
    void testSendMessageToKafka_nullTrade() throws Exception {
        // Act and Assert
        // Perform a POST request with a null body and expect a Bad Request status
        mockMvc.perform(post("/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        // Assert that the producer method was never called
        verify(kafkaProducer, times(0)).sendMessage(TOPIC_NAME,null);
    }
}
