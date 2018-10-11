package com.osbornandrew.personalfinance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osbornandrew.personalfinance.accounts.*;
import com.osbornandrew.personalfinance.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Wallet {

    @Id @GeneratedValue
    @Getter @Setter
    private Long id;

    @OneToOne
    @JsonIgnoreProperties({"wallet"})
    @Getter @Setter
    private User user;

    @Getter @Setter private String name;
    @Getter @Setter private String description;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"wallet"})
    @Getter @Setter private Set<CheckingAccount> checkingAccounts;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"wallet"})
    @Getter @Setter private Set<SavingsAccount> savingsAccounts;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"wallet"})
    @Getter @Setter private Set<Loan> loans;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"wallet"})
    @Getter @Setter private Set<CreditCard> creditCards;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"wallet"})
    @Getter @Setter private Set<Investment> investments;

    public Wallet() { }

    public Wallet(User user) {
        this.user = user;
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
