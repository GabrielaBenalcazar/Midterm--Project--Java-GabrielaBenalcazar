package com.ironhack.midterm.project.models.account;

import com.ironhack.midterm.project.models.clasees.Money;
import com.ironhack.midterm.project.models.user.AccountHolders;
import com.ironhack.midterm.project.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "account_balance", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "account_balance_currency", nullable = false))
    }
    )
    private Money balance;
    @JoinColumn(name = "primary_owner")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AccountHolders primaryOwner;
    @JoinColumn(name = "secondary_owner")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private AccountHolders secondaryOwner;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "account_penalty_fee", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "account_penalty_fee_currency", nullable = false))
    })
    private Money penaltyFee;

    public Account(Money balance, AccountHolders primaryOwner, AccountHolders secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = Constants.PENALTY_FEE;
    }
    public Account(Money balance, AccountHolders primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = null;
        this.penaltyFee = Constants.PENALTY_FEE;
    }

    public void increaseBalance(BigDecimal addAmount) {
      balance.increaseAmount(addAmount);
    }

    public void decreaseBalance(BigDecimal subtractAmount) {
        balance.decreaseAmount(subtractAmount);
    }

    public void applyInterest() {

    }

}
