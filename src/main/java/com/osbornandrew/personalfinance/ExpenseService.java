package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.transactions.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private ExpenseRepository repo;

    @Autowired
    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public Expense save(Expense expense) {
        return repo.save(expense);
    }

    public Expense loadByIdAndUserId(Long acctId, Long userId) {
        return repo.findByIdAndAccount_Wallet_User_Id(acctId, userId);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public List<Expense> loadFixedByUserIdAndFrequency(Long userId, Frequency frequency){
        return repo.findByAccount_Wallet_User_IdAndFrequencyEquals(userId, frequency);
    }
}
