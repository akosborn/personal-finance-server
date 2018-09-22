package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Investment;
import com.osbornandrew.personalfinance.accounts.Loan;
import com.osbornandrew.personalfinance.users.MyUserDetails;
import com.osbornandrew.personalfinance.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/investments")
public class InvestmentController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private WalletService walletService;
    private InvestmentService investmentService;

    @Autowired
    public InvestmentController(WalletService walletService, InvestmentService investmentService) {
        this.walletService = walletService;
        this.investmentService = investmentService;
    }

    @PostMapping("")
    public Set<Investment> postAccount(@RequestBody Investment account) {

        User user = ((MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        Wallet wallet = walletService.loadByUser(user);
        account.setWallet(wallet);
        Investment savedAcct = investmentService.save(account);
        log.info("Saved user (ID {}) investment (ID {}) '{}' ", user.getId(),
                savedAcct.getId(), savedAcct.getName());

        return investmentService.loadByUser(user);
    }
}
