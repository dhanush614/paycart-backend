package com.paycart.order.dto;

import com.paycart.order.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

	private Long orderId;
	private Long userId;
	private OrderStatus status;
	private BigDecimal totalAmount;
	private LocalDateTime createdAt;
	private List<OrderItemResponse> items;

	public OrderResponse(Long orderId, Long userId, OrderStatus status, BigDecimal totalAmount, LocalDateTime createdAt,
			List<OrderItemResponse> items) {
		this.orderId = orderId;
		this.userId = userId;
		this.status = status;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
		this.items = items;
	}

	public Long getOrderId() {
		return orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public List<OrderItemResponse> getItems() {
		return items;
	}
}
