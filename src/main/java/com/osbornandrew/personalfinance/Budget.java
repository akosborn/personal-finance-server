package com.osbornandrew.personalfinance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osbornandrew.personalfinance.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "budget", fetch = FetchType.EAGER)
    @Getter @Setter
    List<BudgetItem> items;

    public Budget(){}

    public Budget(User user){
        this.user = user;
    }
}
