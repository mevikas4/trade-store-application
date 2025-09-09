package com.example.tradestore.consumer;

import com.example.tradestore.model.Order;
import com.example.tradestore.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class MyKafkaOrderListenerTest {

    private OrderService orderService;
    private MyKafkaOrderListener myKafkaOrderListener;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        myKafkaOrderListener = new MyKafkaOrderListener();
        // Inject mock using reflection
        try {
            var field = MyKafkaOrderListener.class.getDeclaredField("orderService");
            field.setAccessible(true);
            field.set(myKafkaOrderListener, orderService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void listen_shouldCallSaveTrade() {
        Order order = new Order(1L,1L, 1, "CP-1", "B1", LocalDate.now().plusDays(5), LocalDate.now(), false);

        myKafkaOrderListener.listen(order);

        verify(orderService, times(1)).saveTrade(order);
    }
}