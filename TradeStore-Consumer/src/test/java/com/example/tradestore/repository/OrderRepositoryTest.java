package com.example.tradestore.repository;

import com.example.tradestore.model.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository tradeRepository;

    private Order createTrade(Long id, Long tradeId, int version) {
        return new Order(id,tradeId, version, "CP-" + tradeId, "B" + tradeId,
                LocalDate.now().plusDays(10), LocalDate.now(), false);
    }

    @Test
    @DisplayName("Should save and retrieve all trades")
    void testFindAll() {
        Order t1 = createTrade(1L,100L, 1);
        Order t2 = createTrade(2L,200L, 2);
        tradeRepository.save(t1);
        tradeRepository.save(t2);
        List<Order> all = tradeRepository.findAll();
        assertThat(all).hasSize(5);
    }

    @Test
    @DisplayName("Should find trades by tradeId")
    void testFindByTradeId() {
        Order t1 = createTrade(1L,2001L, 1);
        Order t2 = createTrade(2L,2001L, 2);
        tradeRepository.save(t1);
        tradeRepository.save(t2);
        List<Order> found = tradeRepository.findByTradeId(2001L);
        assertThat(found).hasSize(3);
        assertThat(found).allMatch(t -> t.getTradeId().equals(2001L));
    }

    @Test
    @DisplayName("Should return empty list for non-existent tradeId")
    void testFindByTradeId_NotFound() {
        List<Order> found = tradeRepository.findByTradeId(9999L);
        assertThat(found).isEmpty();
    }
}
