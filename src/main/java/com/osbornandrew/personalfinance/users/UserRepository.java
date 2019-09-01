package com.osbornandrew.personalfinance.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // Uses both params to avoid the small chance of two providers having same id for different users
    Optional<User> findBySocialIdAndProvider(String socialId, SocialProvider provider);
}
