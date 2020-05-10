package com.osbornandrew.personalfinance.accounts;

public interface Debt {

    double getBalance();
    float getInterestRate();
    double getMinPayment();
    float getMonthlyInterestRate();
}
