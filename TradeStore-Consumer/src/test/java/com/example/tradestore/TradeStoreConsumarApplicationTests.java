package com.example.tradestore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class TradeStoreConsumarApplicationTests {

	@Test
	void contextLoads() {
		// This test will pass as long as the application context loads successfully.
		// It serves as a basic health check for your application's configuration.
	}
	@Test
	void mainMethodRunsSuccessfully() {
		// Verify that the main method runs without throwing an exception
		assertDoesNotThrow(() -> TradeStoreConsumerApplication.main(new String[]{}));
	}
}
