package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {

    Wallet findByUser(User user);
}
