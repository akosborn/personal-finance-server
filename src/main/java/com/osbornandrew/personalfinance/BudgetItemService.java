package com.osbornandrew.personalfinance;

import org.springframework.stereotype.Service;

@Service
public class BudgetItemService {

    private BudgetItemRepository repo;

    public BudgetItemService(BudgetItemRepository repo) {
        this.repo = repo;
    }

    public BudgetItem save(BudgetItem item) {
        return repo.save(item);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public BudgetItem findByUserAndId(Long userId, Long itemId){
        return repo.findByBudget_User_IdAndId(userId, itemId);
    }
}