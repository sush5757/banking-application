package com.bankapp.service;

import com.bankapp.dto.DepositRequestDto;
import com.bankapp.dto.DepositResponseDeto;
import com.bankapp.dto.TransferRequestDto;
import com.bankapp.dto.TransferResponseDto;
import com.bankapp.entity.*;
import com.bankapp.exception.AccountInactiveException;
import com.bankapp.exception.InsufficientBalanceException;
import com.bankapp.exception.ResourceNotFoundException;
import com.bankapp.exception.UnauthorizedException;
import com.bankapp.repository.TransactionRepository;
import com.bankapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransactionService {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TransferResponseDto transfer(TransferRequestDto request, String username) {
        User loggedInUser  = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Account sourceAccount = accountService.getByAccountNumber(request.getFromAccountNumber());

        Account destinationAccount =
                accountService.getByAccountNumber(
                        request.getToAccountNumber()
                );

        if (!sourceAccount.getUser().getId()
                .equals(loggedInUser.getId())) {

            throw new UnauthorizedException(
                    "You are not authorized to access this account"
            );
        }
        if (sourceAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountInactiveException("Account is not active");
        }

        if (sourceAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in account: " + sourceAccount.getAccountNumber());
        }

        if (sourceAccount.getAccountNumber()
                .equals(destinationAccount.getAccountNumber())) {

            throw new RuntimeException(
                    "Cannot transfer to same account"
            );
        }
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(request.getAmount()));

        accountService.updateAccount(sourceAccount);
        accountService.updateAccount(destinationAccount);
        // Here you would also create and save a Transaction entity using transactionRepository

        Transaction txn = Transaction.builder()
                .fromAccount(sourceAccount.getAccountNumber())
                .toAccount(destinationAccount.getAccountNumber())
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

    @Override
    @Transactional
    public DepositResponseDeto deposit(DepositRequestDto requestDto, String username) {

        User employee = userRepository.findByUsername(username)
                .orElseThrow(()->
                        new ResourceNotFoundException("Employee not found"));

        Account account = accountService.getByAccountNumber(requestDto.getAccountNumber());


        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Account is not active");
        }

        account.setBalance(
                account.getBalance().add(requestDto.getAmount())
        );

        accountService.updateAccount(account);

        Transaction txn = Transaction.builder()
                .fromAccount("CASH_DEPOSIT")
                .toAccount(account.getAccountNumber())
                .amount(requestDto.getAmount())
                .status(TransactionStatus.SUCCESS)
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(txn);

        return DepositResponseDeto.builder()
                .transactionId(String.valueOf(txn.getId()))
                .accountNumber(account.getAccountNumber())
                .status("SUCCESS")
                .message("Cash Deposit completed successfully")
                .build();
    }
}
