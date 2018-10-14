package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.CheckingAccount;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CheckingService {

    private CheckingRepository repo;

    @Autowired
    public CheckingService(CheckingRepository repo) {
        this.repo = repo;
    }

    public CheckingAccount save(CheckingAccount account) {
        return repo.save(account);
    }

    public Set<CheckingAccount> loadByUserId(Long id) {
        return repo.findAllByWallet_User_Id(id);
    }
}
