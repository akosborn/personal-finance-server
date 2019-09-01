package com.osbornandrew.personalfinance.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
public class SavingsAccount extends Account {

    @Getter @Setter
    private float interestRate;

    public SavingsAccount() {
        super();
    }

    @JsonCreator
    public SavingsAccount(@JsonProperty("name") String name,
                          @JsonProperty("description") String description,
                          @JsonProperty("balance") double balance,
                          @JsonProperty("interestRate") float interestRate) {

        super(name, description, balance, AccountType.SAVINGS);
        this.interestRate = interestRate;
    }
}
