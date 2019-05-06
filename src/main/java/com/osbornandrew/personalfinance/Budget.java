package com.osbornandrew.personalfinance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Budget {

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @OneToOne
    @JsonIgnoreProperties({"budget", "wallet"})
    @Getter @Setter
    private User user;

    @OneToMany(mappedBy = "budget")
    @Getter @Setter
    Set<BudgetItem> items = new LinkedHashSet<>();

    public Budget(){}

    public Budget(User user){
        this.user = user;
    }
}
