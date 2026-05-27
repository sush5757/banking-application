package com.bankapp.dto;

import com.bankapp.entity.AccountStatus;
import com.bankapp.entity.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
public class AccountDto {

    private String accountNumber;
    private BigDecimal balance;
    private AccountStatus status;
    private AccountType accountType;
}
