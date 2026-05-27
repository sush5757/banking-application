package com.bankapp.service;

import com.bankapp.dto.DepositRequestDto;
import com.bankapp.dto.DepositResponseDeto;
import com.bankapp.dto.TransferRequestDto;
import com.bankapp.dto.TransferResponseDto;

import java.math.BigDecimal;

public interface TransactionService {
    TransferResponseDto transfer(TransferRequestDto request, String username);
    DepositResponseDeto deposit(DepositRequestDto request, String username);
}
