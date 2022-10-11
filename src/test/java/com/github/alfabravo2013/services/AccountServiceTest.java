package com.github.alfabravo2013.services;

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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    private AccountService service;

    @Test
    void getBalance() {
        var balance = service.getBalance(1L);
        assertEquals(
                balance.setScale(2, RoundingMode.HALF_UP),
                new BigDecimal("100.00").setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void deposit() {
        var start = service.getBalance(1L);
        var amount = new BigDecimal("50.00");
        service.deposit(1L, amount);
        var finish = service.getBalance(1L);

        assertEquals(
                finish.setScale(2, RoundingMode.HALF_UP),
                start.add(amount).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void withdraw() {
        var start = service.getBalance(1L);
        var amount = new BigDecimal("50.00");
        service.withdraw(1L, amount);
        var finish = service.getBalance(1L);

        assertEquals(
                finish.setScale(2, RoundingMode.HALF_UP),
                start.subtract(amount).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void transfer() {
        var start1 = service.getBalance(1L);
        var start2 = service.getBalance(2L);
        var amount = new BigDecimal("55.00");

        service.transfer(1L, 2L, amount);

        var finish1 = service.getBalance(1L);
        var finish2 = service.getBalance(2L);

        assertEquals(
                finish1.setScale(2, RoundingMode.HALF_UP),
                start1.subtract(amount).setScale(2, RoundingMode.HALF_UP));

        assertEquals(
                finish2.setScale(2, RoundingMode.HALF_UP),
                start2.add(amount).setScale(2, RoundingMode.HALF_UP));
    }
}
