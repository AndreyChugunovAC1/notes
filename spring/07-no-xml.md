# Способы настройки приложения
1) pure xml
2) xml + annotations
3) Java code + annotations

## Все в Java code
У каждого xml тега есть аналог-аннотация.

Чтобы все настроить в Java коде, необходимо создать специальный класс, помеченный аннотацией `@Configuration`.
```java
@Configuration
public class SpringConfig {
  @Bean
  public ClassicMusic classicBean() {
    return new ClassicMusic();
  }

  @Bean
  public MusicPlayer playerBean() {
    return new MusicPlayer(classicBean());
  }
}
```

Здесь от `@Bean` тот же эффект, как если бы мы написали `<bean>...</bean>` в xml. При этом имя метода (`musicClassicBean`, например здесь) - это его id.

Пример выше - подробная вариация (вручную, без `@Component` и `@Autowire`). Конечно же можно написать удобный xml код 
```xml
<context:component-scan base-package="com.example"/>
```
в виде аннотации:
```java
@Configuration
@ComponentScan("com.example")
public class SpringConfig {}
```
И тогда все будет работать так же просто как и ранее.

## Метод main
Раньше начало функции `main` выглядело так:
```java
var context = new ClassPathXmlApplicationContext("applicationContext.xml");
```
Теперь же оно будет таким:
```java
var context = new AnnotationConfigApplicationContext(SpringConfig.class);
```

Использование `context` такое же.

## Bean
Если ничего не сказано явно, то опять же *scope* - *singletone*.

Пример принципа работы *singletone* (код тот же что и был выше):
```java
@Configuration
public class SpringConfig {
  @Bean
  public ClassicMusic classicBean() {
    return new ClassicMusic();
  }

  @Bean
  public MusicPlayer playerBean() {
    return new MusicPlayer(classicBean());
  }
}
```
Здесь есть вызов classicBean, если он вызовется второй раз и далее, то Spring перехватит этот вызов и вернет тот единственный бин.

## Внедрение зависимостей из файла
Следующий код на java:
```java
@Configuration
@PropertySource("classpath:musicPlayer.properties")
public class SpringConfig {}
```
Эквивалентен следующему коду xml:
```xml
<context:property-placeholder location="classpath:musicPlayer.properties" />
```