package com.example.tradestore.service.impl;

import com.example.tradestore.model.Trade;
import com.example.tradestore.repository.TradeRepository;
import com.example.tradestore.service.TradeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    public Trade saveTrade(Trade newTrade) {
        // Business Rule 1: Trades with a maturity date in the past should not be stored.
        if (null != newTrade.getMaturityDate() && newTrade.getMaturityDate().isBefore(LocalDate.now())) {
            System.out.println("Trade maturity date cannot be in the past.");
            throw new IllegalStateException("Trade maturity date cannot be in the past.");
        }
        // Logic to save or update based on tradeId and version
        if (tradeRepository.findByTradeId(newTrade.getTradeId()).isEmpty()){
            // This is a new trade
            System.out.println("Trade Saving in Table");
            return tradeRepository.save(newTrade);
        } else {
            // This is an update
            List<Trade> tradeList = tradeRepository.findByTradeId(newTrade.getTradeId());
            Trade existingTrade =  Collections.max(tradeList, Comparator.comparing(c -> c.getVersion()));
                if (newTrade.getVersion() > existingTrade.getVersion()) {
                    System.out.println("Trade Saving in Table");
                    return tradeRepository.save(newTrade);
                }
                else if (newTrade.getVersion() == existingTrade.getVersion()) {
                    existingTrade.setVersion(newTrade.getVersion());
                    existingTrade.setBookId(newTrade.getBookId());
                    existingTrade.setCounterPartyId(newTrade.getCounterPartyId());
                    existingTrade.setMaturityDate(newTrade.getMaturityDate());
                    existingTrade.setCreatedDate(newTrade.getCreatedDate());
                    existingTrade.setExpired(newTrade.isExpired());
                    System.out.println("Trade Updating in Table");
                    return tradeRepository.save(existingTrade);
                }
                else {
                    // Handle out-of-sequence or outdated trade version
                    System.out.println("Trade version is outdated.");
                    throw new IllegalStateException("Trade version is outdated.");
                }
            }
    }

    public Trade findTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId).orElseThrow(() -> new EntityNotFoundException("Trade not found with ID: " + tradeId));
    }

    public List<Trade> findAllTrades() {
        return tradeRepository.findAll();
    }

    public void updateExpiredTrades() {
        List<Trade> trades = tradeRepository.findAll();
        LocalDate today = LocalDate.now();
        for (Trade trade : trades) {
            System.out.println("trade.getMaturityDate() " + trade.getMaturityDate() + " today " + today);
            if (!trade.isExpired() && trade.getMaturityDate().isBefore(today)) {
                trade.setExpired(true);
                System.out.println("set Expired to true");
                tradeRepository.save(trade);
            }
        }
    }
}
