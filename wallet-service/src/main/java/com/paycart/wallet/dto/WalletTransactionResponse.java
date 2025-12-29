package com.paycart.wallet.dto;

import com.paycart.wallet.enums.TransactionStatus;
import com.paycart.wallet.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WalletTransactionResponse {

	private Long transactionId;
	private TransactionType type;
	private BigDecimal amount;
	private TransactionStatus status;
	private String description;
	private String correlationId;
	private LocalDateTime createdAt;

	public WalletTransactionResponse(Long transactionId, TransactionType type, BigDecimal amount,
			TransactionStatus status, String description, String correlationId, LocalDateTime createdAt) {
		this.transactionId = transactionId;
		this.type = type;
		this.amount = amount;
		this.status = status;
		this.description = description;
		this.correlationId = correlationId;
		this.createdAt = createdAt;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public TransactionType getType() {
		return type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
