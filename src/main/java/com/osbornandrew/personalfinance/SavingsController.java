package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.CheckingAccount;
import com.osbornandrew.personalfinance.accounts.SavingsAccount;
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
@RequestMapping("/api/savings")
public class SavingsController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private WalletService walletService;
    private SavingsService savingsService;

    @Autowired
    public SavingsController(WalletService walletService, SavingsService savingsService) {
        this.walletService = walletService;
        this.savingsService = savingsService;
    }

    @PostMapping("")
    public Set<SavingsAccount> postAccount(@RequestBody SavingsAccount account) {

        User user = ((MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        Wallet wallet = walletService.loadByUser(user);
        account.setWallet(wallet);
        SavingsAccount savedAcct = savingsService.save(account);
        log.info("Saved user (ID {}) savings account (ID {}) '{}' ", user.getId(),
                savedAcct.getId(), savedAcct.getName());

        return savingsService.loadByUser(user);
    }
}
