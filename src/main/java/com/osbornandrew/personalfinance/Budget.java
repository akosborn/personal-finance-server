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
    @JsonIgnoreProperties({"budget"})
    @Getter @Setter
    private User user;

    @OneToMany(mappedBy = "budget")
    @Getter @Setter
    List<BudgetItem> items;
}
