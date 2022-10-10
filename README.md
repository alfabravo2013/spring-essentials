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

## Transaction isolation and propagation

### Isolation levels
- `READ_UNCOMMITED` means that two transactions working on the same row will see uncommited changes of the other.
*Note that some of such changes may be rolled back later!* **Pro**: this level doesn't block anything
and therefore is very fast. **Con**: this is very risky.
- `READ_COMMITTED` changes made by another transaction may be visible but only once they are committed. However, if
after querying a row it the row is changed, the changes are not visible (aka phantom reads).
- `REPEATABLE_READ` guarantees that repeated reads will produce the same result. This requires a lock on the row.
Phantom reads are still possible if another row is added that otherwise would have been in the result set.
- `SERIALIZABLE` prevents even phantom reads and requires a table lock.
- `DEFAULT` - default for the given data source.
Fast and risky -------->------------->----------->----------> Slow and safe
`READ_UNCOMMITED` -> `READ_COMMITTED` -> `REPEATABLE_READ` -> `SERIALIZABLE`

**The most common choice in terms of performance vs safety is `READ_COMMITTED`! 
This is the default level for most databases (see `DEFAULT`)**

### Propagation
All propagation levels are defined in Java EE, except for `NESTED` which is defined and implemented in Spring.
- `REQUIRED` is the most common one and means that if a transaction is going, we join it. When the current transaction
is complete, the control is passed to the caller. If there is no transaction, we create it and run. It's the default level.
- `REQUIRES_NEW` if a transaction is going, we suspend it and run a new one, and after it's done we resume the first 
transaction, even if the new transaction was rolled back. If there is no transaction, we create it and run.
- `SUPPORTS` if there is a transaction running, join it, otherwise do nothing. Read only methods often use this level.
- `NOT_SUPPORTED` if there is a transaction, suspend it, run the operation outside the transaction, and then resume it.
It may be used where our method does not need a transaction and may mess up another transaction that is running. If
there is no transaction, just proceed.
- `MANDATORY` - if there is a transaction, join it, otherwise throw `TransactionRequiredException`.
- `NEVER` if there is a transaction, throw an exception, otherwise just proceed.
- `NESTED` - Spring specific, if there is a transaction, execute within a nested transaction, otherwise behave like
`REQUIRED`. May be not supported by some data sources. Read docs if need to use it.
