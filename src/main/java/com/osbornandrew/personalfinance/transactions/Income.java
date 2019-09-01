package com.osbornandrew.personalfinance.transactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osbornandrew.personalfinance.accounts.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Income {

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private LocalDate date;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private double amount;

    @ManyToOne
    @JsonIgnoreProperties({"incomes"})
    @Getter @Setter
    private Account account;

    public Income() { }

    public Income(LocalDate date, String description, double amount) {

        this.date = date;
        this.description = description;
        this.amount = amount;
    }
}
