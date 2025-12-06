package com.paycart.wallet.controller;

import com.paycart.wallet.dto.CreateWalletRequest;
import com.paycart.wallet.dto.PaymentRequest;
import com.paycart.wallet.dto.TopUpRequest;
import com.paycart.wallet.entity.Wallet;
import com.paycart.wallet.entity.WalletTransaction;
import com.paycart.wallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

	private final WalletService walletService;

	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}

	// Create a wallet for a user
	@PostMapping
	public Wallet createWallet(@Valid @RequestBody CreateWalletRequest request) {
		return walletService.createWallet(request.getUserId());
	}

	// Get wallet by id
	@GetMapping("/{walletId}")
	public Wallet getWallet(@PathVariable Long walletId) {
		return walletService.getWallet(walletId);
	}

	// Top up wallet
	@PostMapping("/{walletId}/topup")
	public Wallet topUp(@PathVariable Long walletId, @Valid @RequestBody TopUpRequest request) {
		return walletService.topUp(walletId, request);
	}

	// Debit / Payment API
	@PostMapping("/{walletId}/debit")
	public Wallet debit(@PathVariable Long walletId, @Valid @RequestBody PaymentRequest request) {
		return walletService.debit(walletId, request);
	}

	// Get all transactions for a wallet
	@GetMapping("/{walletId}/transactions")
	public List<WalletTransaction> getTransactions(@PathVariable Long walletId) {
		return walletService.getTransactions(walletId);
	}
}
