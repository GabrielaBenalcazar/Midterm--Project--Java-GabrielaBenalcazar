package com.ironhack.midterm.project.models.account;

import com.ironhack.midterm.project.enums.Status;

import com.ironhack.midterm.project.models.clasees.Money;
import com.ironhack.midterm.project.models.user.AccountHolders;
import com.ironhack.midterm.project.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.ironhack.midterm.project.utils.Constants.MAX_INTEREST_RATE_SAVINGS;

@Entity
@Getter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Savings extends Account {
    private  int secretKey;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_savings", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_savings_currency", nullable = false))
    }
    )
    private Money minimumBalance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_savings", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_savings_currency", nullable = false))
    }
    )
    private Money monthlyMaintenanceFee;
    @Column(name = "creation_date_savings")
    private String creationDate;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    private BigDecimal interestRate;

    public Savings(Money balance, AccountHolders primaryOwner, AccountHolders secondaryOwner, int secretKey, Money monthlyMaintenanceFee, String creationDate, Status status)
    {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = Constants.DEFAULT_MINIMUM_BALANCE_SAVINGS;;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.creationDate = creationDate;
        this.status = status;
        this.interestRate = Constants.DEFAULT_INTEREST_RATE_SAVINGS;
    }

    public Savings(Money balance, AccountHolders primaryOwner, AccountHolders secondaryOwner, int secretKey,
                   Money minimumBalance, Money monthlyMaintenanceFee, String creationDate, Status status,
                   BigDecimal interestRate)
    {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.creationDate = creationDate;
        this.status = status;
        if (isValidInterestRate(interestRate)) {
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Invalid interest rate");
        }
        if (isValidMinimumBalance(minimumBalance)) {
            this.minimumBalance = minimumBalance;
        } else {
            throw new IllegalArgumentException("Invalid minimum balance");
        }
    }

    //setters
    public void setInterestRate(BigDecimal interestRate) {
        if (isValidInterestRate(interestRate)) {
            this.interestRate = interestRate;
        } else {
            throw new IllegalArgumentException("Invalid interest rate");
        }
    }

    public void setMinimumBalance(Money minimumBalance) {
        if (isValidMinimumBalance(minimumBalance)) {
            this.minimumBalance = minimumBalance;
        } else {
            throw new IllegalArgumentException("Invalid minimum balance");
        }
    }
    //validation methods
    public boolean isValidInterestRate(BigDecimal interestRate) {
        return interestRate.compareTo(BigDecimal.ZERO) >= 0 && interestRate.compareTo(MAX_INTEREST_RATE_SAVINGS) <= 0;

    }
    public boolean isValidMinimumBalance(Money minimumBalance) {
        return minimumBalance.getAmount().compareTo(Constants.MIN_MINIMUM_BALANCE_SAVINGS.getAmount()) >= 0 &&  minimumBalance.getAmount().compareTo(Constants.DEFAULT_MINIMUM_BALANCE_SAVINGS.getAmount()) <= 0;
    }
    @Override
    public void increaseBalance(BigDecimal addAmount) {
        super.increaseBalance(addAmount);
    }

    @Override
    public void decreaseBalance(BigDecimal subtractAmount) {

        super.decreaseBalance(subtractAmount);

        if(super.getBalance().getAmount().compareTo(this.minimumBalance.getAmount()) < 0){
            super.decreaseBalance(this.getPenaltyFee().getAmount());
        }
    }
    @Override
    public void applyInterest() {
        super.applyInterest();
        BigDecimal balance = super.getBalance().getAmount();
       increaseBalance(balance.multiply(this.interestRate));
    }
}
