package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Account;
import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.users.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private AccountService acctService;
    private ExpenseService expService;

    @Autowired
    public ExpenseController(AccountService acctService, ExpenseService expService) {
        this.acctService = acctService;
        this.expService = expService;
    }

    @RequestMapping("")
    public Set<Expense> getExpenses() {
        return null;
    }

    @RequestMapping(value = "/accounts/{acctId}", method = RequestMethod.POST)
    public Expense postExpense(@PathVariable(value = "acctId") Long acctId,
                               @RequestBody Expense expense) {

        Account account = acctService.loadByUserAndId(
                acctId,
                ((MyUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal()).getUser().getId());
        expense.setAccount(account);
        return expService.save(expense);
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Map testPostToken(@RequestBody String body,
                             @RequestHeader HttpHeaders headers) {

        return Collections.singletonMap("response", headers.get("Authorization").get(0));
    }
}
