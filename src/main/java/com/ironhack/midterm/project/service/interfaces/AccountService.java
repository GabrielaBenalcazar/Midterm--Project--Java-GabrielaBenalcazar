package com.ironhack.midterm.project.service.interfaces;

import com.ironhack.midterm.project.models.clasees.Money;

import java.math.BigDecimal;

public interface AccountService {

    Money getBalance(Long id);

    Money getBalanceForUser(Long accountId, Long userId);
    void setBalance(Long id, Money balance);
    void transferMoney(Long sourceId, Long destinationId, BigDecimal amount);

}
