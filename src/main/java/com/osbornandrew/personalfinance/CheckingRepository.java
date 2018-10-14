package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.CheckingAccount;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CheckingRepository extends CrudRepository<CheckingAccount, Long> {

    Set<CheckingAccount> findAllByWallet_User_Id(Long id);
}
