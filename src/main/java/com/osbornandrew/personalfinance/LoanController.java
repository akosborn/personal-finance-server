package com.osbornandrew.personalfinance;

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
@RequestMapping("/api/loans")
public class LoanController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private WalletService walletService;
    private LoanService loanService;

    @Autowired
    public LoanController(WalletService walletService, LoanService loanService) {
        this.walletService = walletService;
        this.loanService = loanService;
    }

    @PostMapping("")
    public Set<Loan> postAccount(@RequestBody Loan account) {

        User user = ((MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        Wallet wallet = walletService.loadByUserId(user.getId());
        account.setWallet(wallet);
        Loan savedAcct = loanService.save(account);
        log.info("Saved user (ID {}) loan (ID {}) '{}' ", user.getId(),
                savedAcct.getId(), savedAcct.getName());

        return loanService.loadByUser(user);
    }
}
