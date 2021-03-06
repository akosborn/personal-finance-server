package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.*;
import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.transactions.Frequency;
import com.osbornandrew.personalfinance.users.MyUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private WalletService walletService;
    private AccountService acctService;
    private ExpenseService expService;
    private BudgetService budgetService;
    private CategoryService categoryService;
    private BudgetItemService budgetItemService;

    @Autowired
    public AccountController(WalletService walletService,
                             AccountService acctService,
                             ExpenseService expService,
                             BudgetService budgetService,
                             CategoryService categoryService,
                             BudgetItemService budgetItemService) {
        this.walletService = walletService;
        this.acctService = acctService;
        this.expService = expService;
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.budgetItemService = budgetItemService;
    }

    @PostMapping("")
    public Account postAccount(@RequestBody Account account) {
        Long userId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser().getId();
        account.setWallet(walletService.loadByUserId(userId));
        Account savedAcct = acctService.save(account);
        log.info("Saved User {} Account {}: '{}' ", userId, savedAcct.getId(), savedAcct.getName());
        if (savedAcct.getType() == AccountType.CREDIT_CARD || savedAcct.getType() == AccountType.LOAN){
            // Create an associated fixed monthly expense
            Budget budget = account.getWallet().getUser().getBudget();
            CheckingAccount payAccount = (CheckingAccount) acctService.loadByIdAndUserId(3L, userId); // TODO: 1/14/2019 Handle this appropriately
            BudgetItem budgetItem;
            Category category = categoryService.findByName("Debt Repayment");
            if (savedAcct.getType() == AccountType.CREDIT_CARD){
                CreditCard cc = (CreditCard) savedAcct;
                budgetItem = budgetItemService.save(new BudgetItem(category, cc.getName() + " Credit Card",
                        cc.getMinPayment(), budget));
            }
            else {
                Loan loan = (Loan) savedAcct;
                budgetItem = budgetItemService.save(new BudgetItem(category,
                        loan.getName() + " Loan", loan.getMinPayment(), budget));
            }
            budget.getItems().add(budgetItem);
            budgetService.save(budget);
        }
        return savedAcct;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") Long acctId) {
        Long userId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser().getId();
        ResponseEntity response;
        // Ensure that account exists as one of the user's
        if (acctService.loadByIdAndUserId(acctId, userId) != null) {
            acctService.deleteById(acctId);
            log.info("Account {} for User {} successfully deleted.", acctId, userId);
            response = ResponseEntity.ok(
                    Collections.singletonMap("message", "Account " + acctId + " was successfully deleted"));
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "Account " + acctId + " not found."));
            log.info("Account {} for User {} could not be deleted. Not found.", acctId, userId);
        }
        return response;
    }
}
