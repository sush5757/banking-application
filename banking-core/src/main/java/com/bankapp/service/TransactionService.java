package com.bankapp.service;

import com.bankapp.dto.TransferRequestDto;
import com.bankapp.dto.TransferResponseDto;

public interface TransactionService {
    TransferResponseDto transfer(TransferRequestDto request, String username);
}
