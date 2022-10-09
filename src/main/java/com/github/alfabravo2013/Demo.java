package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.Game;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        var game = ctx.getBean("game", Game.class);
        System.out.println(game.playGame());
    }
}
