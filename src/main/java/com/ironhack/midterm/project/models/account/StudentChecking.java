package com.ironhack.midterm.project.models.account;

import com.ironhack.midterm.project.enums.Status;
import com.ironhack.midterm.project.models.clasees.Money;
import com.ironhack.midterm.project.models.user.AccountHolders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class StudentChecking extends Account{
    private  int secretKey;
    @Column(name = "creation_date_student_checking")
    private String creationDate;
    @Enumerated(value = EnumType.STRING)
    private Status status;


    public StudentChecking(Money balance, AccountHolders primaryOwner, AccountHolders secondaryOwner, int secretKey, String creationDate, Status status) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.creationDate = creationDate;
        this.status = status;
    }
}
