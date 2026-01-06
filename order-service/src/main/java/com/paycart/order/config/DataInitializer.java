package com.paycart.order.config;

import com.paycart.order.entity.Inventory;
import com.paycart.order.entity.Product;
import com.paycart.order.repository.InventoryRepository;
import com.paycart.order.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

	@Bean
	public CommandLineRunner initData(ProductRepository productRepository, InventoryRepository inventoryRepository) {
		return args -> {
			if (productRepository.count() == 0) {
				Product p1 = new Product();
				p1.setName("Laptop Bag");
				p1.setDescription("Waterproof laptop bag");
				p1.setPrice(BigDecimal.valueOf(1500));
				productRepository.save(p1);

				Product p2 = new Product();
				p2.setName("Wireless Mouse");
				p2.setDescription("Bluetooth mouse");
				p2.setPrice(BigDecimal.valueOf(700));
				productRepository.save(p2);

				Inventory inv1 = new Inventory();
				inv1.setProduct(p1);
				inv1.setAvailableQuantity(10);
				inventoryRepository.save(inv1);

				Inventory inv2 = new Inventory();
				inv2.setProduct(p2);
				inv2.setAvailableQuantity(20);
				inventoryRepository.save(inv2);
			}
		};
	}
}
