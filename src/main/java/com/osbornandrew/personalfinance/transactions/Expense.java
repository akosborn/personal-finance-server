package com.osbornandrew.personalfinance.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.osbornandrew.personalfinance.accounts.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Expense {

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
    @JsonIgnoreProperties({"expenses"})
    @Getter @Setter
    private Account account;

    public Expense() { }

    @JsonCreator
    public Expense(@JsonProperty("date") String date,
                   @JsonProperty("description") String description,
                   @JsonProperty("amount") double amount) {
        // TODO: 10/12/2018 Parse actual date once client side pattern is defined
        this.date = LocalDate.now();
        this.description = description;
        this.amount = amount;
    }

    public Expense(LocalDate date, String description, double amount) {

        this.date = date;
        this.description = description;
        this.amount = amount;
    }
}
