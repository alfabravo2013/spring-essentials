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
