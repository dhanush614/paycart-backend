package com.paycart.wallet.dto;

import com.paycart.wallet.enums.WalletStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WalletResponse {

	private Long walletId;
	private Long userId;
	private BigDecimal balance;
	private WalletStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public WalletResponse(Long walletId, Long userId, BigDecimal balance, WalletStatus status, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.walletId = walletId;
		this.userId = userId;
		this.balance = balance;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Long getWalletId() {
		return walletId;
	}

	public Long getUserId() {
		return userId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public WalletStatus getStatus() {
		return status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
