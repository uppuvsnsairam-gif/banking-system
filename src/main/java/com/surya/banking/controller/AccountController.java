package com.surya.banking.controller;

import com.surya.banking.model.Account;
import com.surya.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(
            @RequestParam String accountType,
            Authentication authentication) {
        Account account = accountService.createAccount(
            authentication.getName(), accountType);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/my-accounts")
    public ResponseEntity<List<Account>> getMyAccounts(
            Authentication authentication) {
        List<Account> accounts = accountService.getUserAccounts(
            authentication.getName());
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(
            @PathVariable String accountNumber) {
        Account account = accountService
            .getAccountByNumber(accountNumber);
        return ResponseEntity.ok(account);
    }
}