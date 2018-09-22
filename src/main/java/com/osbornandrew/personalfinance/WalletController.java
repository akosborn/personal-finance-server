package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.*;
import com.osbornandrew.personalfinance.users.MyUserDetails;
import com.osbornandrew.personalfinance.users.MyUserService;
import com.osbornandrew.personalfinance.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.Set;


@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/wallet")
public class WalletController
{
    private WalletService walletService;
    private MyUserService userService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WalletController(WalletService walletService, MyUserService userService) {
        this.walletService = walletService;
        this.userService = userService;
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

//        Set<CheckingAccount> checkingAccts = new LinkedHashSet<>();
//        CheckingAccount chk = new CheckingAccount(
//                "Bank of Tuscaloosa",
//                "primary checking account",
//                1148.84);
//        CheckingAccount chk2 = new CheckingAccount(
//                "TCCU",
//                "secondary checking account",
//                6);
//        checkingAccts.add(chk);
//        checkingAccts.add(chk2);
//        Set<SavingsAccount> savingsAccts = new LinkedHashSet<>();
//        SavingsAccount svg = new SavingsAccount(
//                "Bank of Tuscaloosa",
//                "primary savings account",
//                345.53,
//                1.4f);
//        savingsAccts.add(svg);
//        Set<Investment> investments = new LinkedHashSet<>();
//        Investment inv = new Investment(
//                "Robinhood",
//                "stocks account",
//                134.32);
//        investments.add(inv);
//        Set<Loan> loans = new LinkedHashSet<>();
//        Loan loan = new Loan(
//                "Discover",
//                "Student loan",
//                4587.23,
//                7.4f,
//                50d);
//        loans.add(loan);
//        Set<CreditCard> creditCards = new LinkedHashSet<>();
//        CreditCard cc = new CreditCard(
//                "Chase",
//                "Amazon rewards",
//                200,
//                4000,
//                25,
//                18.49f);
//        creditCards.add(cc);
//        user.setWallet(new Wallet(
//                "Primary",
//                "My primary wallet",
//                checkingAccts, savingsAccts, loans,
//                creditCards, investments));
    }
}
