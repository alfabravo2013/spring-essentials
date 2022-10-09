package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.BaseballGame;
import com.github.alfabravo2013.entities.Game;
import com.github.alfabravo2013.entities.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.github.alfabravo2013")
public class AppConfig {

    @Resource
    private DataSource dataSource;

    @Autowired
    @Qualifier("redSox")
    private Team home;

    @Autowired
    @Qualifier("cubs")
    private Team away;

    @Bean
    public Game game() {
        var game = new BaseballGame(home, away);
        game.setDataSource(dataSource);
        return game;
    }
}
