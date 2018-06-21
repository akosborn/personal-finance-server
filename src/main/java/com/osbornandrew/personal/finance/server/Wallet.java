package com.osbornandrew.personal.finance.server;

import com.osbornandrew.personal.finance.server.accounts.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class Wallet {

    @Getter @Setter private Long id;
    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Getter @Setter private Set<CheckingAccount> checkingAccounts;
    @Getter @Setter private Set<SavingsAccount> savingsAccounts;
    @Getter @Setter private Set<Loan> loans;
    @Getter @Setter private Set<CreditCard> creditCards;
    @Getter @Setter private Set<Investment> investments;

    public Wallet() {
    }

    public Wallet(String name, String description, Set<CheckingAccount> checkingAccounts,
                  Set<SavingsAccount> savingsAccounts, Set<Loan> loans,
                  Set<CreditCard> creditCards, Set<Investment> investments) {

        this.name = name;
        this.description = description;
        this.checkingAccounts = checkingAccounts;
        this.savingsAccounts = savingsAccounts;
        this.loans = loans;
        this.creditCards = creditCards;
        this.investments = investments;
    }
}
