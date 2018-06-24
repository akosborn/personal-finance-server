package com.osbornandrew.personal.finance.server.accounts;

import com.osbornandrew.personal.finance.server.transactions.Expense;
import com.osbornandrew.personal.finance.server.transactions.Income;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public abstract class Account {

    @Getter private AccountType type;
    @Getter @Setter private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Getter @Setter private double balance;
    @Getter @Setter private Set<Expense> expenses;
    @Getter @Setter private Set<Income> incomes;

    public Account(String name, String description, double balance, AccountType type) {

        this.name = name;
        this.description = description;
        this.balance = balance;
        this.type = type;
    }
}
