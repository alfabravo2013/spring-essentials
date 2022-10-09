package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.BaseballGame;
import com.github.alfabravo2013.entities.Game;
import com.github.alfabravo2013.entities.Royals;
import com.github.alfabravo2013.entities.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

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


    @Autowired // doesn't throw NoUniqueBeanDefinitionException if beans are put in a collection
    private List<Team> teams;

    @Bean
    @Scope("prototype") // singleton is default
    public Game game() {
        var game = new BaseballGame(home, away);
        game.setDataSource(dataSource);
        return game;
    }

    @Bean
    public Team royals() {
        return new Royals();
    }
}
