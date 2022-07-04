package com.ironhack.midterm.project.controller.interfaces;

import com.ironhack.midterm.project.dto.Transfer;
import com.ironhack.midterm.project.models.clasees.Money;
import com.ironhack.midterm.project.security.UserRoleDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

public interface AccountController {
    Money getBalance(UserRoleDetails userDetails, Long id);
    void setBalance(Long id, Money balance);
    void transferMoney(UserRoleDetails user, Transfer transfer);
}
