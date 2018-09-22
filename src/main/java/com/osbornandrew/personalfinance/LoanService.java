package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.accounts.Loan;
import com.osbornandrew.personalfinance.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LoanService {

    private LoanRepository repo;

    @Autowired
    public LoanService(LoanRepository repo) {
        this.repo = repo;
    }

    public Loan save(Loan account) {
        return repo.save(account);
    }

    public Set<Loan> loadByUser(User user) {
        return repo.findAllByWallet_User(user);
    }
}
