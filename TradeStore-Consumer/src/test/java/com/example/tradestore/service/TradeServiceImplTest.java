package com.example.tradestore.service;

import com.example.tradestore.model.Trade;
import com.example.tradestore.repository.TradeRepository;
import com.example.tradestore.service.impl.TradeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TradeServiceImplTest {
    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;

    private Trade sampleTrade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTrade = new Trade(1L, 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), false);
    }

    @Test
    void saveTrade_NewTrade_Success() {
        when(tradeRepository.findByTradeId(1001L)).thenReturn(Collections.emptyList());
        when(tradeRepository.save(any(Trade.class))).thenReturn(sampleTrade);
        Trade saved = tradeService.saveTrade(sampleTrade);
        assertEquals(1L, saved.getTradeId());
        verify(tradeRepository).save(sampleTrade);
    }

    @Test
    void saveTrade_MaturityDateInPast_ThrowsException() {
        Trade pastTrade = new Trade(2L, 1, "CP-2", "B2", LocalDate.now().minusDays(1), LocalDate.now(), false);
        Exception ex = assertThrows(IllegalStateException.class, () -> tradeService.saveTrade(pastTrade));
        assertEquals("Trade maturity date cannot be in the past.", ex.getMessage());
        verify(tradeRepository, never()).save(any());
    }

    @Test
    void saveTrade_UpdateTrade_HigherVersion_Success() {
        Trade existing = new Trade(2L, 1, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);
        Trade newTrade = new Trade(3L, 2, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), false);
        when(tradeRepository.findByTradeId(1001L)).thenReturn(List.of(existing));
        when(tradeRepository.save(newTrade)).thenReturn(newTrade);
        Trade saved = tradeService.saveTrade(newTrade);
        assertEquals(2, saved.getVersion());
        verify(tradeRepository).save(newTrade);
    }

    @Test
    void saveTrade_UpdateTrade_EqualVersion_UpdatesFields() {
        Trade existing = new Trade(2L, 1, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);
        Trade update = new Trade(2L, 1, "CP-NEW", "B2", LocalDate.now().plusDays(10), LocalDate.now(), true);
        when(tradeRepository.findByTradeId(2L)).thenReturn(List.of(existing));
        when(tradeRepository.save(any(Trade.class))).thenReturn(update);
        Trade saved = tradeService.saveTrade(update);
        assertEquals("CP-NEW", saved.getCounterPartyId());
        assertEquals("B2", saved.getBookId());
        assertTrue(saved.isExpired());
        verify(tradeRepository).save(existing);
    }

    @Test
    void saveTrade_UpdateTrade_LowerVersion_ThrowsException() {
        Trade existing = new Trade(2L, 2, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);
        Trade outdated = new Trade(2L, 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), false);
        when(tradeRepository.findByTradeId(2L)).thenReturn(List.of(existing));
        Exception ex = assertThrows(IllegalStateException.class, () -> tradeService.saveTrade(outdated));
        assertEquals("Trade version is outdated.", ex.getMessage());
        verify(tradeRepository, never()).save(any());
    }

    @Test
    void findTradeById_Found() {
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(sampleTrade));
        Trade found = tradeService.findTradeById(1L);
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
        List<Trade> trades = List.of(sampleTrade, new Trade(2L, 1, "CP-2", "B2", LocalDate.now().plusDays(5), LocalDate.now(), false));
        when(tradeRepository.findAll()).thenReturn(trades);
        List<Trade> result = tradeService.findAllTrades();
        assertEquals(2, result.size());
    }
}

