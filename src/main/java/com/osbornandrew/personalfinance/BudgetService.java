package com.osbornandrew.personalfinance;

import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    private BudgetRepository repo;

    public BudgetService(BudgetRepository repo) {
        this.repo = repo;
    }

    public Budget save(Budget budget) {
        return repo.save(budget);
    }

    public Budget loadByIdAndUserId(Long budgetId, Long userId) {
        return repo.findByIdAndUser_Id(budgetId, userId);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}