package com.osbornandrew.personalfinance;

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

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping("")
    public Budget postBudget(@RequestBody Budget budget) {
        User user = ((MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        budget.setUser(user);
        Budget savedBudget = budgetService.save(budget);
        log.info("Saved user (ID {}) budget (ID {})", user.getId(), savedBudget.getId());
        return savedBudget;
    }
}
