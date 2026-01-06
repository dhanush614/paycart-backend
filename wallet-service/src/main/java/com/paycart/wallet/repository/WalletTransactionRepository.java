package com.paycart.wallet.repository;

import com.paycart.wallet.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

	List<WalletTransaction> findByWalletIdOrderByCreatedAtDesc(Long walletId);

	Optional<WalletTransaction> findByCorrelationId(String correlationId);
}
