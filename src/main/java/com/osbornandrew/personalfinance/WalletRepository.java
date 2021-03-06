package com.osbornandrew.personalfinance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Long> {

    Wallet findByUser_Id(Long id);
}
