package com.example.tradestore.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TradeTest {

    private Trade trade;
    private LocalDate maturityDate;
    private LocalDate createdDate;

    @BeforeEach
    void setUp() {
        maturityDate = LocalDate.of(2025, 12, 31);
        createdDate = LocalDate.now();
        // Use the all-args constructor to initialize a Trade object
        trade = new Trade("T123", 1, "CP-1", "B-1", maturityDate, createdDate, false);
    }

    @Test
    void testNoArgsConstructor() {
        Trade defaultTrade = new Trade();
        assertNotNull(defaultTrade);
    }

    @Test
    void testAllArgsConstructor() {
        // Assert that the object was created with the correct values
        assertEquals("T123", trade.getTradeId());
        assertEquals(1, trade.getVersion());
        assertEquals("CP-1", trade.getCounterPartyId());
        assertEquals("B-1", trade.getBookId());
        assertEquals(maturityDate, trade.getMaturityDate());
        assertEquals(createdDate, trade.getCreatedDate());
        assertFalse(trade.isExpired());
    }

    @Test
    void testGettersAndSetters() {
        Trade newTrade = new Trade();

        // Arrange
        String newTradeId = "T456";
        int newVersion = 2;
        String newCounterPartyId = "CP-2";
        String newBookId = "B-2";
        LocalDate newMaturityDate = LocalDate.of(2026, 1, 15);

        // Act
        newTrade.setTradeId(newTradeId);
        newTrade.setVersion(newVersion);
        newTrade.setCounterPartyId(newCounterPartyId);
        newTrade.setBookId(newBookId);
        newTrade.setMaturityDate(newMaturityDate);
        newTrade.setCreatedDate(createdDate);
        newTrade.setExpired(true);

        // Assert
        assertEquals(newTradeId, newTrade.getTradeId());
        assertEquals(newVersion, newTrade.getVersion());
        assertEquals(newCounterPartyId, newTrade.getCounterPartyId());
        assertEquals(newBookId, newTrade.getBookId());
        assertEquals(newMaturityDate, newTrade.getMaturityDate());
        assertEquals(createdDate, newTrade.getCreatedDate());
        assertTrue(newTrade.isExpired());
    }

    @Test
    void testToString() {
        String expectedToString = "Trade{" +
                "tradeId=T123, version=1, counterPartyId=CP-1, bookId=B-1" +
                ", maturityDate=2025-12-31, createdDate=" + createdDate + ", expired=false" +
                '}';
        assertEquals(expectedToString, trade.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        // Create an identical trade object
        Trade identicalTrade = new Trade("T123", 1, "CP-1", "B-1", maturityDate, createdDate, false);

        // Create a different trade object
        Trade differentTrade = new Trade("T999", 2, "CP-2", "B-2", maturityDate, createdDate, false);

        // Test equals()
        assertTrue(trade.equals(identicalTrade)); // Identical objects should be equal
        assertEquals(trade.hashCode(), identicalTrade.hashCode()); // Hash codes should be the same

        assertFalse(trade.equals(differentTrade)); // Different objects should not be equal
        assertNotEquals(trade.hashCode(), differentTrade.hashCode()); // Hash codes should be different

        assertFalse(trade.equals(null)); // Object should not be equal to null
        assertFalse(trade.equals(new Object())); // Object should not be equal to a different class instance
    }
}