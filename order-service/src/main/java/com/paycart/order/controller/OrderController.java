package com.paycart.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paycart.order.dto.CreateOrderRequest;
import com.paycart.order.dto.OrderResponse;
import com.paycart.order.entity.Product;
import com.paycart.order.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	// List products
	@GetMapping("/products")
	public List<Product> getProducts() {
		return orderService.getActiveProducts();
	}

	// Create order
	@PostMapping("/orders")
	public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
		return orderService.createOrder(request);
	}

	// Get order by id
	@GetMapping("/orders/{orderId}")
	public OrderResponse getOrder(@PathVariable Long orderId) {
		return orderService.getOrder(orderId);
	}

	// Get all orders for a user
	@GetMapping("/users/{userId}/orders")
	public List<OrderResponse> getUserOrders(@PathVariable Long userId) {
		return orderService.getOrdersForUser(userId);
	}
}
