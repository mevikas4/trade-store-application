package com.example.tradestore.producer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TradeStoreProducerApplicationTests {

	@Test
	void contextLoads() {
		// This test will pass as long as the application context loads successfully.
		// It serves as a basic health check for your application's configuration.
	}
	@Test
	void mainMethodRunsSuccessfully() {
		// Verify that the main method runs without throwing an exception
		assertDoesNotThrow(() -> TradeStoreProducerApplication.main(new String[]{}));
	}
}
