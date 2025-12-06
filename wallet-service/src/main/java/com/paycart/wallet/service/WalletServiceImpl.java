package com.paycart.wallet.service;

import com.paycart.wallet.dto.PaymentRequest;
import com.paycart.wallet.dto.TopUpRequest;
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
	public Wallet createWallet(Long userId) {
		Wallet wallet = new Wallet();
		wallet.setUserId(userId);
		wallet.setBalance(BigDecimal.ZERO);
		return walletRepository.save(wallet);
	}

	@Override
	public Wallet getWallet(Long walletId) {
		return walletRepository.findById(walletId)
				.orElseThrow(() -> new RuntimeException("Wallet not found with id: " + walletId));
	}

	@Override
	@Transactional
	public Wallet topUp(Long walletId, TopUpRequest request) {
		Wallet wallet = getWallet(walletId);

		BigDecimal amount = request.getAmount();
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be positive");
		}

		// Update wallet balance
		wallet.setBalance(wallet.getBalance().add(amount));
		Wallet savedWallet = walletRepository.save(wallet);

		// Create transaction record
		WalletTransaction tx = new WalletTransaction();
		tx.setWallet(savedWallet);
		tx.setType(TransactionType.CREDIT);
		tx.setAmount(amount);
		tx.setStatus(TransactionStatus.SUCCESS);
		tx.setDescription(request.getDescription());
		tx.setCorrelationId(null); // later for idempotency

		transactionRepository.save(tx);

		return savedWallet;
	}
	
	@Override
    @Transactional
    public Wallet debit(Long walletId, PaymentRequest request) {
        Wallet wallet = getWallet(walletId);

        BigDecimal amount = request.getAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        // Check balance
        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // Update balance
        wallet.setBalance(wallet.getBalance().subtract(amount));
        Wallet savedWallet = walletRepository.save(wallet);

        // Record transaction
        WalletTransaction tx = new WalletTransaction();
        tx.setWallet(savedWallet);
        tx.setType(TransactionType.DEBIT);
        tx.setAmount(amount);
        tx.setStatus(TransactionStatus.SUCCESS);
        tx.setDescription(request.getDescription());
        tx.setCorrelationId(request.getCorrelationId());

        transactionRepository.save(tx);

        return savedWallet;
    }

	@Override
	public List<WalletTransaction> getTransactions(Long walletId) {
		return transactionRepository.findByWalletIdOrderByCreatedAtDesc(walletId);
	}
}
