package com.example.tradestore.service.impl;

import com.example.tradestore.model.Order;
import com.example.tradestore.repository.OrderRepository;
import com.example.tradestore.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order saveTrade(Order newOrder) {
        // Business Rule 1: Trades with a maturity date in the past should not be stored.
        if (newOrder.getMaturityDate().isBefore(LocalDate.now())) {
            System.out.println("Trade maturity date cannot be in the past.");
            throw new IllegalStateException("Trade maturity date cannot be in the past.");
        }
        // Logic to save or update based on tradeId and version
        if (orderRepository.findByTradeId(newOrder.getTradeId()).isEmpty()){
            // This is a new trade
            System.out.println("Trade Saving in Table");
            return orderRepository.save(newOrder);
        } else {
            // This is an update
            List<Order> tradeList = orderRepository.findByTradeId(newOrder.getTradeId());
            Order existingOrder =  Collections.max(tradeList, Comparator.comparing(c -> c.getVersion()));
                if (newOrder.getVersion() > existingOrder.getVersion()) {
                    System.out.println("Trade Saving in Table");
                    return orderRepository.save(newOrder);
                }
                else if (newOrder.getVersion() == existingOrder.getVersion()) {
                    existingOrder.setVersion(newOrder.getVersion());
                    existingOrder.setBookId(newOrder.getBookId());
                    existingOrder.setCounterPartyId(newOrder.getCounterPartyId());
                    existingOrder.setMaturityDate(newOrder.getMaturityDate());
                    existingOrder.setCreatedDate(newOrder.getCreatedDate());
                    existingOrder.setExpired(newOrder.isExpired());
                    System.out.println("Trade Updating in Table");
                    return orderRepository.save(existingOrder);
                }
                else {
                    // Handle out-of-sequence or outdated trade version
                    System.out.println("Trade version is outdated.");
                    throw new IllegalStateException("Trade version is outdated.");
                }
            }
    }

    public Order findTradeById(Long tradeId) {
        return orderRepository.findById(tradeId).orElseThrow(() -> new EntityNotFoundException("Trade not found with ID: " + tradeId));
    }

    public List<Order> findAllTrades() {
        return orderRepository.findAll();
    }

    public void updateExpiredTrades() {
        List<Order> trades = orderRepository.findAll();
        LocalDate today = LocalDate.now();
        for (Order trade : trades) {
            if (!trade.isExpired() && trade.getMaturityDate().isEqual(today)) {
                trade.setExpired(true);
                orderRepository.save(trade);
            }
        }
    }
}
