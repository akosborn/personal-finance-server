package com.osbornandrew.personal.finance.server.accounts;

import lombok.Getter;
import lombok.Setter;

public class CreditCard extends Account {

    @Getter @Setter private double limit;
    @Getter @Setter private double minPayment;
    @Getter @Setter private float interestRate;

    public CreditCard(String name, String description, double balance,
                      double limit, double minPayment, float interestRate) {

        super(name, description, balance, AccountType.CREDIT_CARD);
        this.limit = limit;
        this.minPayment = minPayment;
        this.interestRate = interestRate;
    }
}
