package com.bankapp.service;

import com.bankapp.dto.TransferRequestDto;
import com.bankapp.dto.TransferResponseDto;
import com.bankapp.entity.Account;
import com.bankapp.entity.Transaction;
import com.bankapp.entity.TransactionStatus;
import com.bankapp.exception.InsufficientBalanceException;
import com.bankapp.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransactionService {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransferResponseDto transfer(TransferRequestDto request) {
        Account from = accountService.getByAccountNumber(request.getFromAccountNumber());
        Account to = accountService.getByAccountNumber(request.getToAccountNumber());

        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in account: " + from.getAccountNumber());
        }
        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        accountService.updateAccount(from);
        accountService.updateAccount(to);
        // Here you would also create and save a Transaction entity using transactionRepository

        Transaction txn = Transaction.builder()
                .fromAccount(from.getAccountNumber())
                .toAccount(to.getAccountNumber())
                .amount(request.getAmount())
                .status(TransactionStatus.SUCCESS)
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(txn);

        return TransferResponseDto.builder()
                .transactionId(UUID.randomUUID().toString())
                .status("SUCCESS")
                .message("Transfer completed successfully")
                .build();
    }
}
