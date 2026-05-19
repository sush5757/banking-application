package com.bankapp.service;

import com.bankapp.entity.*;
import com.bankapp.exception.DuplicateResourceException;
import com.bankapp.repository.AccountRepository;
import com.bankapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;


    @Transactional
    @Override
    public User register(User user) {

        if(userRepository.existsByEmail(user.getEmail())){
            throw new DuplicateResourceException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        User savedUser = userRepository.save(user);

        long count = accountRepository.count() + 1;

        String branchCode = "101";

        String productCode = "SB";

        String sequence = String.format("%08d", count);

        String accountNumber =
                "BNK" +
                        branchCode +
                        productCode +
                        sequence;

        Account account = new Account();

        account.setAccountNumber(accountNumber);

        account.setAccountType(AccountType.SAVINGS);

        account.setStatus(AccountStatus.ACTIVE);

        account.setBalance(BigDecimal.ZERO);

        account.setUser(savedUser);

        accountRepository.save(account);
        return savedUser;
    }
}
