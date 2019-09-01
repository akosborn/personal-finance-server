package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.transactions.Expense;
import com.osbornandrew.personalfinance.transactions.Frequency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    Expense findByIdAndAccount_Wallet_User_Id(Long id, Long userId);

    List<Expense> findByAccount_Wallet_User_IdAndFrequencyEquals(Long userId, Frequency frequency);
}
