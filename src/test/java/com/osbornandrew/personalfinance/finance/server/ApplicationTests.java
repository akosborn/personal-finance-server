package com.osbornandrew.personalfinance.finance.server;

import com.osbornandrew.personalfinance.repayment.FinanceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;

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
}
