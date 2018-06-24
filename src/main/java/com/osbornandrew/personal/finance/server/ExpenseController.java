package com.osbornandrew.personal.finance.server;

import com.google.common.collect.Sets;
import com.osbornandrew.personal.finance.server.transactions.Expense;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ExpenseController {

    @RequestMapping("")
    public Set<Expense> getExpenses() {

        return Sets.newHashSet(
                new Expense("Bell's", 19.45),
                new Expense("Taco Bell", 4.65),
                new Expense("States Golf Course", 10.75),
                new Expense("Gun Lake Casino", 23));
    }
}
