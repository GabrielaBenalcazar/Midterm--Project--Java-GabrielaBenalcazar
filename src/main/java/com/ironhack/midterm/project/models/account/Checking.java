package com.ironhack.midterm.project.models.account;

import com.ironhack.midterm.project.enums.Status;
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
public class Checking extends Account{

    private  int secretKey;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_checking", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_checking_currency", nullable = false))
    }
    )
    private Money minimumBalance;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_checking", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_checking_currency", nullable = false))
    }
    )
    private Money monthlyMaintenanceFee;
    @Column(name = "creation_date_checking")
    private String creationDate;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public Checking(Money balance, AccountHolders primaryOwner, AccountHolders secondaryOwner,
                    int secretKey, String creationDate, Status status) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = Constants.DEFAULT_MINIMUM_BALANCE_CHECKING;
        this.monthlyMaintenanceFee = Constants.MONTHLY_MAINTENANCE_FEE_CHECKING;
        this.creationDate = creationDate;
        this.status = status;
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


}
