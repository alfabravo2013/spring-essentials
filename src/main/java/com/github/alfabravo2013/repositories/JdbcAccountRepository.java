package com.github.alfabravo2013.repositories;

import com.github.alfabravo2013.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@Repository
@Profile("test")
public class JdbcAccountRepository implements AccountRepository {
    private final JdbcTemplate template;

    private final RowMapper<Account> accountMapper = (rs, rowNum) ->
            new Account(rs.getLong("id"), rs.getBigDecimal("balance"));

    private static long nextId = 4;

    @Autowired
    public JdbcAccountRepository(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Account> findAll() {
        var sqlText = "SELECT * FROM account";
        return template.query(sqlText, accountMapper);
    }

    @Override
    public Account findById(Long id) {
        var sqlTxt = "SELECT * FROM account WHERE id=?";
        return template.queryForObject(sqlTxt, accountMapper, id);
    }

    @Override
    public long count() {
        var sqlTxt = "SELECT COUNT(*) FROM account";
        var result = template.queryForObject(sqlTxt, Long.class);
        return result == null ? 0L : result;
    }

    @Override
    public Long create(BigDecimal balance) {
        var id = nextId++;
        var sqlTxt = "INSERT INTO account (id, balance) VALUES(?, ?)";
        var updateCount = template.update(sqlTxt, id, balance);
        return updateCount == 0 ? null : id;
    }

    @Override
    public int deleteById(Long id) {
        var sqlTxt = "DELETE FROM account WHERE id=?";
        return template.update(sqlTxt, id);
    }

    @Override
    public void update(Account account) {
        var sqlTxt = "UPDATE account SET balance=? WHERE id=?";
        template.update(sqlTxt, account.getBalance(), account.getId());
    }
}
