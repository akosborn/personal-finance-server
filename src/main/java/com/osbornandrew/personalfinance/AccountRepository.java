package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Account;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByIdAndWallet_User_Id(Long id, Long userId);
}
