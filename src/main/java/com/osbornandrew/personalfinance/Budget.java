package com.osbornandrew.personalfinance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.users.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
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

    @OneToMany(mappedBy = "budget")
    @Getter @Setter
    Set<Expense> fixedExpenses = new LinkedHashSet<>();

    public Budget(){}

    public Budget(User user){
        this.user = user;
    }
}
