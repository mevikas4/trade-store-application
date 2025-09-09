package com.example.tradestore.service;

import com.example.tradestore.model.Order;
import com.example.tradestore.model.Trade;

import java.util.List;

public interface OrderService {
    public Order saveTrade(Order newTrade);

    public Order findTradeById(Long tradeId) ;

    public List<Order> findAllTrades() ;
}
