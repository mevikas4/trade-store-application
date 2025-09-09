package com.example.tradestore.service;

import com.example.tradestore.model.Trade;

import java.util.List;

public interface TradeService {

    public Trade saveTrade(Trade newTrade);

    public Trade findTradeById(Long tradeId) ;

    public List<Trade> findAllTrades() ;

    public void updateExpiredTrades();
}
