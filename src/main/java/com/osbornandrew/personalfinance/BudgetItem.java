package com.osbornandrew.personalfinance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class BudgetItem {

    @Id
    @GeneratedValue
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({"budgetItems"})
    @Getter @Setter
    private Budget budget;

    @Getter @Setter
    @ManyToOne
    private Category category;

    @Getter @Setter
    private double fraction;

    @Getter @Setter
    private double amount;

    // TODO: 12/14/2018 Add properties for start and end dates
}
