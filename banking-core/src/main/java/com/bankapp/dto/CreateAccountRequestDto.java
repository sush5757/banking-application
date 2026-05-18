package com.bankapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequestDto {
    private String accountNumber;

    private BigDecimal balance;

    private Long userId;

}
