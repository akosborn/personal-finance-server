package com.osbornandrew.personalfinance.finance.server;

import com.osbornandrew.personalfinance.accounts.CreditCard;
import com.osbornandrew.personalfinance.accounts.Debt;
import com.osbornandrew.personalfinance.accounts.Loan;
import com.osbornandrew.personalfinance.repayment.FinanceUtils;
import com.osbornandrew.personalfinance.repayment.PaymentPlan;
import com.osbornandrew.personalfinance.repayment.RepaymentCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles(profiles = "dev")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void numberOfPayments() {
		final DecimalFormat df = new DecimalFormat("#.##");
		assert Double.valueOf(df.format(FinanceUtils.numberOfPayments(3500, 0.005, 100))).equals(38.57);

	}

	@Test
	public void payoffAmount() {
		final DecimalFormat df = new DecimalFormat("#.##");
		assert Double.valueOf(df.format(FinanceUtils.payoffAmount(18000, 0.011875, 24, 617.39))).equals(6866.97);
	}

	@Test
	public void avalanchePan() {
		Loan personal = new Loan("Discover", "personal", 6000, 184, 11f, 1);
        CreditCard cc = new CreditCard("Citi", "meh", 900, 5000, 25, 19.5f, 1);
        CreditCard b = new CreditCard("Chase", "meh", 40, 5000, 25, 19.5f, 1);
        List<Debt> accounts = new ArrayList<>();
        accounts.add(personal);
        accounts.add(cc);
        accounts.add(b);
        RepaymentCalculator.buildAvalanchePlan(accounts, 300);
	}
}
