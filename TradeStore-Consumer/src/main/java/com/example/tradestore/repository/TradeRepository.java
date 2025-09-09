package com.example.tradestore.repository;

import com.example.tradestore.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Trade entities.
 * By extending JpaRepository, Spring Data automatically provides
 * standard CRUD (Create, Read, Update, Delete) methods.
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    /**
     * Finds a list of all trades, sorted by the tradeId in ascending order.
     * This provides a simple way to retrieve trades in their storage sequence.
     *
     * @return A List of Trade objects, ordered by tradeId.
     */
    List<Trade> findAll();
    /**
     * Finds a trade by its unique tradeId.
     *
     * @param tradeId The unique identifier of the trade.
     * @return An Optional containing the found Trade, or an empty Optional if not found.
     */
    List<Trade> findByTradeId(Long tradeId);

}