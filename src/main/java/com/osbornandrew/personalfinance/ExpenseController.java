package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Account;
import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.users.MyUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AccountService acctService;
    private ExpenseService expService;

    @Autowired
    public ExpenseController(AccountService acctService, ExpenseService expService) {
        this.acctService = acctService;
        this.expService = expService;
    }

    @PostMapping("/accounts/{acctId}/expenses")
    public Expense postExpense(@PathVariable(value = "acctId") Long acctId,
                               @RequestBody Expense expense) {
        Long userId = ((MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser().getId();
        Account account = acctService.loadByIdAndUserId(
                acctId, userId);
        // TODO: 10/14/2018 Handle not finding account
        expense.setAccount(account);
        Expense savedExp = expService.save(expense);
        log.info("Saved Expense {} for User {}", savedExp.getId(), userId);

        return savedExp;
    }

    @DeleteMapping("accounts/{acctId}/expenses/{expId}")
    public ResponseEntity deleteExpense(@PathVariable("acctId") Long acctId,
                                        @PathVariable("expId") Long expId) {
        Long userId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser().getId();
        ResponseEntity response;
        // Ensure that expense exists as one of the user's
        if (expService.loadByIdAndUserId(expId, userId) != null) {
            expService.deleteById(expId);
            log.info("Expense {} for User {} successfully deleted.", expId, userId);
            response = ResponseEntity.ok(
                    Collections.singletonMap("message", "Expense  " + expId + " was successfully deleted"));
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "Expense " + expId + " not found."));
            log.info("Expense {} for User {} could not be deleted. Not found.", expId, userId);
        }
        return response;
    }
}
