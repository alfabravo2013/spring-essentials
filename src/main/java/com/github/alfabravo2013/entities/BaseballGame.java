package com.github.alfabravo2013.entities;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

public class BaseballGame implements Game {
    private Team homeTeam;
    private Team awayTeam;

    private DataSource dataSource;

    public BaseballGame() {}

    // Constructor injection is preferred if dependencies are absolutely required
    // since this way you cannot forget to initialize them
    public BaseballGame(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Team getHomeTeam() {
        return homeTeam;
    }

    @Override
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @Override
    public Team getAwayTeam() {
        return awayTeam;
    }

    @Override
    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override
    public String playGame() {
        return Math.random() < 0.5 ? getHomeTeam().getName() : getAwayTeam().getName();
    }

    @Override
    public String toString() {
        return String.format("Game between %s at %s", awayTeam.getName(), homeTeam.getName());
    }

    @PostConstruct
    public void startGame() {
        System.out.println("Playing national athem");
    }

    @PreDestroy
    public void endGame() {
        System.out.println("Showing highlights");
    }
}
