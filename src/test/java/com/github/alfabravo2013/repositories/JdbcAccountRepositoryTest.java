package com.github.alfabravo2013.repositories;

import com.github.alfabravo2013.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
@ActiveProfiles("test")
class JdbcAccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    @Test
    void findAll() {
        var accounts = repository.findAll();
        assertEquals(3, accounts.size());
    }

    @Test
    void findById() {
        var account = repository.findById(1L);
        assertEquals(1L, account.getId());
        assertEquals(new BigDecimal("100.00"), account.getBalance().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void count() {
        var count = repository.count();
        assertEquals(3, count);
    }

    @Test
    void create() {
        var id = repository.create(new BigDecimal("235.00"));
        var account = repository.findById(id);
        assertTrue(id > 3);
        assertNotNull(account);
        assertEquals(new BigDecimal("235.00"), account.getBalance().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void deleteById() {
        for (var account : repository.findAll()) {
            repository.deleteById(account.getId());
        }

        assertEquals(0, repository.findAll().size());
    }

    @Test
    void update() {
        var account = repository.findById(1L);
        account.setBalance(new BigDecimal("10.00"));
        repository.update(account);
        var updated = repository.findById(1L);

        assertEquals(
                account.getBalance().setScale(2, RoundingMode.HALF_UP),
                updated.getBalance().setScale(2, RoundingMode.HALF_UP));
    }
}
