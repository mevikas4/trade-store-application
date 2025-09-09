package com.example.tradestore.consumer;

import com.example.tradestore.model.Order;
import com.example.tradestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaOrderListener {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "my_first_topic1", groupId = "my_first_topic1-0")
    public void listen(Order order) {
        System.out.println("Received message: " + order);
        orderService.saveTrade(order);
    }
}