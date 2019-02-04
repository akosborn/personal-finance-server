package com.osbornandrew.personalfinance.repayment;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PaymentPlan {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private double budget;

    @Getter @Setter
    private double totalInterestPaid;

    @Getter @Setter
    private double totalBalance;

    @Getter @Setter
    List<AccountPaymentSchedule> schedules = new ArrayList<>();

    public PaymentPlan(double budget, double totalBalance) {
        this.budget = budget;
        this.totalBalance = totalBalance;
    }

    public void addInterest(double interest){
        totalInterestPaid += interest;
    }
}
