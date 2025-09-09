package com.example.tradestore;

import com.example.tradestore.model.Trade;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.Duration;
import java.util.Properties;

@SpringBootApplication
@EnableKafka
@EnableScheduling
public class TradeStoreConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeStoreConsumerApplication.class, args);
    }
}

