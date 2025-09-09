package com.example.tradestore.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDate maturity = LocalDate.now().plusDays(5);
        LocalDate created = LocalDate.now();
        Order order = new Order(1L,1L, 2, "CP-1", "B1", maturity, created, true);
        assertEquals(1L, order.getTradeId());
        assertEquals(2, order.getVersion());
        assertEquals("CP-1", order.getCounterPartyId());
        assertEquals("B1", order.getBookId());
        assertEquals(maturity, order.getMaturityDate());
        assertEquals(created, order.getCreatedDate());
        assertTrue(order.isExpired());
    }

    @Test
    void testSetters() {
        Order order = new Order();
        order.setId(2L);
        order.setTradeId(2002L);
        order.setVersion(3);
        order.setCounterPartyId("CP-2");
        order.setBookId("B2");
        LocalDate maturity = LocalDate.now().plusDays(10);
        LocalDate created = LocalDate.now();
        order.setMaturityDate(maturity);
        order.setCreatedDate(created);
        order.setExpired(false);
        assertEquals(2L, order.getId());
        assertEquals(2002L, order.getTradeId());
        assertEquals(3, order.getVersion());
        assertEquals("CP-2", order.getCounterPartyId());
        assertEquals("B2", order.getBookId());
        assertEquals(maturity, order.getMaturityDate());
        assertEquals(created, order.getCreatedDate());
        assertFalse(order.isExpired());
    }

    @Test
    void testToString() {
        Order order = new Order(1L,1L, 2, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);
        assertNotNull(order.toString());
        assertTrue(order.toString().contains("version=2"));
    }
}

