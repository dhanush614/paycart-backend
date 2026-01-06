package com.paycart.order.dto;

import java.math.BigDecimal;

public class OrderItemResponse {

	private Long productId;
	private String productName;
	private Integer quantity;
	private BigDecimal priceAtPurchase;

	public OrderItemResponse(Long productId, String productName, Integer quantity, BigDecimal priceAtPurchase) {
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.priceAtPurchase = priceAtPurchase;
	}

	public Long getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public BigDecimal getPriceAtPurchase() {
		return priceAtPurchase;
	}
}
