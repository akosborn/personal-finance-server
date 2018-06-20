package com.osbornandrew.personal.finance.server.accounts;

import lombok.Getter;
import lombok.Setter;

public abstract class Account {

    @Getter private AccountType type;
    @Getter @Setter private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Getter @Setter private double balance;

    public Account(String name, String description, double balance, AccountType type) {

        this.name = name;
        this.description = description;
        this.balance = balance;
        this.type = type;
    }
}
