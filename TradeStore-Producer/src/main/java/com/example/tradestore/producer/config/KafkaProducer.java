package com.example.tradestore.producer.config;

import com.example.tradestore.model.Trade;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, Trade> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Trade> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String TOPIC_NAME,Trade trade) {
        kafkaTemplate.send(TOPIC_NAME, trade);
        System.out.println("Message " + trade +
                " has been successfully sent to the topic: " + TOPIC_NAME);
    }

}