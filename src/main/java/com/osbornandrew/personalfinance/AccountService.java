package com.osbornandrew.personalfinance;

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

    public Account loadByIdAndUserId(Long acctId, Long userId) {
        return repo.findByIdAndWallet_User_Id(acctId, userId);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}