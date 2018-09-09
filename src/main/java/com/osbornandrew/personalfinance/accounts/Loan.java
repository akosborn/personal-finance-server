package com.osbornandrew.personalfinance.accounts;

import lombok.Getter;
import lombok.Setter;

public class Loan extends Account {

    @Getter @Setter private float interestRate;
    @Getter @Setter private double minPayment;

    public Loan(String name, String description, double balance, float interestRate,
                double minPayment) {

        super(name, description, balance, AccountType.LOAN);
        this.interestRate = interestRate;
        this.minPayment = minPayment;
    }
}
