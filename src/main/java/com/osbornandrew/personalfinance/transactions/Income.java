package com.osbornandrew.personalfinance.transactions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Income {

    @Getter @Setter private Long id;
    @Getter @Setter private LocalDate date;
    @Getter @Setter private String description;
    @Getter @Setter private double amount;

    public Income(String description, double amount) {

        this.date = LocalDate.now();
        this.description = description;
        this.amount = amount;
    }
}
