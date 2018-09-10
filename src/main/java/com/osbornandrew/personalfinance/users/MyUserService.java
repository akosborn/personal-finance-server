package com.osbornandrew.personalfinance.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserService {

    private UserRepository repo;

    @Autowired
    public MyUserService(UserRepository repo) {
        this.repo = repo;
    }

    public Optional<User> loadById(Long id) {
        return repo.findById(id);
    }

    public Optional<User> loadBySocialIdAndProvider(String socialId, SocialProvider provider) {
        return repo.findBySocialIdAndProvider(socialId, provider);
    }

    public User save(User user) {
        return repo.save(user);
    }
}
