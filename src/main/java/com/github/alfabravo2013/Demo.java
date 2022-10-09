package com.github.alfabravo2013;

import com.github.alfabravo2013.entities.Game;
import com.github.alfabravo2013.entities.Team;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.text.NumberFormat;

public class Demo {
    public static void main(String[] args) throws ParserConfigurationException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//         ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        var royals = ctx.getBean("redSox", Team.class);

        var game1 = ctx.getBean("game", Game.class);
        System.out.println("Game 1: " + game1);

        var game2 = ctx.getBean("game", Game.class);
        game2.setAwayTeam(royals);
        System.out.println("Game 2: " + game2);

        System.out.println("Game 1: " + game1);

        var nf = ctx.getBean(NumberFormat.class);
        var amount = 12345.6789;
        System.out.println(nf.format(amount));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

    }
}
