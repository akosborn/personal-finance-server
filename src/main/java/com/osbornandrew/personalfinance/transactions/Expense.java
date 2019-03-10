package com.osbornandrew.personalfinance.transactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.osbornandrew.personalfinance.Budget;
import com.osbornandrew.personalfinance.Category;
import com.osbornandrew.personalfinance.accounts.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Expense {

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @Transient
    @Getter @Setter
    private Long accountId;

    @Getter @Setter
    private LocalDate date;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private double amount;

    @ManyToOne
    @JsonIgnoreProperties({"expenses", "incomes", "balance", "description"})
    @Getter @Setter
    private Account account;

    @ManyToOne
    @JsonIgnoreProperties({"fixedExpenses", "items"})
    @Getter @Setter
    private Budget budget;

    @Getter @Setter
    private Frequency frequency;

    @Getter @Setter
    private int fixedDayOfMonth;

    @Getter @Setter
    @ManyToOne
    private Category category;

    @Transient
    @Setter
    private String categoryName;

    public Expense() { }

    @JsonCreator
    public Expense(@JsonProperty("date") String date,
                   @JsonProperty("description") String description,
                   @JsonProperty("amount") double amount,
                   @JsonProperty("frequency") int frequency,
                   @JsonProperty("accountId") Long accountId,
                   @JsonProperty("category") String categoryName) {
        // TODO: 10/12/2018 Parse actual date once client side pattern is defined
        this.date = LocalDate.now();
        this.description = description;
        this.amount = amount;
        this.frequency = Frequency.values()[frequency]; // TODO: 1/13/2019 Handle IndexOutOfBoundsException
        this.accountId = accountId;
        this.categoryName = categoryName;
        // TODO: 1/13/2019 Set date
    }

    public Expense(LocalDate date, String description, double amount) {
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public Expense(int fixedDayOfMonth, String description, double amount, Account account,
                   Frequency frequency, Budget budget, Category category) {
        this.fixedDayOfMonth = fixedDayOfMonth;
        this.description = description;
        this.amount = amount;
        this.account = account;
        this.frequency = frequency;
        this.budget = budget;
        this.category = category;
    }

    public String getCatName() {
        return categoryName;
    }
}
