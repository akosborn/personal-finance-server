package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.SavingsAccount;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SavingsRepository extends CrudRepository<SavingsAccount, Long> {

    Set<SavingsAccount> findAllByWallet_User(User user);
}
