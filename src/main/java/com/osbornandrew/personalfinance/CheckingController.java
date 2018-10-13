package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.CheckingAccount;
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
@RequestMapping("/api/checking")
public class CheckingController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private WalletService walletService;
    private CheckingService checkingService;
    private AccountService acctService;

    @Autowired
    public CheckingController(WalletService walletService, CheckingService checkingService,
                              AccountService acctService) {
        this.walletService = walletService;
        this.checkingService = checkingService;
        this.acctService = acctService;
    }

    @PostMapping("")
    public Set<CheckingAccount> postAccount(@RequestBody CheckingAccount account) {

        Long userId = ((MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser().getId();
        account.setWallet(walletService.loadByUserId(userId));
        CheckingAccount savedAcct = (CheckingAccount) acctService.save(account);
        log.info("Saved user (ID {}) checking account (ID {}) '{}' ", userId,
                savedAcct.getId(), savedAcct.getName());

        return checkingService.loadByUserId(userId);
    }
}
