package com.bankapp.controller;


import com.bankapp.dto.CreateAccountRequestDto;
import com.bankapp.entity.Account;
import com.bankapp.service.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AccountService accountService;

     // Implement account-related endpoints here (e.g., get account details, create account, etc.)
    @PostMapping
    public Account createAccount(
            @RequestBody CreateAccountRequestDto request){
        return accountService.createAccount(request);

    }
}
