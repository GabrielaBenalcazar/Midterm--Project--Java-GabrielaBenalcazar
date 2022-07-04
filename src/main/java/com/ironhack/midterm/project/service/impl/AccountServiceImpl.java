package com.ironhack.midterm.project.service.impl;

import com.ironhack.midterm.project.models.account.Account;
import com.ironhack.midterm.project.models.clasees.Money;
import com.ironhack.midterm.project.repository.AccountRepository;
import com.ironhack.midterm.project.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Money getBalance(Long id) {
        return accountRepository.getBalance(id);

    }

    @Override
    public Money getBalanceForUser(Long accountId, Long userId) {
        return null;
    }

    @Override
    public void setBalance(Long id, Money balance) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        optionalAccount.get().setBalance(balance);
        accountRepository.save(optionalAccount.get());
    }

    @Override
    public void transferMoney(Long sourceId, Long destinationId, BigDecimal amount) {
        Optional<Account> sourceAccount = accountRepository.findById(sourceId);
        Optional<Account> destinationAccount = accountRepository.findById(destinationId);
        if(sourceAccount.isEmpty() ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SourceAccount not found");
        }
        if (destinationAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DestinationAccount not found");
        }

        sourceAccount.get().decreaseBalance(amount);
        destinationAccount.get().increaseBalance(amount);

        accountRepository.save(sourceAccount.get());
        accountRepository.save(destinationAccount.get());

    }
}
