package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.BaseballGame;
import com.github.alfabravo2013.entities.Game;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Demo {
    public static void main(String[] args) {
        // ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        var game = ctx.getBean("game", Game.class);
        System.out.println(game.playGame());

        System.out.println("There are " + ctx.getBeanDefinitionCount() + " beans");

        for (var name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        System.out.println(((BaseballGame) game).getDataSource().getClass().getName());
    }
}
