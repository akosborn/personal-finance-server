package com.osbornandrew.personalfinance.accounts;

public class CheckingAccount extends Account {

    public CheckingAccount(String name, String description, double balance) {

        super(name, description, balance, AccountType.CHECKING);

    }
}
