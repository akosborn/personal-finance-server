package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.transactions.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
}
