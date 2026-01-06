package com.paycart.wallet.service;

import java.util.List;

import com.paycart.wallet.dto.PaymentRequest;
import com.paycart.wallet.dto.TopUpRequest;
import com.paycart.wallet.dto.WalletResponse;
import com.paycart.wallet.dto.WalletTransactionResponse;

public interface WalletService {

	WalletResponse createWallet(Long userId);

	WalletResponse getWallet(Long walletId);

	WalletResponse topUp(Long walletId, TopUpRequest request);

	List<WalletTransactionResponse> getTransactions(Long walletId);

	WalletResponse debit(Long walletId, PaymentRequest request);
}
