package com.paycart.order.service;

import java.util.List;

import com.paycart.order.dto.CreateOrderRequest;
import com.paycart.order.dto.OrderResponse;
import com.paycart.order.entity.Product;

public interface OrderService {

	List<Product> getActiveProducts();

	OrderResponse createOrder(CreateOrderRequest request);

	OrderResponse getOrder(Long orderId);

	List<OrderResponse> getOrdersForUser(Long userId);
}
