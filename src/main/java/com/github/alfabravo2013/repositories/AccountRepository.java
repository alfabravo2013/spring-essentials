package com.github.alfabravo2013.repositories;

import com.github.alfabravo2013.entities.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    Account findById(Long id);

    long count();

    Long create(BigDecimal balance);

    int deleteById(Long id);

    void update(Account account);
}
