package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.users.MyUserDetails;
import com.osbornandrew.personalfinance.users.MyUserService;
import com.osbornandrew.personalfinance.users.User;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/wallet")
public class WalletController
{
    private MyUserService userService;
    private WalletService walletService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WalletController(MyUserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    @RequestMapping("")
    public Wallet getWallet() {
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Wallet wallet = user.getWallet();

        // Create new wallet for user if user doesn't have one
        if (wallet == null) {
            user.setWallet(new Wallet(user));
            wallet = userService.save(user).getWallet();
            log.info("Created wallet (ID {}) for {} user {} (ID {})", wallet.getId(), user.getProvider(),
                    user.getDisplayName(), user.getId());
        }
        else {
            log.info("Found wallet (ID {}) for {} user {} (ID {})", wallet.getId(), user.getProvider(),
                    user.getDisplayName(), user.getId());
        }
        return wallet;
    }

    @PutMapping("")
    public Wallet updateWallet(@RequestBody Wallet putWallet) {
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Wallet wallet = user.getWallet();
        wallet.setAnnualIncome(putWallet.getAnnualIncome());
        return walletService.save(wallet);
    }
}
