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
            return appContext.getBean("royals", Team.class);
        } else {
            // call super.royals()
            // put "royals" to appContext
            // return "royals";
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
## Factory methods and factory beans

If we define a bean `NumberFormat` in an XML config:

```xml
<bean id="nf" class="java.text.NumberFormat" />
```

it won't work without a reference to a factory method because the class is abstract:

```xml
<bean id="nf" class="java.text.NumberFormat" factory-method="getCurrencyInstance" />
```

If a factory method is within a different class, we need to supply a `factory-bean`:

```xml
<bean id="factory" class="javax.xml.parsers.DocumentBuilderFactory" factory-method="newInstance" />
<bean id="builder" class="javax.xml.parsers.DocumentBuilder" factory-bean="factory" factory-method="newDocumentBuilder" />
```
*This example doesn't work throwing `BeanCreationException` with the reason 
`"because module java.xml does not export com.sun.org.apache.xerces.internal.jaxp to unnamed module"` but shows the principle.*
**Absolutely legacy code, should be replaced with Java-based config.**

## Bean initialization and destruction

`@Bean(initMethod="start", destroyMethod="finish")` is one of the ways to define custom initialization and destruction of a bean.
Both referred methods must not have any arguments and must return `void`.

**Note that Spring doesn't necesserily destroy a bean!** To enforce the destruction, call `ctx.close()` on your context reference.
**Also, note that init and destroy methods work only if the bean is a singleton!**

Another way is to use `@PostConstruct` and `@PreDestroy` on the methods inside the bean class. 
They are **standard Java annotations** and are not Spring-bound but **cannot be used if you don't own the code of the class**.

## AOP
This concept helps to avoid code tangling and code scattering. 
- Code tangling means that there are multiple unrelated things going on in one method. 
For example: doing business logic plus writing logs to a file plus checking permissions.
- Code scattering means repeating the same things in different parts of the application. 
For example: calling a logger everywhere.

Aspect oriented programming allows to encapsulate such things in a class and apply its functionality in various places.
This way the code is removed from the business logic and is not repeated elsewhere.
