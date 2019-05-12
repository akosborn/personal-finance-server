package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Debt;
import com.osbornandrew.personalfinance.repayment.PaymentPlan;
import com.osbornandrew.personalfinance.users.MyUserDetails;
import com.osbornandrew.personalfinance.users.User;
import com.osbornandrew.personalfinance.repayment.RepaymentCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/debt-repayment")
public class DebtRepaymentController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private CreditCardService ccService;
    private LoanService loanService;

    @Autowired
    public DebtRepaymentController(CreditCardService ccService,
                                   LoanService loanService) {
        this.ccService = ccService;
        this.loanService = loanService;
    }

    @RequestMapping("")
    public PaymentPlan getPaymentPlan(){
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Debt> debts = new ArrayList<>();
        debts.addAll(ccService.loadByUser(user));
        debts.addAll(loanService.loadByUser(user));
        return RepaymentCalculator.buildAvalanchePlan(debts, 520);
    }
}
