package com.paycart.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.paycart.order.dto.CreateOrderRequest;
import com.paycart.order.dto.OrderItemRequest;
import com.paycart.order.dto.OrderItemResponse;
import com.paycart.order.dto.OrderResponse;
import com.paycart.order.entity.Inventory;
import com.paycart.order.entity.Order;
import com.paycart.order.entity.OrderItem;
import com.paycart.order.entity.Product;
import com.paycart.order.enums.OrderStatus;
import com.paycart.order.repository.InventoryRepository;
import com.paycart.order.repository.OrderRepository;
import com.paycart.order.repository.ProductRepository;

@Service
public class OrderServiceImpl implements OrderService {

	private final ProductRepository productRepository;
	private final InventoryRepository inventoryRepository;
	private final OrderRepository orderRepository;

	public OrderServiceImpl(ProductRepository productRepository, InventoryRepository inventoryRepository,
			OrderRepository orderRepository) {
		this.productRepository = productRepository;
		this.inventoryRepository = inventoryRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public List<Product> getActiveProducts() {
		return productRepository.findByActiveTrue();
	}

	@Override
	public OrderResponse getOrder(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
		return mapToResponse(order);
	}

	@Override
	public List<OrderResponse> getOrdersForUser(Long userId) {
		return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::mapToResponse).toList();
	}

	@Override
	public OrderResponse createOrder(CreateOrderRequest request) {
		Order order = createAndPersistOrder(request);
		return mapToResponse(order);
	}

	private Order createAndPersistOrder(CreateOrderRequest request) {
		Order order = new Order();
		order.setUserId(request.getUserId());

		BigDecimal total = BigDecimal.ZERO;

		for (OrderItemRequest itemReq : request.getItems()) {
			Product product = productRepository.findById(itemReq.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found: " + itemReq.getProductId()));

			Inventory inventory = inventoryRepository.findByProduct(product)
					.orElseThrow(() -> new RuntimeException("Inventory not found for product: " + product.getId()));

			if (inventory.getAvailableQuantity() < itemReq.getQuantity()) {
				throw new IllegalArgumentException("Insufficient inventory for product: " + product.getName());
			}

			inventory.setAvailableQuantity(inventory.getAvailableQuantity() - itemReq.getQuantity());
			inventoryRepository.save(inventory);

			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setQuantity(itemReq.getQuantity());
			orderItem.setPriceAtPurchase(product.getPrice());
			order.addItem(orderItem);

			total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
		}

		order.setTotalAmount(total);
		order.setStatus(OrderStatus.CREATED);

		return orderRepository.save(order);
	}

	private OrderResponse mapToResponse(Order order) {

		List<OrderItemResponse> itemResponses = order.getItems().stream()
				.map(item -> new OrderItemResponse(item.getProduct().getId(), item.getProduct().getName(),
						item.getQuantity(), item.getPriceAtPurchase()))
				.toList();

		return new OrderResponse(order.getId(), order.getUserId(), order.getStatus(), order.getTotalAmount(),
				order.getCreatedAt(), itemResponses);
	}

}
