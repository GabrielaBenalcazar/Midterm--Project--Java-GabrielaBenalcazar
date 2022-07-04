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
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends Account{
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency", nullable = false))
    }
    )
    private Money creditLimit;

    private BigDecimal interestRate;

    public CreditCard(Money balance, AccountHolders primaryOwner, AccountHolders secondaryOwner) {
        super(balance, primaryOwner, secondaryOwner);
        this.creditLimit = Constants.DEFAULT_CREDIT_LIMIT;
        this.interestRate = Constants.DEFAULT_INTEREST_RATE_CREDIT_CARD;
    }

    public CreditCard(Money creditLimit, BigDecimal interestRate) {
        if (isValidCreditLimit(creditLimit)) {
            this.creditLimit = creditLimit;
        } else {
            throw new IllegalArgumentException("Invalid credit limit");
        }

        if (isValidInterestRate(interestRate)) {
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Invalid interest rate");
        }
    }
    //setters
    public void setCreditLimit(Money creditLimit) {
        if (isValidCreditLimit(creditLimit)) {
            this.creditLimit = creditLimit;
        } else {
            throw new IllegalArgumentException("Invalid credit limit");
        }
    }
    public void setInterestRate(BigDecimal interestRate) {
        if (isValidInterestRate(interestRate)) {
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Invalid interest rate");
        }
    }
    //validation methods
    public boolean isValidCreditLimit(Money creditLimit) {
        return creditLimit.getAmount().compareTo(Constants.DEFAULT_CREDIT_LIMIT.getAmount()) >= 0 && creditLimit.getAmount().compareTo(Constants.MAX_CREDIT_LIMIT.getAmount()) <= 0;
    }

    public boolean isValidInterestRate(BigDecimal interestRate){
        return interestRate.compareTo(Constants.MIN_INTEREST_RATE_CREDIT_CARD)>=0 && interestRate.compareTo(Constants.DEFAULT_INTEREST_RATE_CREDIT_CARD)<=0;
    }

    @Override
    public void applyInterest() {
        super.applyInterest();
        BigDecimal balance = super.getBalance().getAmount();
        increaseBalance(balance.multiply(this.interestRate.divide(new BigDecimal(12))));
    }




}
