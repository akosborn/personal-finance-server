package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Account;
import com.osbornandrew.personalfinance.accounts.CheckingAccount;
import com.osbornandrew.personalfinance.users.MyUserDetails;
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
@RequestMapping("/api")
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private WalletService walletService;
    private AccountService acctService;

    @Autowired
    public AccountController(WalletService walletService,
                             AccountService acctService) {
        this.walletService = walletService;
        this.acctService = acctService;
    }

    @PostMapping("/accounts")
    public Account postAccount(@RequestBody Account account) {

        Long userId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser().getId();
        account.setWallet(walletService.loadByUserId(userId));
        Account savedAcct = acctService.save(account);
        log.info("Saved user {) account {}: '{}' ", userId, savedAcct.getId(), savedAcct.getName());

        return savedAcct;
    }
}
