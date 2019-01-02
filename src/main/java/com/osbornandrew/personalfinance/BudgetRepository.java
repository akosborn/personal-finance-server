package com.osbornandrew.personalfinance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends CrudRepository<Budget, Long> {

    Budget findByIdAndUser_Id(Long id, Long userId);
}
