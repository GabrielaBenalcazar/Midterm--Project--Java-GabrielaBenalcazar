package com.ironhack.midterm.project.controller.impl;

import com.ironhack.midterm.project.controller.interfaces.AccountController;
import com.ironhack.midterm.project.dto.Transfer;
import com.ironhack.midterm.project.models.account.Account;
import com.ironhack.midterm.project.models.clasees.Money;
import com.ironhack.midterm.project.models.user.Role;
import com.ironhack.midterm.project.models.user.ThirdParty;

import com.ironhack.midterm.project.repository.AccountRepository;
import com.ironhack.midterm.project.security.UserRoleDetails;
import com.ironhack.midterm.project.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@RestController
public class AccountControllerImpl implements AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    /**
     * AccountHolders should be able to access their own account balance
     *
     * Admins should be able to access the balance for any account and to modify it.
     */
    @GetMapping("/account/{id}/balance")
    @ResponseStatus(HttpStatus.OK)
    public Money getBalance(@AuthenticationPrincipal UserRoleDetails userDetails,
                            @PathVariable(name="id") Long id) {
        Role roleAdmin = new Role("ADMIN");
        Role roleAccountHolder = new Role("ACCOUNT_HOLDER");
        if (userDetails.getRoles().contains(roleAdmin)) {
            return accountService.getBalance(id);
        } else if (userDetails.getRoles().contains(roleAccountHolder)) {
            Optional<Account> optionalAccount = accountRepository.findById(id);

            if (optionalAccount.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }

            Account account = optionalAccount.get();

            if (!Objects.equals(account.getPrimaryOwner().getId(), userDetails.getId())) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }

            return accountService.getBalance(id);
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Admins should be able to access the balance for any account and to modify it.
     */
    @PatchMapping("/account/{id}/balance")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setBalance(Long id, Money balance) {
        accountService.setBalance(id, balance);
    }

    /**
     * Account holders should be able to transfer money from any of their accounts to any other account
     * (regardless of owner). The transfer should only be processed if the account has sufficient funds.
     *
     * The user must provide the Primary or Secondary owner name and the id of the account
     * that should receive the transfer.
     * There must be a way for third-party users to receive and send money to other accounts.
     *
     * In order to receive and send money, Third-Party Users must provide their hashed key in the header
     * of the HTTP request. They also must provide the amount, the Account id and the account secret key
     */
    @PostMapping("/account/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transferMoney(@AuthenticationPrincipal UserRoleDetails userDetails,
                              @RequestBody(required = false) Transfer transfer) {
        Optional<Account> optionalAccount = accountRepository.findById(transfer.getSourceAccount());

        if (optionalAccount.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        Account account = optionalAccount.get();
        Role roleAdmin = new Role("ADMIN");
        Role roleAccountHolder = new Role("ACCOUNT_HOLDER");

        if (userDetails.getRoles().contains(roleAccountHolder)) {
            if (!Objects.equals(transfer.getPrimaryOwnerName(), account.getPrimaryOwner().getName()) ||
                    !Objects.equals(transfer.getSecondaryOwnerName(), account.getSecondaryOwner().getName())
            ) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            ThirdParty thirdParty = (ThirdParty) userDetails.getUser();
            if (!Objects.equals(transfer.getHashedKey(), thirdParty.getPassword())) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
        }

        accountService.transferMoney(transfer.getSourceAccount(), transfer.getDestinationAccount(), transfer.getAmount().getAmount());
    }
}
