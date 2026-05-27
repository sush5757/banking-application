package com.bankapp.service;

import com.bankapp.dto.AccountDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account getByAccountNumber(String accountId);
    void updateAccount(Account account);
    Account createAccount(CreateAccountRequestDto request);
    List<AccountDto> getAccountsByUsername(String username);
}
