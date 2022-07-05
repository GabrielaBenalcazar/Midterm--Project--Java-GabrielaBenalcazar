package com.ironhack.midterm.project.controller.interfaces;

import com.ironhack.midterm.project.enums.Status;
import com.ironhack.midterm.project.models.account.*;
import com.ironhack.midterm.project.models.clasees.Address;
import com.ironhack.midterm.project.models.clasees.Money;
import com.ironhack.midterm.project.models.user.*;
import com.ironhack.midterm.project.repository.AccountRepository;
import com.ironhack.midterm.project.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

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
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Role roleAccountHolder = new Role("ACCOUNT_HOLDER");
        Role roleAdmin = new Role("ADMIN");
        Role roleThirdParty = new Role("THIRD_PARTY");

        Address address = new Address(0, "", "", "", "");
        AccountHolders accountHolder1 = new AccountHolders("name", passwordEncoder.encode("pass1"),"12.12.12",address,address);
        accountHolder1.setRoles(Set.of(roleAccountHolder));
        AccountHolders accountHolder2 = new AccountHolders("name2",  passwordEncoder.encode("pass1"), "date2", address, address);
        accountHolder2.setRoles(Set.of(roleAccountHolder));
        admin = new Admin("admin", passwordEncoder.encode("pass"));
        admin.setRoles(Set.of(roleAdmin));
        thirdParty = new ThirdParty("thirdParty", passwordEncoder.encode("pass4"));
        thirdParty.setRoles(Set.of(roleThirdParty));

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

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", holder1Auth);


        String route = "/account/" +
                savingsAccount.getId() +
                "/balance";

        MvcResult mvcResult = mockMvc.perform(get(route).headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("1000"));
    }

    @Test
    void setBalance() {

    }

    @Test
    void transferMoney() {

    }
}