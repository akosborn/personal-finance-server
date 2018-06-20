package com.osbornandrew.personal.finance.server.accounts;

public class CheckingAccount extends Account {

    public CheckingAccount(String name, String description, double balance) {

        super(name, description, balance, AccountType.CHECKING);

    }
}
