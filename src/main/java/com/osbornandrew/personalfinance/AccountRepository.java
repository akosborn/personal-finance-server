package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}
