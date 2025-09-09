package com.example.tradestore.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TradeTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDate maturity = LocalDate.now().plusDays(5);
        LocalDate created = LocalDate.now();
        Trade trade = new Trade(1L, 2, "CP-1", "B1", maturity, created, true);
        assertEquals(1L, trade.getTradeId());
        assertEquals(2, trade.getVersion());
        assertEquals("CP-1", trade.getCounterPartyId());
        assertEquals("B1", trade.getBookId());
        assertEquals(maturity, trade.getMaturityDate());
        assertEquals(created, trade.getCreatedDate());
        assertTrue(trade.isExpired());
    }

    @Test
    void testSetters() {
        Trade trade = new Trade();
        trade.setId(2L);
        trade.setTradeId(2002L);
        trade.setVersion(3);
        trade.setCounterPartyId("CP-2");
        trade.setBookId("B2");
        LocalDate maturity = LocalDate.now().plusDays(10);
        LocalDate created = LocalDate.now();
        trade.setMaturityDate(maturity);
        trade.setCreatedDate(created);
        trade.setExpired(false);
        assertEquals(2L, trade.getId());
        assertEquals(2002L, trade.getTradeId());
        assertEquals(3, trade.getVersion());
        assertEquals("CP-2", trade.getCounterPartyId());
        assertEquals("B2", trade.getBookId());
        assertEquals(maturity, trade.getMaturityDate());
        assertEquals(created, trade.getCreatedDate());
        assertFalse(trade.isExpired());
    }

    @Test
    void testToString() {
        Trade trade = new Trade(1L, 2, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);
        assertNotNull(trade.toString());
        assertTrue(trade.toString().contains("version=2"));
    }
}

