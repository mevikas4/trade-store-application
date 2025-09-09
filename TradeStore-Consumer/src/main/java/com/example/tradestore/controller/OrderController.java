package com.example.tradestore.controller;

import com.example.tradestore.model.Order;
import com.example.tradestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("{tradeId}")
    public Order getTradeDetails(Long tradeId) {
        return orderService.findTradeById(tradeId);
    }

    @GetMapping
    public List<Order> getAllTrades() {
        return orderService.findAllTrades();
    }

    @PostMapping
    public ResponseEntity<Order> createOrUpdateTrade(@RequestBody Order trade) {
        try {
            System.out.println("Received message: " + trade);
            Order savedTrade = orderService.saveTrade(trade);
            return new ResponseEntity<>(savedTrade, HttpStatus.CREATED);
        } catch (IllegalStateException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}