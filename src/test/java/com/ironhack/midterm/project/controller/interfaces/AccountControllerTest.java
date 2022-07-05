package com.ironhack.midterm.project.controller.interfaces;

import com.ironhack.midterm.project.enums.Status;
import com.ironhack.midterm.project.models.account.*;
import com.ironhack.midterm.project.models.clasees.Address;
import com.ironhack.midterm.project.models.clasees.Money;
import com.ironhack.midterm.project.models.user.AccountHolders;
import com.ironhack.midterm.project.models.user.Admin;
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
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    private AccountHolders accountHolder1;
    private AccountHolders accountHolder2;
    private Admin admin;
    private ThirdParty thirdParty;

    private Account savingsAccount;
    private Account checkingAccount;
    private Account studentCheckingAccount;
    private Account creditCardAccount;

    private String holder1Auth = "Basic aG9sZGVyMTpwYXNzMQ==";
    private String holder2Auth = "Basic aG9sZGVyMjpwYXNzMg==";
    private String adminAuth = "Basic YWRtaW46cGFzcw==";
    private String thirdPartyAuth = "Basic dGhpcmQ6cGFzczQ=";

    @BeforeEach
    void setUp() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Address addr = new Address(0, "", "", "", "");

        accountHolder1 = new AccountHolders("holder1", passwordEncoder.encode("pass1"), "date", addr, "addr");
        accountHolder2 = new AccountHolders("holder2", passwordEncoder.encode("pass2"), "date", addr, "addr");
        admin = new Admin("admin", passwordEncoder.encode("pass"));
        thirdParty = new ThirdParty("third", passwordEncoder.encode("pass4"), "hash");

        userRepository.saveAll(List.of(accountHolder1, accountHolder2, admin, thirdParty));

        savingsAccount = new Savings(new Money(new BigDecimal(1000)), accountHolder1,
                accountHolder2, 0, new Money(new BigDecimal(100)), "date", Status.ACTIVE);
        checkingAccount = new Checking(new Money(new BigDecimal(1100)), accountHolder2,
                accountHolder1, 0, "date", Status.ACTIVE);
        studentCheckingAccount = new StudentChecking(new Money(new BigDecimal(1300)), accountHolder1,
                accountHolder2, 0, "date", Status.ACTIVE);
        creditCardAccount = new CreditCard(new Money(new BigDecimal(1200)), accountHolder1, accountHolder2);

        accountRepository.saveAll(List.of(savingsAccount, checkingAccount, studentCheckingAccount, creditCardAccount));
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getBalance_1() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", holder1Auth);
//
//        String route = "/account/" +
//                savingsAccount.getId() +
//                "/balance";
//
//        MvcResult mvcResult = mockMvc.perform(get(route).headers(headers))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        assertTrue(mvcResult.getResponse().getContentAsString().contains("1000"));
    }

    @Test
    void setBalance() {

    }

    @Test
    void transferMoney() {

    }
}