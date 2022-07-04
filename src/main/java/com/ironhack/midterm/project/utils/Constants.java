package com.ironhack.midterm.project.utils;

import com.ironhack.midterm.project.models.clasees.Money;

import java.math.BigDecimal;

public abstract class Constants {

    //ALL ACCOUNT TYPES CONSTANTS

    public static final Money PENALTY_FEE  = new Money(new BigDecimal("40"));

    //SAVINGS CONSTANTS
    public static final BigDecimal DEFAULT_INTEREST_RATE_SAVINGS = new BigDecimal("0.0025");
    public static final BigDecimal MAX_INTEREST_RATE_SAVINGS = new BigDecimal("0.5");

    public static final Money DEFAULT_MINIMUM_BALANCE_SAVINGS = new Money(new BigDecimal(1000));

    public static final Money MIN_MINIMUM_BALANCE_SAVINGS =  new Money(new BigDecimal(100));

    //CREDIT CARD CONSTANTS
    public static final BigDecimal DEFAULT_INTEREST_RATE_CREDIT_CARD = new BigDecimal("0.2");
    public static final BigDecimal MIN_INTEREST_RATE_CREDIT_CARD = new BigDecimal("0.1");
    public static final Money DEFAULT_CREDIT_LIMIT=  new Money(new BigDecimal(100));
    public static final Money MAX_CREDIT_LIMIT=  new Money(new BigDecimal(100000));
// CHECKING CONSTANTS

    public static final Money DEFAULT_MINIMUM_BALANCE_CHECKING = new Money(new BigDecimal(250));
    public static final Money MONTHLY_MAINTENANCE_FEE_CHECKING =  new Money(new BigDecimal(12));
}
