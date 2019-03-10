package com.osbornandrew.personalfinance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osbornandrew.personalfinance.accounts.Account;
import com.osbornandrew.personalfinance.users.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Wallet {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JsonIgnoreProperties({"wallet", "budget"})
    private User user;

    private String name;
    private String description;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"wallet"})
    private Set<Account> accounts;

    private float weeklyIncome;

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
