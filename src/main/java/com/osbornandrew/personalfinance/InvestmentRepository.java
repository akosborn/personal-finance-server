package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Investment;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface InvestmentRepository extends CrudRepository<Investment, Long> {

    Set<Investment> findAllByWallet_User(User user);
}
