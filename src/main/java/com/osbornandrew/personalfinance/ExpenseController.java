package com.osbornandrew.personalfinance;

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
        return null;
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Map testPostToken(@RequestBody String body,
                             @RequestHeader HttpHeaders headers) {

        return Collections.singletonMap("response", headers.get("Authorization").get(0));
    }
}
