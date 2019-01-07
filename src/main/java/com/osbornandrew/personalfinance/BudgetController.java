package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.users.MyUserDetails;
import com.osbornandrew.personalfinance.users.MyUserService;
import com.osbornandrew.personalfinance.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BudgetService budgetService;
    private BudgetItemService budgetItemService;
    private MyUserService userService;
    private CategoryService categoryService;

    @Autowired
    public BudgetController(BudgetService budgetService,
                            MyUserService userService,
                            BudgetItemService budgetItemService,
                            CategoryService categoryService) {
        this.budgetService = budgetService;
        this.userService = userService;
        this.budgetItemService = budgetItemService;
        this.categoryService = categoryService;
    }

    @RequestMapping("")
    public Budget getBudget(){
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Budget budget = user.getBudget();
        // Create new budget for user if user doesn't have one
        if (budget == null) {
            user.setBudget(new Budget(user));
            budget = userService.save(user).getBudget();
            log.info("Created budget (ID {}) for {} user {} (ID {})", budget.getId(), user.getProvider(),
                    user.getDisplayName(), user.getId());
        }
        else {
            log.info("Found budget (ID {}) for {} user {} (ID {})", budget.getId(), user.getProvider(),
                    user.getDisplayName(), user.getId());
        }
        return budget;
    }

    @PostMapping("/{budgetId}")
    public BudgetItem postBudgetItem(@PathVariable("budgetId") Long budgetId,
                                 @RequestBody BudgetItem item) {
        User user = ((MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUser();
        Budget budget = budgetService.loadByIdAndUserId(budgetId, user.getId());
        item.setBudget(budget);
        Category category = categoryService.findByName(item.getCategory().getName());
        if (category == null){
            category = categoryService.save(item.getCategory());
        }
        item.setCategory(category);
        // TODO: 1/2/2019 Need to a) Create a category service or b) Set cascade type to CascadeType.ALL for BudgetItem
        BudgetItem savedItem = budgetItemService.save(item);
        log.info("Saved user (ID {}) budget item (ID {})", user.getId(), savedItem.getId());
        return savedItem;
    }
}
