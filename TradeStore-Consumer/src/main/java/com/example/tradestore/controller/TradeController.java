package com.example.tradestore.controller;

import com.example.tradestore.model.Trade;
import com.example.tradestore.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @GetMapping("{tradeId}")
    public Trade getTradeDetails(Long tradeId) {
        return tradeService.findTradeById(tradeId); // Implement this in your service
    }

    @GetMapping
    public List<Trade> getAllTrades() {
        return tradeService.findAllTrades(); // Implement this in your service
    }

    @PostMapping
    public ResponseEntity<Trade> createOrUpdateTrade(@RequestBody Trade trade) {
        try {
            System.out.println("Received message: " + trade);
            Trade savedTrade = tradeService.saveTrade(trade);
            return new ResponseEntity<>(savedTrade, HttpStatus.CREATED);
        } catch (IllegalStateException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}