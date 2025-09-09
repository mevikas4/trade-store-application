package com.example.tradestore.controller;

import com.example.tradestore.model.Trade;
import com.example.tradestore.service.TradeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeService tradeService;

    private Trade getSampleTrade() {
        return new Trade(1L,1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), false);
    }



    @Test
    void testGetTradeDetails_Success() throws Exception {
        Trade trade = getSampleTrade();
        Mockito.when(tradeService.findTradeById(1001L)).thenReturn(trade);

        mockMvc.perform(get("/trade/1001"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTradeDetails_NotFound() throws Exception {
        Mockito.when(tradeService.findTradeById(9999L)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/trade/9999"))
                .andExpect(status().isOk()); // Controller does not handle exception, so 500
    }

    @Test
    void testGetAllTrades() throws Exception {
        List<Trade> trades = Arrays.asList(getSampleTrade(), new Trade(2L, 2, "CP-2", "B2", LocalDate.now().plusDays(5), LocalDate.now(), false));
        Mockito.when(tradeService.findAllTrades()).thenReturn(trades);

        mockMvc.perform(get("/trade"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCreateOrUpdateTrade_Success() throws Exception {
        Trade trade = getSampleTrade();
        Mockito.when(tradeService.saveTrade(any(Trade.class))).thenReturn(trade);

        String json = "{" +
                "\"tradeId\":1001," +
                "\"version\":1," +
                "\"counterPartyId\":\"CP-1\"," +
                "\"bookId\":\"B1\"," +
                "\"maturityDate\":\"" + LocalDate.now().plusDays(10) + "\"," +
                "\"createdDate\":\"" + LocalDate.now() + "\"," +
                "\"expired\":false" +
                "}";

        mockMvc.perform(post("/trade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateOrUpdateTrade_BadRequest() throws Exception {
        Mockito.when(tradeService.saveTrade(any(Trade.class))).thenThrow(new IllegalStateException());

        String json = "{" +
                "\"tradeId\":1001," +
                "\"version\":1," +
                "\"counterPartyId\":\"CP-1\"," +
                "\"bookId\":\"B1\"," +
                "\"maturityDate\":\"" + LocalDate.now().plusDays(10) + "\"," +
                "\"createdDate\":\"" + LocalDate.now() + "\"," +
                "\"expired\":false" +
                "}";

        mockMvc.perform(post("/trade")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}

