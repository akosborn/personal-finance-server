package com.osbornandrew.personalfinance.util;

import com.osbornandrew.personalfinance.accounts.Account;
import com.osbornandrew.personalfinance.accounts.CreditCard;
import com.osbornandrew.personalfinance.accounts.Loan;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RepaymentCalculator {

    private static DecimalFormat df = new DecimalFormat("#.00");
    private Map<Debt, List<PaymentRecord>> plan = new LinkedHashMap<>();
    private double totalInterestPaid = 0;
    private double totalBalance = 0;

    @Getter @Setter
    private double budget;

    public static void main(String[] args) {
        Loan personal = new Loan("Discover", "personal", 6000, 184, 11f, 1);
        CreditCard cc = new CreditCard("Citi", "meh", 900, 5000, 25, 19.5f, 1);
        List<Debt> accounts = new ArrayList<>();
        accounts.add(personal);
        accounts.add(cc);

        RepaymentCalculator calculator = new RepaymentCalculator(500);
        if (calculator.getBudget() == 0){
            for (Debt debt : accounts) {
                calculator.setBudget(calculator.getBudget() + debt.getMinPayment());
            }
        }
        Map<Debt, List<PaymentRecord>> avalanche = calculator.buildAvalanchePlan(accounts);

        accounts = new ArrayList<>();
        personal = new Loan("Discover", "personal", 6000, 184, 11f, 1);
        cc = new CreditCard("Citi", "meh", 900, 5000, 25, 19.5f, 1);
        accounts.add(personal);
        accounts.add(cc);
        calculator = new RepaymentCalculator(500);
        Map<Debt, List<PaymentRecord>> snowball = calculator.buildSnowballPlan(accounts);
    }

    public RepaymentCalculator(double budget) {
        this.budget = budget;
    }

    public Map<Debt, List<PaymentRecord>> buildSnowballPlan(List<Debt> accounts){
        // Sort by highest balance
        accounts.sort((o1, o2) -> Double.compare(o2.getBalance(), o1.getBalance()));
        setupPaymentPlan(accounts);
        return plan;
    }

    public Map<Debt, List<PaymentRecord>> buildAvalanchePlan(List<Debt> accounts){
        // Sort by highest interest rate
        accounts.sort((o1, o2) -> Float.compare(o2.getInterestRate(), o1.getInterestRate()));
        setupPaymentPlan(accounts);
        return plan;
    }

    private void setupPaymentPlan(List<Debt> accounts){
        for (Debt debt : accounts){
            totalBalance += debt.getBalance();
            plan.put(debt, new ArrayList<>());
        }

        double excess = budget - sumMinPayments(accounts, 0, accounts.size());
        payMonth(0, accounts, excess, 0);
    }

    public void payMonth(int start, List<Debt> accounts, double excess, int month){
        for (int acctIndex = start; acctIndex < accounts.size(); acctIndex++){
            Debt debt = accounts.get(acctIndex);
            float monthlyRate = (debt.getInterestRate() / 12) / 100;
            double interest = debt.getBalance() * monthlyRate;
            totalInterestPaid += interest;
            double balance = debt.getBalance() + interest;
            double payment = debt.getMinPayment();

            if (excess > 0){ // Can pay higher than min on at least one account
                if ((excess + debt.getMinPayment()) > balance){ // All of excess isn't needed
                    payment = balance;
                    excess = (excess + debt.getMinPayment()) - payment;
                    start++; // This account is paid off
                }
                else{
                    payment += excess;
                    excess = 0;
                }
            }
            else if (payment > balance){ // Last payment for account
                payment = balance;
                excess += (debt.getMinPayment() - payment); // Roll excess over to next account payment
                start++; // This account is paid off
            }
            double principalPaid = payment - interest;
            balance -= payment;
            ((Account) debt).setBalance(balance);
            PaymentRecord record = new PaymentRecord(month, payment, balance, interest, principalPaid);
            plan.get(debt).add(record);
//            System.out.println(record.toString());
        }
        month++;

//        System.out.println();

        excess = budget - sumMinPayments(accounts, start, accounts.size());
        if (start != accounts.size()){
            payMonth(start, accounts, excess, month);
        }
        else {
            System.out.println("You paid off your total balance of $" + df.format(totalBalance) + " in " + month + 1 + " months! You paid $" + df.format(totalInterestPaid)
                    + " in interest :(");
        }
    }

    private static double sumMinPayments(List<Debt> accounts, int start, int end) {
        double sum = 0;
        for (int i = start; i < end; i++){
            sum += accounts.get(i).getMinPayment();
        }
        return sum;
    }

    public static void calculatePaymentDuration(Debt debt, double payment){
        final float monthlyRate = (debt.getInterestRate() / 12) / 100;
        double balanceRemaining = debt.getBalance();
        double totalInterest = 0;
        if (payment == 0){
            payment = debt.getMinPayment();
        }

        int month = 0;
        while (balanceRemaining >= payment){ // One iteration per month
            double interest = balanceRemaining * monthlyRate;
            totalInterest += interest;

            balanceRemaining += interest;
            balanceRemaining -= payment;

            double principalPaid = payment - interest;

//            System.out.println("Month " + month);
//            System.out.println("balanceRemaining = [$" + df.format(balanceRemaining) +
//                    "], payment = [$" + df.format(payment) + "], interestForMonth = [$" + df.format(interest) + "], principalPaid = [$" +
//                    df.format(principalPaid) + "]");
//            System.out.println();

            month++;
        }

        while (balanceRemaining > 0){
            double interest = balanceRemaining * monthlyRate;
            double finalPayment = 0;
            totalInterest += interest;

            balanceRemaining += interest;
            if (balanceRemaining > payment){ // Addition of the monthly interest could cause this to be true
                balanceRemaining -= payment;
            }
            else {
                finalPayment = balanceRemaining;
                balanceRemaining = 0;
            }

//            System.out.println("Month " + month);
//            System.out.println("balanceRemaining = [$" + df.format(balanceRemaining) +
//                    "], payment = [$" + df.format(finalPayment) + "], interestForMonth = [$" + df.format(interest) + "]");
//            System.out.println();

            month++;
        }

        System.out.println(((Account)debt).getName() + ": At $" + df.format(payment) + "/mo, it will take " + month + 1 + " months to pay off the initial balance " +
                "of $" + df.format(debt.getBalance()) + ". Total interest paid will be $" +  df.format(totalInterest) + ".");
    }
}
