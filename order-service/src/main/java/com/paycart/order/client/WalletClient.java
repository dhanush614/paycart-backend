package com.paycart.order.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class WalletClient {

	private final RestTemplate restTemplate;

	public WalletClient() {
		this.restTemplate = new RestTemplate();
	}

	public void debit(Long walletId, BigDecimal amount, String correlationId, String description) {
		String url = "http://localhost:8081/api/wallets/" + walletId + "/debit";

		Map<String, Object> body = new HashMap<>();
		body.put("amount", amount);
		body.put("correlationId", correlationId);
		body.put("description", description);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

		restTemplate.postForEntity(url, request, String.class);
	}
}
