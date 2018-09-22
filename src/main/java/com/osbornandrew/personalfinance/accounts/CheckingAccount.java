package com.osbornandrew.personalfinance.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class CheckingAccount extends Account {

    public CheckingAccount() {
        super();
    }

    @JsonCreator
    public CheckingAccount(@JsonProperty("name") String name,
                           @JsonProperty("description") String description,
                           @JsonProperty("balance") double balance) {

        super(name, description, balance, AccountType.CHECKING);
    }
}
