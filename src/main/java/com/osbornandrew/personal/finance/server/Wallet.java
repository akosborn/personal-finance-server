package com.osbornandrew.personal.finance.server;

import com.osbornandrew.personal.finance.server.accounts.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class Wallet {

    @Getter @Setter private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Getter @Setter private double balance;
    @Getter @Setter private Set<Account> accounts;

    public Wallet() {
    }

    public Wallet(String name, String description, Set<Account> accounts) {

        this.name = name;
        this.description = description;
        this.accounts = accounts;
    }
}
