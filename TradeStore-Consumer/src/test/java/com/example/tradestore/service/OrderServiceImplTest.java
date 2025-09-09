package com.example.tradestore.service;

import com.example.tradestore.model.Order;
import com.example.tradestore.model.Trade;
import com.example.tradestore.repository.OrderRepository;
import com.example.tradestore.service.impl.OrderServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {
    @Mock
    private OrderRepository tradeRepository;

    @InjectMocks
    private OrderServiceImpl tradeService;

    private Order sampleTrade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTrade = new Order(1L,1L, 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), false);
    }

    @Test
    void saveTrade_NewTrade_Success() {
        when(tradeRepository.findByTradeId(1001L)).thenReturn(Collections.emptyList());
        when(tradeRepository.save(any(Order.class))).thenReturn(sampleTrade);
        Order saved = tradeService.saveTrade(sampleTrade);
        assertEquals(1L, saved.getTradeId());
        verify(tradeRepository).save(sampleTrade);
    }

    @Test
    void saveTrade_MaturityDateInPast_ThrowsException() {
        Order pastTrade = new Order(2L,2L, 1, "CP-2", "B2", LocalDate.now().minusDays(1), LocalDate.now(), false);
        Exception ex = assertThrows(IllegalStateException.class, () -> tradeService.saveTrade(pastTrade));
        assertEquals("Trade maturity date cannot be in the past.", ex.getMessage());
        verify(tradeRepository, never()).save(any());
    }

    @Test
    void saveTrade_UpdateTrade_HigherVersion_Success() {
        Order existing = new Order(2L, 2L,1, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);
        Order newTrade = new Order(3L, 3L,2, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), false);
        when(tradeRepository.findByTradeId(1001L)).thenReturn(List.of(existing));
        when(tradeRepository.save(newTrade)).thenReturn(newTrade);
        Order saved = tradeService.saveTrade(newTrade);
        assertEquals(2, saved.getVersion());
        verify(tradeRepository).save(newTrade);
    }

    @Test
    void saveTrade_UpdateTrade_EqualVersion_UpdatesFields() {
        Order existing = new Order(2L,2l, 1, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);
        Order update = new Order(2L, 2L, 1, "CP-NEW", "B2", LocalDate.now().plusDays(10), LocalDate.now(), true);
        when(tradeRepository.findByTradeId(2L)).thenReturn(List.of(existing));
        when(tradeRepository.save(any(Order.class))).thenReturn(update);
        Order saved = tradeService.saveTrade(update);
        assertEquals("CP-NEW", saved.getCounterPartyId());
        assertEquals("B2", saved.getBookId());
        assertTrue(saved.isExpired());
        verify(tradeRepository).save(existing);
    }

    @Test
    void saveTrade_UpdateTrade_LowerVersion_ThrowsException() {
        Order existing = new Order(2L,2L, 2, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);
        Order outdated = new Order(2L, 2L,1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), false);
        when(tradeRepository.findByTradeId(2L)).thenReturn(List.of(existing));
        Exception ex = assertThrows(IllegalStateException.class, () -> tradeService.saveTrade(outdated));
        assertEquals("Trade version is outdated.", ex.getMessage());
        verify(tradeRepository, never()).save(any());
    }

    @Test
    void findTradeById_Found() {
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(sampleTrade));
        Order found = tradeService.findTradeById(1L);
        assertEquals(1L, found.getTradeId());
    }

    @Test
    void findTradeById_NotFound_ThrowsException() {
        when(tradeRepository.findById(99L)).thenReturn(Optional.empty());
        Exception ex = assertThrows(EntityNotFoundException.class, () -> tradeService.findTradeById(99L));
        assertTrue(ex.getMessage().contains("Trade not found with ID: 99"));
    }

    @Test
    void findAllTrades_ReturnsList() {
        List<Order> trades = List.of(sampleTrade, new Order(2L,2L, 1, "CP-2", "B2", LocalDate.now().plusDays(5), LocalDate.now(), false));
        when(tradeRepository.findAll()).thenReturn(trades);
        List<Order> result = tradeService.findAllTrades();
        assertEquals(2, result.size());
    }
}

