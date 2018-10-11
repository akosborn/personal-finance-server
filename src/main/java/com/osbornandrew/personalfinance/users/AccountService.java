package com.osbornandrew.personalfinance.users;

import com.osbornandrew.personalfinance.AccountRepository;
import com.osbornandrew.personalfinance.accounts.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Account save(Account account) {
        return repo.save(account);
    }
}