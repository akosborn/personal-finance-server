package com.osbornandrew.personalfinance.util;

import com.osbornandrew.personalfinance.accounts.Account;
import com.osbornandrew.personalfinance.accounts.CreditCard;
import com.osbornandrew.personalfinance.accounts.Debt;
import com.osbornandrew.personalfinance.accounts.Loan;
import com.osbornandrew.personalfinance.repayment.AccountPaymentSchedule;
import com.osbornandrew.personalfinance.repayment.PaymentPlan;
import com.osbornandrew.personalfinance.repayment.PaymentRecord;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RepaymentCalculator {

    private static DecimalFormat df = new DecimalFormat("#.00");

//    public static void main(String[] args) {
//        Loan personal = new Loan("Discover", "personal", 6000, 184, 11f, 1);
//        CreditCard cc = new CreditCard("Citi", "meh", 900, 5000, 25, 19.5f, 1);
//        List<Debt> accounts = new ArrayList<>();
//        accounts.add(personal);
//        accounts.add(cc);
//
//        PaymentPlan avalanche = RepaymentCalculator.buildAvalanchePlan(accounts);
//        PaymentPlan snowball = RepaymentCalculator.buildSnowballPlan(accounts);
//    }

    /**
     * Generates a payment plan based on the snowball debt payment plan.
     *
     * @param  accounts List of accounts implementing Debt interface
     * @return PaymentPlan
     */
    public static PaymentPlan buildSnowballPlan(List<Debt> accounts){
        // Sort by highest balance
        accounts.sort((o1, o2) -> Double.compare(o2.getBalance(), o1.getBalance()));
        return buildSnowballPlan(accounts, sumMinPayments(accounts, 0, accounts.size()));
    }

    public static PaymentPlan buildSnowballPlan(List<Debt> accounts, double budget){
        // Sort by highest balance
        accounts.sort((o1, o2) -> Double.compare(o2.getBalance(), o1.getBalance()));
        return setupPaymentPlan(accounts, budget);
    }

    public static PaymentPlan buildAvalanchePlan(List<Debt> accounts){
        return buildAvalanchePlan(accounts, sumMinPayments(accounts, 0, accounts.size()));
    }

    public static PaymentPlan buildAvalanchePlan(List<Debt> accounts, double budget){
        // Sort by highest interest rate
        accounts.sort((o1, o2) -> Float.compare(o2.getInterestRate(), o1.getInterestRate()));
        return setupPaymentPlan(accounts, budget);
    }

    private static PaymentPlan setupPaymentPlan(List<Debt> accounts, double budget){
        PaymentPlan plan = new PaymentPlan(budget, sumTotalBalance(accounts, 0, accounts.size()));
        for (Debt debt : accounts){
            plan.getSchedules().add(new AccountPaymentSchedule(debt));
        }

        double excess = budget - sumMinPayments(accounts, 0, accounts.size());
        return payMonth(0, plan, excess, 0, budget);
    }

    private static PaymentPlan payMonth(int start, PaymentPlan plan, double excess, int month, double budget){
        for (int acctIndex = start; acctIndex < plan.getSchedules().size(); acctIndex++){
            AccountPaymentSchedule schedule = plan.getSchedules().get(acctIndex);
            Debt debt = schedule.getAccount();

            double lastBalance = schedule.getPaymentRecords().size() > 0 ? schedule.getPaymentRecords().get(schedule.getPaymentRecords().size() - 1).getBalance() : debt.getBalance();
            double interest = lastBalance * debt.getMonthlyInterestRate();
            schedule.addInterest(interest);
            plan.addInterest(interest);

            double balance = lastBalance + interest;
            double payment = debt.getMinPayment();

            if (excess > 0){ // Can pay higher than min on at least one account
                if ((excess + debt.getMinPayment()) > balance){ // All of excess isn't needed
                    payment = balance;
                    excess = (excess + debt.getMinPayment()) - payment;
                    if (start == acctIndex) {
                        start++;
                    } // This account is paid off
                }
                else{
                    payment += excess;
                    excess = 0;
                }
            }
            else if (payment > balance){ // Last payment for account
                payment = balance;
                excess += (debt.getMinPayment() - payment); // Roll excess over to next account payment

                // If first account in list was paid off
                if (start == acctIndex) {
                    start++;
                }
                System.out.println(((Account)debt).getName() + " $" + df.format(debt.getBalance()) + " paid off!");
            }
            double principalPaid = payment - interest;
            balance -= payment;
            if (lastBalance > 0) {
                PaymentRecord record = new PaymentRecord(month, payment, balance, interest, principalPaid);
                schedule.getPaymentRecords().add(record);
            }
        }
        month++;

        excess = budget - sumMinPayments(start, plan.getSchedules().size(), plan.getSchedules());
        if (start != plan.getSchedules().size()){
            payMonth(start, plan, excess, month, budget);
        }
        else {
            System.out.println("You paid off your total balance of $" + df.format(plan.getTotalBalance()) +
                    " in " + (month + 1) + " months! You paid $" + df.format(plan.getTotalInterestPaid())
                    + " in interest :(");
        }
        return plan;
    }

    private static double sumMinPayments(List<Debt> accounts, int start, int end) {
        double sum = 0;
        for (int i = start; i < end; i++){
            sum += accounts.get(i).getMinPayment();
        }
        return sum;
    }

    private static double sumMinPayments(int start, int end, List<AccountPaymentSchedule> acctSchedules) {
        double sum = 0;
        for (int i = start; i < end; i++){
            sum += acctSchedules.get(i).getAccount().getMinPayment();
        }
        return sum;
    }

    private static double sumTotalBalance(List<Debt> accounts, int start, int end) {
        double sum = 0;
        for (int i = start; i < end; i++){
            sum += accounts.get(i).getBalance();
        }
        return sum;
    }
}
