package com.example.tradestore.consumer;

import com.example.tradestore.model.Trade;
import com.example.tradestore.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaTradeListener {

    @Autowired
    private TradeService tradeService;

    @KafkaListener(topics = "my_first_topic1", groupId = "my_first_topic1-0")
    public void listen(Trade trade) {
        System.out.println("Received message: " + trade);
        tradeService.saveTrade(trade);
    }
}