package com.osbornandrew.personalfinance.accounts;

public class Investment extends Account {

    public Investment(String name, String description, double balance) {
        super(name, description, balance, AccountType.INVESTMENT);
    }
}
