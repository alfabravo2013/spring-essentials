package com.github.alfabravo2013.entities;


import com.github.alfabravo2013.AppConfig;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class BaseballGameTest {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private Game game;

    @RepeatedTest(10)
    void playGame() {
        var home = game.getHomeTeam().getName();
        var away = game.getAwayTeam().getName();

        var result = game.playGame();

        if (result == null) {
            return;
        }

        assertTrue(result.contains(home) || result.contains(away));
    }

    @Test
    void getAndSetHomeTeam() {
        var royals = ctx.getBean("royals", Team.class);
        game.setHomeTeam(royals);

        assertEquals(royals.getName(), game.getHomeTeam().getName());
    }
}
