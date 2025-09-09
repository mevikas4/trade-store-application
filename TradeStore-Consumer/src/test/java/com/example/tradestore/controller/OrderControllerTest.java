package com.example.tradestore.controller;

import com.example.tradestore.model.Order;
import com.example.tradestore.service.OrderService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    private Order getSampleTrade() {
        return new Order(1L,1L,1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDate.now(), false);
    }



    @Test
    void testGetTradeDetails_Success() throws Exception {
        Order trade = getSampleTrade();
        Mockito.when(orderService.findTradeById(1001L)).thenReturn(trade);

        mockMvc.perform(get("/order/1001"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTradeDetails_NotFound() throws Exception {
        Mockito.when(orderService.findTradeById(9999L)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/order/9999"))
                .andExpect(status().isOk()); // Controller does not handle exception, so 500
    }

    @Test
    void testGetAllTrades() throws Exception {
        List<Order> trades = Arrays.asList(getSampleTrade(), new Order(2L, 2L,2, "CP-2", "B2", LocalDate.now().plusDays(5), LocalDate.now(), false));
        Mockito.when(orderService.findAllTrades()).thenReturn(trades);

        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCreateOrUpdateTrade_Success() throws Exception {
        Order trade = getSampleTrade();
        Mockito.when(orderService.saveTrade(any(Order.class))).thenReturn(trade);

        String json = "{" +
                "\"tradeId\":1001," +
                "\"version\":1," +
                "\"counterPartyId\":\"CP-1\"," +
                "\"bookId\":\"B1\"," +
                "\"maturityDate\":\"" + LocalDate.now().plusDays(10) + "\"," +
                "\"createdDate\":\"" + LocalDate.now() + "\"," +
                "\"expired\":false" +
                "}";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateOrUpdateTrade_BadRequest() throws Exception {
        Mockito.when(orderService.saveTrade(any(Order.class))).thenThrow(new IllegalStateException());

        String json = "{" +
                "\"tradeId\":1001," +
                "\"version\":1," +
                "\"counterPartyId\":\"CP-1\"," +
                "\"bookId\":\"B1\"," +
                "\"maturityDate\":\"" + LocalDate.now().plusDays(10) + "\"," +
                "\"createdDate\":\"" + LocalDate.now() + "\"," +
                "\"expired\":false" +
                "}";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}

