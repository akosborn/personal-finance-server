package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.SavingsAccount;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SavingsService {

    private SavingsRepository repo;

    @Autowired
    public SavingsService(SavingsRepository repo) {
        this.repo = repo;
    }

    public SavingsAccount save(SavingsAccount account) {
        return repo.save(account);
    }

    public Set<SavingsAccount> loadByUser(User user) {
        return repo.findAllByWallet_User(user);
    }
}
