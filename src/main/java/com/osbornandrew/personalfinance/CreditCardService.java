package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.CreditCard;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CreditCardService {

    private CreditCardRepository repo;

    @Autowired
    public CreditCardService(CreditCardRepository repo) {
        this.repo = repo;
    }

    public CreditCard save(CreditCard account) {
        return repo.save(account);
    }

    public Set<CreditCard> loadByUser(User user) {
        return repo.findAllByWallet_User(user);
    }
}
