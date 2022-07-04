package com.ironhack.midterm.project.dto;

import com.ironhack.midterm.project.models.clasees.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class Transfer {
    private Long sourceAccount;
    private Long destinationAccount;
    private Money amount;

    private String primaryOwnerName;
    private String secondaryOwnerName;

    private String hashedKey;
}
