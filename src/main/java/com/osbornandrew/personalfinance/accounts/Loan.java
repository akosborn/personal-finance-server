package com.osbornandrew.personalfinance.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
public class Loan extends Account {

    public Loan() { }

    @Getter @Setter
    private float interestRate;

    @Getter @Setter
    private double minPayment;

    @Getter @Setter
    private int dueDay;

    public Loan(@JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("balance") double balance,
                @JsonProperty("minPayment") double minPayment,
                @JsonProperty("interestRate") float interestRate,
                @JsonProperty("dueDay") int dueDay) {
        super(name, description, balance, AccountType.LOAN);
        this.interestRate = interestRate;
        this.minPayment = minPayment;
        this.dueDay = dueDay;
    }
}
