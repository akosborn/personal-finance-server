package com.osbornandrew.personal.finance.server;

import com.google.common.collect.Sets;
import com.osbornandrew.personal.finance.server.accounts.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = {"http://localhost:4200"})
public class WalletController
{
    @RequestMapping("")
    public Wallet getWallet() {
        CheckingAccount chk = new CheckingAccount(
                "Bank of Tuscaloosa",
                "primary checking account",
                1148.84);
        SavingsAccount svg = new SavingsAccount(
                "Bank of Tuscaloosa",
                "primary savings account",
                345.53,
                1.4f);
        Investment inv = new Investment(
                "Robinhood",
                "stocks account",
                134.32);
        Loan loan = new Loan(
                "Discover",
                "Student loan",
                4587.23,
                7.4f,
                50d);
        CreditCard cc = new CreditCard(
                "Chase",
                "Amazon rewards",
                200,
                4000,
                25,
                1.5f);
        return new Wallet(
                "Primary",
                "My primary wallet",
                Sets.newHashSet(chk), Sets.newHashSet(svg), Sets.newHashSet(loan),
                Sets.newHashSet(cc), Sets.newHashSet(inv));
    }
}
