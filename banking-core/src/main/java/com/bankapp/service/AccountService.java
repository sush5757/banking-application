package com.bankapp.service;

import com.bankapp.entity.Account;

public interface AccountService {
    Account getByAccountNumber(String accountId);
    void updateAccount(Account account);

}
