package com.bankapp.service;

import com.bankapp.dto.AccountDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.entity.Account;
import com.bankapp.entity.User;
import com.bankapp.exception.ResourceNotFoundException;
import com.bankapp.repository.AccountRepository;
import com.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public Account getByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new ResourceNotFoundException("Account not found"));
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    //Add Account type like FD Current etc
    public Account createAccount(CreateAccountRequestDto request){

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        Account account = Account.builder()
                .accountNumber(request.getAccountNumber())
                .balance(request.getBalance())
                .user(user)
                .build();
        return accountRepository.save(account);
    }

    @Override
    public List<AccountDto> getAccountsByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));

        return accountRepository.findByUserId(user.getId())
                .stream()
                .map(account -> AccountDto.builder()
                        .accountNumber(account.getAccountNumber())
                        .balance(account.getBalance())
                        .status(account.getStatus())
                        .accountType(account.getAccountType())
                        .build())
                .toList();
    }

}
