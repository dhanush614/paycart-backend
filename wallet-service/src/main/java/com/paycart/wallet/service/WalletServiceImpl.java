package com.paycart.wallet.service;

import com.paycart.wallet.dto.PaymentRequest;
import com.paycart.wallet.dto.TopUpRequest;
import com.paycart.wallet.dto.WalletResponse;
import com.paycart.wallet.dto.WalletTransactionResponse;
import com.paycart.wallet.entity.Wallet;
import com.paycart.wallet.entity.WalletTransaction;
import com.paycart.wallet.enums.TransactionStatus;
import com.paycart.wallet.enums.TransactionType;
import com.paycart.wallet.repository.WalletRepository;
import com.paycart.wallet.repository.WalletTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

	private final WalletRepository walletRepository;
	private final WalletTransactionRepository transactionRepository;

	public WalletServiceImpl(WalletRepository walletRepository, WalletTransactionRepository transactionRepository) {
		this.walletRepository = walletRepository;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public WalletResponse createWallet(Long userId) {
		Wallet wallet = new Wallet();
		wallet.setUserId(userId);
		wallet.setBalance(BigDecimal.ZERO);
		Wallet saved = walletRepository.save(wallet);
		return mapWallet(saved);
	}

	@Override
	public WalletResponse getWallet(Long walletId) {
		Wallet wallet = walletRepository.findById(walletId)
				.orElseThrow(() -> new RuntimeException("Wallet not found with id: " + walletId));
		return mapWallet(wallet);
	}

	@Override
	@Transactional
	public WalletResponse topUp(Long walletId, TopUpRequest request) {
		Wallet wallet = walletRepository.findById(walletId)
				.orElseThrow(() -> new RuntimeException("Wallet not found with id: " + walletId));

		BigDecimal amount = request.getAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}

		wallet.setBalance(wallet.getBalance().add(amount));
		Wallet savedWallet = walletRepository.save(wallet);

		WalletTransaction tx = new WalletTransaction();
		tx.setWallet(savedWallet);
		tx.setType(TransactionType.CREDIT);
		tx.setAmount(amount);
		tx.setStatus(TransactionStatus.SUCCESS);
		tx.setDescription(request.getDescription());
		tx.setCorrelationId(null);

		transactionRepository.save(tx);

		return mapWallet(savedWallet);
	}

	@Override
	@Transactional
	public WalletResponse debit(Long walletId, PaymentRequest request) {
		Wallet wallet = walletRepository.findById(walletId)
				.orElseThrow(() -> new RuntimeException("Wallet not found with id: " + walletId));

		BigDecimal amount = request.getAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}

		String correlationId = request.getCorrelationId();

		if (correlationId != null && !correlationId.isBlank()) {
			var existing = transactionRepository.findByCorrelationId(correlationId);
			if (existing.isPresent()) {
				return mapWallet(wallet);
			}
		}

		if (wallet.getBalance().compareTo(amount) < 0) {
			throw new IllegalArgumentException("Insufficient balance");
		}

		wallet.setBalance(wallet.getBalance().subtract(amount));
		Wallet savedWallet = walletRepository.save(wallet);

		WalletTransaction tx = new WalletTransaction();
		tx.setWallet(savedWallet);
		tx.setType(TransactionType.DEBIT);
		tx.setAmount(amount);
		tx.setStatus(TransactionStatus.SUCCESS);
		tx.setDescription(request.getDescription());
		tx.setCorrelationId(correlationId);

		transactionRepository.save(tx);

		return mapWallet(savedWallet);
	}

	@Override
	public List<WalletTransactionResponse> getTransactions(Long walletId) {
		return transactionRepository.findByWalletIdOrderByCreatedAtDesc(walletId).stream().map(this::mapTransaction)
				.toList();
	}

	private WalletResponse mapWallet(com.paycart.wallet.entity.Wallet wallet) {
		return new WalletResponse(wallet.getId(), wallet.getUserId(), wallet.getBalance(), wallet.getStatus(),
				wallet.getCreatedAt(), wallet.getUpdatedAt());
	}

	private WalletTransactionResponse mapTransaction(com.paycart.wallet.entity.WalletTransaction tx) {
		return new WalletTransactionResponse(tx.getId(), tx.getType(), tx.getAmount(), tx.getStatus(),
				tx.getDescription(), tx.getCorrelationId(), tx.getCreatedAt());
	}
}
