package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.Game;
import com.github.alfabravo2013.entities.Team;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.xml.parsers.ParserConfigurationException;

public class Demo {
    public static void main(String[] args) throws ParserConfigurationException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//         ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        var game = ctx.getBean("game", Game.class);
        var royals = ctx.getBean("royals", Team.class);
        var cubs = ctx.getBean("cubs", Team.class);
        var redSox = ctx.getBean("redSox", Team.class);

        game.setHomeTeam(royals);
        game.setAwayTeam(cubs);
        game.playGame();

        game.setHomeTeam(cubs);
        game.setAwayTeam(redSox);
        game.playGame();

        ctx.close();
    }
}
