package com.bankapp.controller;

import com.bankapp.dto.TransferRequestDto;
import com.bankapp.dto.TransferResponseDto;
import com.bankapp.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public TransferResponseDto transfer(@Valid @RequestBody TransferRequestDto request) {
        return transactionService.transfer(request);
        // Implementation goes here }
    }
}
