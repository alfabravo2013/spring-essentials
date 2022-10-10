package com.github.alfabravo2013;

import com.github.alfabravo2013.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(ctx.getBeanDefinitionCount());
        for (var name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}
