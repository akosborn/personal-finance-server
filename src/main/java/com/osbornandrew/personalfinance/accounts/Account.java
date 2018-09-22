package com.osbornandrew.personalfinance.accounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osbornandrew.personalfinance.Wallet;
import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.transactions.Income;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@MappedSuperclass
public abstract class Account {

    public Account() {
    }

    @Getter
    private AccountType type;

    @ManyToOne
    @JsonIgnoreProperties({"checkingAccounts", "savingsAccounts", "creditCards", "loans"})
    @Getter @Setter
    private Wallet wallet;

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private double balance;

    @Transient
    @Getter @Setter
    private Set<Expense> expenses;

    @Transient
    @Getter @Setter
    private Set<Income> incomes;

    public Account(String name, String description, double balance, AccountType type) {

        this.name = name;
        this.description = description;
        this.balance = balance;
        this.type = type;
    }
}
