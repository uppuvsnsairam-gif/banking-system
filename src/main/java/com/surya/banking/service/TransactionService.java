package com.surya.banking.service;

import com.surya.banking.dto.TransactionRequest;
import com.surya.banking.model.Account;
import com.surya.banking.model.Transaction;
import com.surya.banking.repository.AccountRepository;
import com.surya.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction deposit(TransactionRequest request) {
        Account account = accountRepository
            .findByAccountNumber(request.getAccountNumber())
            .orElseThrow(() -> 
                new RuntimeException("Account not found!"));

        account.setBalance(
            account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        return saveTransaction(account, "DEPOSIT", 
            request.getAmount(), request.getDescription());
    }

    @Transactional
    public Transaction withdraw(TransactionRequest request) {
        Account account = accountRepository
            .findByAccountNumber(request.getAccountNumber())
            .orElseThrow(() -> 
                new RuntimeException("Account not found!"));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        account.setBalance(
            account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        return saveTransaction(account, "WITHDRAWAL", 
            request.getAmount(), request.getDescription());
    }

    @Transactional
    public String transfer(TransactionRequest request) {
        Account sender = accountRepository
            .findByAccountNumber(request.getAccountNumber())
            .orElseThrow(() -> 
                new RuntimeException("Sender account not found!"));

        Account receiver = accountRepository
            .findByAccountNumber(request.getTargetAccountNumber())
            .orElseThrow(() -> 
                new RuntimeException("Receiver account not found!"));

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        sender.setBalance(
            sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(
            receiver.getBalance().add(request.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        saveTransaction(sender, "TRANSFER", 
            request.getAmount(), "Transfer to " + 
            request.getTargetAccountNumber());
        saveTransaction(receiver, "TRANSFER", 
            request.getAmount(), "Transfer from " + 
            request.getAccountNumber());

        return "Transfer successful!";
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        Account account = accountRepository
            .findByAccountNumber(accountNumber)
            .orElseThrow(() -> 
                new RuntimeException("Account not found!"));
        return transactionRepository.findByAccountId(account.getId());
    }

    private Transaction saveTransaction(Account account, 
            String type, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setDescription(description);
        return transactionRepository.save(transaction);
    }
}