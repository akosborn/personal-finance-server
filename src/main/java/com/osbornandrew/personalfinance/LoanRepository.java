package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Loan;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {

    Set<Loan> findAllByWallet_User(User user);
}
