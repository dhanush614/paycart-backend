package com.paycart.wallet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paycart.wallet.dto.CreateWalletRequest;
import com.paycart.wallet.dto.PaymentRequest;
import com.paycart.wallet.dto.TopUpRequest;
import com.paycart.wallet.dto.WalletResponse;
import com.paycart.wallet.dto.WalletTransactionResponse;
import com.paycart.wallet.service.WalletService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

	private final WalletService walletService;

	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}

	// Create a wallet for a user
	@PostMapping
	public WalletResponse createWallet(@Valid @RequestBody CreateWalletRequest request) {
		return walletService.createWallet(request.getUserId());
	}

	// Get wallet by id
	@GetMapping("/{walletId}")
	public WalletResponse getWallet(@PathVariable Long walletId) {
		return walletService.getWallet(walletId);
	}

	// Top up wallet
	@PostMapping("/{walletId}/topup")
	public WalletResponse topUp(@PathVariable Long walletId, @Valid @RequestBody TopUpRequest request) {
		return walletService.topUp(walletId, request);
	}

	// Debit / Payment API
	@PostMapping("/{walletId}/debit")
	public WalletResponse debit(@PathVariable Long walletId, @Valid @RequestBody PaymentRequest request) {
		return walletService.debit(walletId, request);
	}

	// Get all transactions for a wallet
	@GetMapping("/{walletId}/transactions")
	public List<WalletTransactionResponse> getTransactions(@PathVariable Long walletId) {
		return walletService.getTransactions(walletId);
	}
}
