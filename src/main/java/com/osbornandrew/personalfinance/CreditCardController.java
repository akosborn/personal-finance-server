package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.CreditCard;
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
@RequestMapping("/api/credit")
public class CreditCardController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private WalletService walletService;
    private CreditCardService creditCardService;

    @Autowired
    public CreditCardController(WalletService walletService, CreditCardService creditCardService) {
        this.walletService = walletService;
        this.creditCardService = creditCardService;
    }

    @PostMapping("")
    public Set<CreditCard> postAccount(@RequestBody CreditCard account) {

        User user = ((MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        Wallet wallet = walletService.loadByUser(user);
        account.setWallet(wallet);
        CreditCard savedAcct = creditCardService.save(account);
        log.info("Saved user (ID {}) credit card (ID {}) '{}' ", user.getId(),
                savedAcct.getId(), savedAcct.getName());

        return creditCardService.loadByUser(user);
    }
}
