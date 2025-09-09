package com.example.tradestore.consumer;

import com.example.tradestore.model.Trade;
import com.example.tradestore.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class MyKafkaTradeListenerTest {

    private TradeService tradeService;
    private MyKafkaTradeListener myKafkaListener;

    @BeforeEach
    void setUp() {
        tradeService = mock(TradeService.class);
        myKafkaListener = new MyKafkaTradeListener();
        // Inject mock using reflection
        try {
            var field = MyKafkaTradeListener.class.getDeclaredField("tradeService");
            field.setAccessible(true);
            field.set(myKafkaListener, tradeService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void listen_shouldCallSaveTrade() {
        Trade trade = new Trade(1L, 1, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);

        myKafkaListener.listen(trade);

        verify(tradeService, times(1)).saveTrade(trade);
    }
}