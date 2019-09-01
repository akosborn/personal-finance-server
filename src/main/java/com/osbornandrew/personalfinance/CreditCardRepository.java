package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.CreditCard;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {

    Set<CreditCard> findAllByWallet_User(User user);
}
