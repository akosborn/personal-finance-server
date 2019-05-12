package com.osbornandrew.personalfinance.repayment;

public class FinanceUtils {
    /**
     * @return The number of payments on a debt
     *
     * http://financeformulas.net/Number-of-Periods-of-Annuity-from-Present-Value.html#calcHeader
     * */
    public static double numberOfPayments(double presentValue, double rate, double payment) {
        return Math.log(Math.pow(1 - (presentValue * rate) / payment, (-1))) / Math.log(1 + rate);
    }

    /**
     * @return The balance remaining after n payment periods.
     *
     * https://brownmath.com/bsci/loan.htm#Sample1
     * */
    public static double payoffAmount(double originalBalance, double rate, int paymentsMade, double payment) {
        return (originalBalance * Math.pow(1 + rate, paymentsMade) - ((payment / rate) * (Math.pow(1 + rate, paymentsMade) - 1)));
    }
}
