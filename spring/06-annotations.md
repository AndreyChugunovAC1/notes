# Аннотации
Чтобы Spring понимал, что ему следует искать аннотации и ориентироваться на них, нужно написать:
```xml
<context:component-scan base-package="com.example"/>
```

## @Component
Этой аннотацией помечается класс, и она означает, что класс должен быть создан как *singleton* в *Spring Application Context*.

Можно указать *id*: `@Component("customNameFor my clazz")`. По этому *id* класс можно будет найти: `context.getBean("customNameFor my clazz", ...)`.

## @Autowired
Эта аннотация отвечает за внедрение зависимостей. Ее можно написать в 3 местах:
* Над конструктором, принимающим некоторый интерфейс или класс. Тогда Spring будет искать класс, (либо класс, реализующий интерфейс) и являющийся `@Component`.
* Над setter'ом - то же самое поведение
* Непосредственно над полем класса, даже приватным.

Вот все 3 способа вперемешку:
```java
@Component
class MyBean {
  @Autowired
  private MySubBean beanny;

  @Autowired
  public MyBean(MySubBean beanny) {
    this.beanny = beanny;
  }

  @Autowired
  public void setBeanny(MySubBean beanny) {
    this.beanny = beanny;
  }
}
```

**Нюанс:** В случае интерфейса, если не находится ни одного бина либо более одного бина, реализующего этот интерфейс - это ошибка (во втором случае - неоднозначность). Если же такой бин один - выбирается он.

Если конструктор принимает несколько, скажем, интерфесйов, которые нужно внедрить, то достаточно 1 раз написать `@Autowired`.

Ну и понятное дело, что это работает через *reflection* (*Java Reflection API*).

Все 3 способа внедрения абсолютно одинаковы, программисту же остается для себя решить в каком стиле это делать.

## @Qualifier
Проблема неоднозначности (интерфейс реализуют 2 компонента) может быть решена аннотацией `@Qualifier("id")`. Которая выбирает каким именно классом пользоваться.
```java
@Autowired
@Qualifier("subBean1")
private SubBean subBean;
```
Если конструктор принимает несколько бинов:
```java
@Autowired
public MyBean(@Qualifier("subBean1") SubBean subBean1,
  @Qualifier("subBean2") SubBean subBean2) {...}
```

## @Value
Нужна для внедрения зависимостей из внешнего файла.

Первые 2 шага внедрения зависимойтей из файла остаются: сам файл `*.properties` и 
```xml
<context:property-placeholder location="classpath:musicPlayer.properties" />
```

И затем можно написать:
```java
@Value("${musicPlayer.name}")
private String name;
```

## @Scope
```java
@Conponent
@Scope("singletone")
public class MyBean {}

@Conponent
@Scope("prototype")
public class MyBean {}
```
Порядок внедрения prototype'ов не гарантируется.

## @PostConstruct @PreDestroy
... - указание сответственно методов *init* и *destroy*. Требования к этим методам и прочее рассмотрено ранее.




