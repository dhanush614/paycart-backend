package com.paycart.wallet.service;

import com.paycart.wallet.dto.PaymentRequest;
import com.paycart.wallet.dto.TopUpRequest;
import com.paycart.wallet.entity.Wallet;
import com.paycart.wallet.entity.WalletTransaction;

import java.util.List;

public interface WalletService {

	Wallet createWallet(Long userId);

	Wallet getWallet(Long walletId);

	Wallet topUp(Long walletId, TopUpRequest request);

	List<WalletTransaction> getTransactions(Long walletId);
	
	Wallet debit(Long walletId, PaymentRequest request);
}
