# Создание проекта
Для создания проекта нужно выбрать систему сборки (maven, gradle).

Затем следует выбрать *архетип* - файловую структуру проекта.

## Пример проекта:
(см. директорию `webapp`)

Рассмотрим что получилось на примере *maven* + *maven-archetype-webapp*:

```
├───src
│   └───main
│       └───webapp
│           ├───WEB-INF
|           |   └───web.xml
|           └───index.jsp
├───target
|   ├───classes
|   └───test-classes
└───pom.xml
```
`web.xml` - нужен для создания web приложения.

Далее следует создать директорию `src/main/resources` - директория по умолчанию для ресурсов в gradle или maven. То есть "*файлы или данные, которые используются приложением во время выполнения, но не являются частью исходного кода*". 

В *intellij idea* следует пометить эту директорию как *resource*. В остальных ide это достигается прописывнием вручную этого в `pom.xml`:

```xml
<build>
    <resources>
        <resource>
            <directory>
              путь/к/вашей/директории
            </directory>
        </resource>
    </resources>
</build>
```

Непосредственно для указанного выше путя `src/main/resources` этого делать не нужно - это директория по умолчанию.

### Известный казус
В maven раньше версия `JDK` (раньше это к тому же называлось `JRE`) записывалась в формате `1.*`. Не суть. Пришлось автоматически сгенерированную версию 1.8 поменять на 21:
```xml
  <maven.compiler.source>21</maven.compiler.source>
  <maven.compiler.target>21</maven.compiler.target>
```

Для самого кода следует создать `src/main/java/YOUR/ORGANIZATION/NAME/` - там будут находиться исходники кода.

В pom.xml также надо прописать зависимости. В данном примере это будут:
* spring-core
* spring-beans
* spring-context
Чтобы не запариваться с прописыванием зависимостей (теги все эти расставлять) можно воспрользоваться сайтом [Maven Repository](https://mvnrepository.com/)

Вот пример кода для работы с бинами:
```java
// class from spring-context
var context = new ClassPathXmlApplicationContext("applicationContext.xml");
TestBean testBean = context.getBean("testBean", TestBean.class);
System.out.println(testBean);
context.close();
```

Сам *bean*:
```java
public class TestBean {
  private String name;

  TestBean(String name) {
    updateName(name);
  }

  @Override
  public String toString() {
    return name;
  }
}
```

Чтобы Spring сам создавал нужные бины (и к тому же передавал в них аргументы), нужно прописать это в `src/main/resources/someConfigName.xml` как:
```xml
<bean id="testBean"
    class="com.example.TestBean">
    <constructor-arg value="Andrey"/>
</bean>
```

Еще в начале этого файла нужно поместить вот что-то в этом роде:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans  xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
```
Впрочем, этот файл работает с настройкой спринга, так что да, тут должно быть написать примерно все.

`xmlns` - xml namespaces.



