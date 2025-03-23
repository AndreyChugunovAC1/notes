# Внедрение зависимостей

Обсудим решение 4 проблемы: "*(4) Объект, который нужно передать MusicPlayer, все же где-то надо передать*"

Да, это можно сделать в main, но опять же, spring позволяет это делать автоматически.

## Шаги работы со Spring
* Создание java-класса
* Связывание биныов (аннотации/xml)
* Теперь все объекты берутся из *контейнера Spring*

## Способы внедрения зависимостей
* Конструктор
* Setter
* scope/factory method/...
* Spring - xml, @annotation
* Autowiring - (наверное, предсказуемое) угадывание зависимостей

**Пример с xml:**
```xml
<bean id="musicPlayer"
    class="com.example.MusicPlayer">
    <constructor-arg ref="musicBean"/>
</bean>
```

### С помощью конструктора
```java
ClassA A = new ClassA(new ClassB());
```
```xml
<bean id="musicPlayer"
    class="com.example.MusicPlayer">
    <constructor-arg ref="musicBean"/>
</bean>
```

### С помощью setter'а
```java
public void setValue(SomeType value) {
  this.value = value;
}
```
```xml
<bean id="valueHolder"
    class="com.example.ValueHolder">
    <property name="value" ref="someTypeBean"/>
    <property name="anotherValue" ref="someTypeBeanAnother"/>
</bean>
```

## Внедрение значений из внешнего файла






