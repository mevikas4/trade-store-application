package com.example.tradestore.producer.controller;

import com.example.tradestore.producer.config.KafkaProducer;
import com.example.tradestore.model.Trade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final KafkaProducer kafkaProducer;
    private final String TOPIC_NAME = "my_first_topic1"; // Replace with your desired topic name

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/send")
    public void sendMessageToKafka(@RequestBody Trade trade) {
        kafkaProducer.sendMessage(TOPIC_NAME,trade);
    }
}