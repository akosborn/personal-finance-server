package com.osbornandrew.personalfinance.repayment;

import com.osbornandrew.personalfinance.accounts.Debt;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AccountPaymentSchedule {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private double totalInterestPaid;

    @Getter @Setter
    private int monthsToPaid;

    @Getter @Setter
    private Debt account;

    @Getter @Setter
    private List<PaymentRecord> paymentRecords = new ArrayList<>();

    public AccountPaymentSchedule(Debt account) {
        this.account = account;
    }

    public void addInterest(double interest){
        totalInterestPaid += interest;
    }
}
