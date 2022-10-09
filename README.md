# Spring demo

## Bean scopes

`"singleton"` is the default scope. If we have a Java config file with a bean definition:

```java
@Configuration
public class AppConfig {
    @Bean
    public Game game() {
        return new BaseballGame(royals(), cubs());
    }

    @Bean
    public Team cubs() {
        return new Cubs();
    }

    @Bean
    public Team royals() {
        return new Royals();
    }
}

```

Spring actually creates a sublass of `AppConfig` and overrides each method so that in the subclass `royals()`
becomes like

```java
class AppConfigProxy extends AppConfig {
    @Override
    public Team royals() {
        if (appContext.contains("royals")) {
            return appContext.getBean("royals");
        } else {
            super.royals();
            appContext.add("royals");
            return "royals";
        }
    }
}
```

that's why we can make `game()` to accept `@Bean` methods as arguments, and they still remain singletons:

```java
@Configuration
class AppConfig {
    @Bean
    public game() {
        return new BaseballGame(royals(), cubs());
    }
}
```
