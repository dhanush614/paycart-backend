package com.paycart.wallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PaymentRequest {

	@NotNull(message = "amount is required")
	@DecimalMin(value = "0.01", inclusive = true, message = "amount must be greater than 0")
	private BigDecimal amount;

	// For now optional; later we can pass orderId or correlationId for idempotency
	private String description;

	private String correlationId; // optional, for future idempotency

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
}
