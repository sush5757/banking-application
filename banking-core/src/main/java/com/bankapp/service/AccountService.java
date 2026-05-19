package com.bankapp.service;

import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.entity.Account;

import java.util.Optional;

public interface AccountService {
    Account getByAccountNumber(String accountId);
    void updateAccount(Account account);
    Account createAccount(CreateAccountRequestDto request);
}
