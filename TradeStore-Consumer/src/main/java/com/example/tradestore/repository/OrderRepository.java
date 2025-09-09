package com.example.tradestore.repository;

import com.example.tradestore.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Repository interface for managing Order entities.
 * By extending MongoRepository, Spring Data automatically provides
 * standard CRUD (Create, Read, Update, Delete) methods.
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

    /**
     * Finds a list of all trades, sorted by the tradeId in ascending order.
     * This provides a simple way to retrieve trades in their storage sequence.
     *
     * @return A List of Trade objects, ordered by tradeId.
     */
    List<Order> findAll();
    /**
     * Finds a trade by its unique tradeId.
     *
     * @param tradeId The unique identifier of the trade.
     * @return An Optional containing the found Trade, or an empty Optional if not found.
     */
    List<Order> findByTradeId(Long tradeId);

}