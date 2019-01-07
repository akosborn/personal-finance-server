package com.osbornandrew.personalfinance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetItemRepository extends CrudRepository<BudgetItem, Long> {
}
