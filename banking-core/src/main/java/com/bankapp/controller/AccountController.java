package com.bankapp.controller;


import com.bankapp.dto.AccountDto;
import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.entity.Account;
import com.bankapp.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AccountService accountService;

     // Implement account-related endpoints here (e.g., get account details, create account, etc.)
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(
            @RequestBody CreateAccountRequestDto request){
        return ResponseEntity.status(200).body(accountService.createAccount(request));

    }


    @GetMapping("/my-accounts")
    public ResponseEntity<List<AccountDto>> getMyAccounts(Authentication authentication) {
        String username = authentication.getName();
        List<AccountDto> accounts = accountService.getAccountsByUsername(username);
        return ResponseEntity.ok(accounts);
    }
}
