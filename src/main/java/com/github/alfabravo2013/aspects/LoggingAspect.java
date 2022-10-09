package com.github.alfabravo2013.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component // can be a @Bean as well
public class LoggingAspect {
    private final Logger logger = Logger.getLogger(getClass().getName());

    // to be applied at any depth within the ...alfabravo2013 package
    // on any method starting with "set" and having a single argument and returning void
    // (*) means a single arg, (..) means multiple args
    // In other words, it's intended to be applied on any setters
    @Before("execution(void com.github.alfabravo2013..*.set*(*))")
    public void callSetter(JoinPoint joinPoint) {
        var method = joinPoint.getSignature().getName();
        var args = Arrays.stream(joinPoint.getArgs()).map(Object::toString).toList();
        var target = joinPoint.getTarget().toString();
        logger.info(String.format("%s is called with the args %s on %s", method, args, target));
    }

    @Around("execution(String playGame())")
    public Object checkForRain(ProceedingJoinPoint pjp) throws Throwable {
        var rain = Math.random() < 0.5;
        Object result = null;
        if (rain) {
            logger.info(pjp.getTarget() + " rained out");
        } else {
            result = pjp.proceed();
            logger.info("Winner was " + result.toString());
        }
        return result;
    }
}
