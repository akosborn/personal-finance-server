package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Account;
import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.users.MyUserDetails;
import com.osbornandrew.personalfinance.users.MyUserService;
import com.osbornandrew.personalfinance.users.User;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private BudgetService budgetService;
    private BudgetItemService budgetItemService;
    private MyUserService userService;
    private CategoryService categoryService;
    private ExpenseService expService;
    private AccountService accountService;

    @Autowired
    public BudgetController(BudgetService budgetService,
                            MyUserService userService,
                            BudgetItemService budgetItemService,
                            CategoryService categoryService,
                            ExpenseService expService,
                            AccountService accountService) {
        this.budgetService = budgetService;
        this.userService = userService;
        this.budgetItemService = budgetItemService;
        this.categoryService = categoryService;
        this.expService = expService;
        this.accountService = accountService;
    }

    @RequestMapping("")
    public Budget getBudget(){
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Budget budget;
        // Create new budget for user if user doesn't have one
        if (user.getBudget() == null) {
            user.setBudget(new Budget(user));
            budget = userService.save(user).getBudget();
            log.info("Created budget (ID {}) for {} user {} (ID {})", budget.getId(), user.getProvider(),
                    user.getDisplayName(), user.getId());
        }
        else {
            // Lazy initialization error if user::getBudget is used
            budget = budgetService.loadByIdAndUserId(user.getBudget().getId(), user.getId());
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

    @DeleteMapping("/{budgetId}/items/{itemId}")
    public ResponseEntity deleteBudgetItem(@PathVariable("budgetId") Long budgetId,
                                           @PathVariable("itemId") Long itemId) {
        // budgetId isn't currently used since itemId is guaranteed to be unique. Using for client clarity.
        Long userId = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser().getId();
        ResponseEntity response;
        // Ensure that item exists as one of the user's
        if (budgetItemService.findByUserAndId(userId, itemId) != null) {
            budgetItemService.deleteById(itemId);
            log.info("BudgetItem {} for User {} successfully deleted.", itemId, userId);
            response = ResponseEntity.ok(
                    Collections.singletonMap("message", "BudgetItem " + itemId + " was successfully deleted"));
        }
        else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("message", "BudgetItem " + itemId + " not found."));
            log.info("BudgetItem {} for User {} could not be deleted. Not found.", itemId, userId);
        }

        return response;
    }

    @PatchMapping("/{budgetId}/items/{itemId}")
    public BudgetItem patchBudgetItem(@PathVariable("budgetId") Long budgetId,
                                      @PathVariable("itemId") Long itemId,
                                      @RequestBody Map<Object, Object> partialItem) {
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        BudgetItem item = budgetItemService.findByUserAndId(user.getId(), itemId);
        // Use reflection to update only the field(s) in the request body
        partialItem.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(BudgetItem.class, k.toString());
            assert field != null;
            field.setAccessible(true);
            ReflectionUtils.setField(field, item, v);
        });
        return budgetItemService.save(item);
    }
}
