package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.Game;
import com.github.alfabravo2013.entities.Team;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        // ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        var royals = ctx.getBean("royals", Team.class);

        var game1 = ctx.getBean("game", Game.class);
        System.out.println("Game 1: " + game1);

        var game2 = ctx.getBean("game", Game.class);
        game2.setAwayTeam(royals);
        System.out.println("Game 2: " + game2);

        System.out.println("Game 1: " + game1);
    }
}
