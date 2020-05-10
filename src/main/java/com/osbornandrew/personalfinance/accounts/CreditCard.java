package com.osbornandrew.personalfinance.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CreditCard extends Account implements Debt {

    @Column(precision = 2)
    @Getter @Setter
    private double limitAmt;

    @Column(precision = 2)
    @Getter @Setter
    private double minPayment;

    @Getter @Setter
    private float interestRate;

    @Getter @Setter
    private int dueDay;

    public CreditCard() {
        super();
    }

    @JsonCreator
    public CreditCard(@JsonProperty("name") String name,
                      @JsonProperty("description") String description,
                      @JsonProperty("balance") double balance,
                      @JsonProperty("limitAmt") double limitAmt,
                      @JsonProperty("minPayment") double minPayment,
                      @JsonProperty("interestRate") float interestRate,
                      @JsonProperty("dueDay") int dueDay) {
        super(name, description, balance, AccountType.CREDIT_CARD);
        this.limitAmt = limitAmt;
        this.minPayment = minPayment;
        this.interestRate = interestRate;
        this.dueDay = dueDay;
    }

    public float getMonthlyInterestRate(){
        return (interestRate / 12) / 100;
    }
}
