package com.osbornandrew.personalfinance;

import com.google.common.collect.Sets;
import com.osbornandrew.personalfinance.transactions.Expense;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @RequestMapping("")
    public Set<Expense> getExpenses() {

        return Sets.newHashSet(
                new Expense("Bell's", 19.45),
                new Expense("Taco Bell", 4.65),
                new Expense("States Golf Course", 10.75),
                new Expense("Gun Lake Casino", 23));
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Map testPostToken(@RequestBody String body,
                             @RequestHeader HttpHeaders headers) {

        return Collections.singletonMap("response", headers.get("Authorization").get(0));
    }
}
