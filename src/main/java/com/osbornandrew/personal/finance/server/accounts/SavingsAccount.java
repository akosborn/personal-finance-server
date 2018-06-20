package com.osbornandrew.personal.finance.server.accounts;

import lombok.Getter;
import lombok.Setter;

public class SavingsAccount extends Account {

    @Getter @Setter private float interestRate;

    public SavingsAccount(String name, String description, double balance, float interestRate) {

        super(name, description, balance, AccountType.SAVINGS);
        this.interestRate = interestRate;
    }
}
