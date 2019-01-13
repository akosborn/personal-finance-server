package com.osbornandrew.personalfinance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnoreProperties({"items", "fixedExpenses"})
    @Getter @Setter
    private Budget budget;

    @Getter @Setter
    @ManyToOne
    private Category category;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private double fraction;

    @Getter @Setter
    private double amount;

    // TODO: 12/14/2018 Add properties for start and end dates

    public BudgetItem() {}

    @JsonCreator
    public BudgetItem(@JsonProperty("category") String category,
                      @JsonProperty("description") String description,
                      @JsonProperty("amount") double amount){
        this.category = new Category(category);
        this.description = description;
        this.amount = amount;
    }
}
