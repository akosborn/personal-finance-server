package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Investment;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InvestmentService {

    private InvestmentRepository repo;

    @Autowired
    public InvestmentService(InvestmentRepository repo) {
        this.repo = repo;
    }

    public Investment save(Investment account) {
        return repo.save(account);
    }

    public Set<Investment> loadByUser(User user) {
        return repo.findAllByWallet_User(user);
    }
}
