package com.bankapp.service;

import com.bankapp.entity.Account;
import com.bankapp.exception.ResourceNotFoundException;
import com.bankapp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    @Override
    public Account getByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new ResourceNotFoundException("Account not found"));
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }
}
