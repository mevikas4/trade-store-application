package com.example.tradestore.repository;

import com.example.tradestore.model.Trade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    private Trade createTrade(Long tradeId, int version) {
        return new Trade(tradeId, version, "CP-" + tradeId, "B" + tradeId,
                LocalDate.now().plusDays(10), LocalDate.now(), false);
    }

    @Test
    @DisplayName("Should save and retrieve all trades")
    void testFindAll() {
        Trade t1 = createTrade(1001L, 1);
        Trade t2 = createTrade(1002L, 2);
        tradeRepository.save(t1);
        tradeRepository.save(t2);
        List<Trade> all = tradeRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("Should find trades by tradeId")
    void testFindByTradeId() {
        Trade t1 = createTrade(2001L, 1);
        Trade t2 = createTrade(2001L, 2);
        tradeRepository.save(t1);
        tradeRepository.save(t2);
        List<Trade> found = tradeRepository.findByTradeId(2001L);
        assertThat(found).hasSize(2);
        assertThat(found).allMatch(t -> t.getTradeId().equals(2001L));
    }

    @Test
    @DisplayName("Should return empty list for non-existent tradeId")
    void testFindByTradeId_NotFound() {
        List<Trade> found = tradeRepository.findByTradeId(9999L);
        assertThat(found).isEmpty();
    }
}
