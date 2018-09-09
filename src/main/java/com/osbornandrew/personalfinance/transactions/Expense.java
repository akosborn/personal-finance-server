package com.osbornandrew.personalfinance.transactions;

import com.osbornandrew.personalfinance.accounts.Account;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Expense {

    @Getter @Setter private Long id;
    @Getter @Setter private LocalDate date;
    @Getter @Setter private String description;
    @Getter @Setter private double amount;
    @Getter @Setter private Account account;

    public Expense(String description, double amount) {

        this.date = LocalDate.now();
        this.description = description;
        this.amount = amount;
    }
}
