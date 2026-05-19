package com.bankapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositResponseDeto {
    private String transactionId;
    private String accountNumber;
    private String status;
    private String message;
    private double newBalance;
}
