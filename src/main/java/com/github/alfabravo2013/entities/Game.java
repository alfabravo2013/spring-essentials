package com.github.alfabravo2013.entities;

public interface Game {

    Team getHomeTeam();

    void setHomeTeam(Team team);

    Team getAwayTeam();

    void setAwayTeam(Team team);

    String playGame();
}
