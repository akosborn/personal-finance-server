package com.osbornandrew.personalfinance;

import com.osbornandrew.personalfinance.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {

    private WalletRepository repo;

    @Autowired
    public WalletService(WalletRepository repo) {
        this.repo = repo;
    }

    public Optional<Wallet> loadById(Long id) {
        return repo.findById(id);
    }

    public Wallet loadByUser(User user) {
        return repo.findByUser(user);
    }

    public Wallet save(Wallet wallet) {
        return repo.save(wallet);
    }
}
