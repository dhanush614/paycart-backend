package com.paycart.order.repository;

import com.paycart.order.entity.Inventory;
import com.paycart.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	Optional<Inventory> findByProduct(Product product);
}
