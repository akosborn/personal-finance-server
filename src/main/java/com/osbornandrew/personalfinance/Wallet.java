package com.osbornandrew.personalfinance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osbornandrew.personalfinance.accounts.*;
import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.transactions.Income;
import com.osbornandrew.personalfinance.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
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

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String description;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"wallet"})
    @Getter @Setter
    private Set<Account> accounts;

    public Wallet() { }

    public Wallet(User user) {
        this.user = user;
    }

    public Wallet(String name, String description, Set<Account> accounts) {

        this.name = name;
        this.description = description;
        this.accounts = accounts;
    }
}
