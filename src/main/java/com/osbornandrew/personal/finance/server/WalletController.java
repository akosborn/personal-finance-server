package com.osbornandrew.personal.finance.server;

import com.osbornandrew.personal.finance.server.accounts.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.Set;


@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/wallet")
public class WalletController
{
    @RequestMapping("")
    public Wallet getWallet() {
        Set<CheckingAccount> checkingAccts = new LinkedHashSet<>();
        CheckingAccount chk = new CheckingAccount(
                "Bank of Tuscaloosa",
                "primary checking account",
                1148.84);
        CheckingAccount chk2 = new CheckingAccount(
                "TCCU",
                "secondary checking account",
                6);
        checkingAccts.add(chk);
        checkingAccts.add(chk2);
        Set<SavingsAccount> savingsAccts = new LinkedHashSet<>();
        SavingsAccount svg = new SavingsAccount(
                "Bank of Tuscaloosa",
                "primary savings account",
                345.53,
                1.4f);
        savingsAccts.add(svg);
        Set<Investment> investments = new LinkedHashSet<>();
        Investment inv = new Investment(
                "Robinhood",
                "stocks account",
                134.32);
        investments.add(inv);
        Set<Loan> loans = new LinkedHashSet<>();
        Loan loan = new Loan(
                "Discover",
                "Student loan",
                4587.23,
                7.4f,
                50d);
        loans.add(loan);
        Set<CreditCard> creditCards = new LinkedHashSet<>();
        CreditCard cc = new CreditCard(
                "Chase",
                "Amazon rewards",
                200,
                4000,
                25,
                18.49f);
        creditCards.add(cc);
        return new Wallet(
                "Primary",
                "My primary wallet",
                checkingAccts, savingsAccts, loans,
                creditCards, investments);
    }
}
