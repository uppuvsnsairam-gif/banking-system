package com.surya.banking.service;

import com.surya.banking.model.Account;
import com.surya.banking.model.User;
import com.surya.banking.repository.AccountRepository;
import com.surya.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public Account createAccount(String email, String accountType) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> 
                    new RuntimeException("User not found!"));

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType(accountType);
        account.setUser(user);

        return accountRepository.save(account);
    }

    public List<Account> getUserAccounts(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> 
                    new RuntimeException("User not found!"));
        return accountRepository.findByUserId(user.getId());
    }

    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> 
                    new RuntimeException("Account not found!"));
    }

    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID()
                .toString().replace("-", "")
                .substring(0, 10).toUpperCase();
    }
}