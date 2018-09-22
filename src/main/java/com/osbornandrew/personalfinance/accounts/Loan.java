package com.osbornandrew.personalfinance.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
public class Loan extends Account {

    @Getter @Setter private float interestRate;
    @Getter @Setter private double minPayment;

    public Loan(@JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("balance") double balance,
                @JsonProperty("minPayment") double minPayment,
                @JsonProperty("interestRate") float interestRate) {

        super(name, description, balance, AccountType.LOAN);
        this.interestRate = interestRate;
        this.minPayment = minPayment;
    }
}
