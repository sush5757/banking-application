package com.bankapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransferResponseDto {
    private String transactionId;
    private String status;
    private String message;
}
