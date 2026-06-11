package com.surya.banking.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String accountNumber;
    private BigDecimal amount;
    private String description;
    private String targetAccountNumber; // For transfers only
}