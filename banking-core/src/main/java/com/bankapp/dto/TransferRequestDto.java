package com.bankapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {
    @NotNull
    private String fromAccountNumber;
    @NotNull
    private String toAccountNumber;
    @NotNull
    @Positive
    private BigDecimal amount;
}
