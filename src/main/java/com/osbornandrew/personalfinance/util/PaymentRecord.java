package com.osbornandrew.personalfinance.util;

import lombok.Getter;
import lombok.Setter;

public class PaymentRecord {

    @Getter @Setter
    private int month;

    @Getter @Setter
    private double paymentAmount;

    @Getter @Setter
    private double balance;

    @Getter @Setter
    private double interestAccrued;

    @Getter @Setter
    private double principalPaid;

    public PaymentRecord(int month, double paymentAmount, double balance, double interestAccrued, double principalPaid) {
        this.month = month;
        this.paymentAmount = paymentAmount;
        this.balance = balance;
        this.interestAccrued = interestAccrued;
        this.principalPaid = principalPaid;
    }

    @Override
    public String toString() {
        return "PaymentRecord{" +
                "month=" + month +
                ", paymentAmount=" + paymentAmount +
                ", balance=" + balance +
                ", interestAccrued=" + interestAccrued +
                ", principalPaid=" + principalPaid +
                '}';
    }
}
