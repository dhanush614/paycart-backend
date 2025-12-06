package com.paycart.wallet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateWalletRequest {

	@NotNull(message = "userId is required")
	@Min(value = 1, message = "userId must be a positive number")
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
