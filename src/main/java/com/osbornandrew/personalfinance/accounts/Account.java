package com.osbornandrew.personalfinance.accounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.osbornandrew.personalfinance.Wallet;
import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.transactions.Income;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CheckingAccount.class, name = "CHECKING"),
        @JsonSubTypes.Type(value = CreditCard.class, name = "CREDIT_CARD"),
        @JsonSubTypes.Type(value = Investment.class, name = "INVESTMENT"),
        @JsonSubTypes.Type(value = Loan.class, name = "LOAN"),
        @JsonSubTypes.Type(value = SavingsAccount.class, name = "SAVINGS")
})
public abstract class Account {

    @Getter
    private AccountType type;

    @ManyToOne
    @JsonIgnoreProperties({"accounts"})
    @Getter @Setter
    private Wallet wallet;

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private double balance;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"account"})
    @Getter @Setter
    private Set<Expense> expenses;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"account"})
    @Getter @Setter
    private Set<Income> incomes;

    public Account() { }

    public Account(String name, String description, double balance, AccountType type) {

        this.name = name;
        this.description = description;
        this.balance = balance;
        this.type = type;
    }
}
