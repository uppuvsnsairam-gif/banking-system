package com.surya.banking.controller;

import com.surya.banking.dto.TransactionRequest;
import com.surya.banking.model.Transaction;
import com.surya.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(
            @RequestBody TransactionRequest request) {
        Transaction transaction = 
            transactionService.deposit(request);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @RequestBody TransactionRequest request) {
        Transaction transaction = 
            transactionService.withdraw(request);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestBody TransactionRequest request) {
        String response = transactionService.transfer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<Transaction>> getHistory(
            @PathVariable String accountNumber) {
        List<Transaction> transactions = 
            transactionService.getTransactionHistory(accountNumber);
        return ResponseEntity.ok(transactions);
    }
}