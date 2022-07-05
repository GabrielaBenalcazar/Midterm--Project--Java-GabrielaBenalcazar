package com.ironhack.midterm.project.service.interfaces;

import com.ironhack.midterm.project.enums.Status;
import com.ironhack.midterm.project.models.account.Account;
import com.ironhack.midterm.project.models.account.Checking;
import com.ironhack.midterm.project.models.clasees.Address;
import com.ironhack.midterm.project.models.clasees.Money;
import com.ironhack.midterm.project.models.user.AccountHolders;
import com.ironhack.midterm.project.models.user.ThirdParty;
import com.ironhack.midterm.project.models.user.User;
import com.ironhack.midterm.project.repository.AccountRepository;
import com.ironhack.midterm.project.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    private Account acc1;
    private Account acc2;

    @BeforeEach
    void setup() {
        Address address = new Address(0, "", "", "", "");
        AccountHolders user = new AccountHolders("name", "pass", "date", address, "address");
        AccountHolders user2 = new AccountHolders("name2", "pass2", "date2", address, "address2");

        userRepository.save(user);
        userRepository.save(user2);

        acc1 = new Checking(new Money(new BigDecimal(1000)), user, user2, 0, "date", Status.ACTIVE);
        acc2 = new Checking(new Money(new BigDecimal(2000)), user2, user, 0, "date", Status.ACTIVE);

        accountRepository.save(acc1);
        accountRepository.save(acc2);
    }

    @AfterEach
    void teardown() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getBalance_1() {
        BigDecimal balance = accountService.getBalance(acc1.getId()).getAmount();

        assertEquals(new BigDecimal("1000.00"), balance);
    }

    @Test
    void getBalance_2() {
        BigDecimal balance = accountService.getBalance(acc2.getId()).getAmount();

        assertEquals(new BigDecimal("2000.00"), balance);
    }

    @Test
    void setBalance_1() {
        accountService.setBalance(acc1.getId(), new Money(new BigDecimal(25)));

        BigDecimal balance = accountService.getBalance(acc1.getId()).getAmount();

        assertEquals(new BigDecimal("25.00"), balance);
    }
    @Test
    void setBalance_2() {
        accountService.setBalance(acc2.getId(), new Money(new BigDecimal(45)));

        BigDecimal balance = accountService.getBalance(acc2.getId()).getAmount();

        assertEquals(new BigDecimal("45.00"), balance);
    }

    @Test
    void transferMoney_1() {
        accountService.transferMoney(acc1.getId(), acc2.getId(), new BigDecimal(5));

        BigDecimal balance1 = accountService.getBalance(acc1.getId()).getAmount();
        BigDecimal balance2 = accountService.getBalance(acc2.getId()).getAmount();

        assertEquals(new BigDecimal("995.00"), balance1);
        assertEquals(new BigDecimal("2005.00"), balance2);
    }

    @Test
    void transferMoney_2() {
        assertThrows(ResponseStatusException.class, () ->
            accountService.transferMoney(acc1.getId(), acc2.getId(), new BigDecimal("5000"))
        );
    }
}