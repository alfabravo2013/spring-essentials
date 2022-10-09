package com.github.alfabravo2013.entities;

import javax.sql.DataSource;

public class BaseballGame implements Game {
    private Team homeTeam;
    private Team awayTeam;

    private DataSource dataSource;

    public BaseballGame() {}

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
}
