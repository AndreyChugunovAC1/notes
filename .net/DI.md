# Dependency Injection

Dependency injection (DI) или внедрение зависимостей представляет механизм, который позволяет сделать взаимодействующие в приложении объекты **слабосвязанными**.

Такие объекты связанны через абстракции (`abstract class`, `interface`)

IoC-контейнеры (Inversion Of Control) - фабрики, устанавливающие зависимости для DI.

`IServiceProvider` - контейнер ASP.NET для IoC.

`app.Services` возвращает `ServiceDescriptor[]`.

Добавление зависимостей вручную, но извне:
```cs
builder.Services.AddTransient</* interface */, /* realization */>();

// ...

app.Services.GetService</* interface */>(); 
```
либо в обход слабой зависимости (сильная зависимость в общем-то):
```cs
builder.Services.AddTransient</* realization */>();

// ...

app.Services.GetService</* realization */>(); 
```

Добавлять новые методы в интерфейс целесообразно через расширения C#

### Время жизни сервисов
Есть 3:
* AddSingletone (один на все время)
* AddScoped (один на запрос)
* AddTransistent (каждый раз новый)

### Способы получения зависимости
Через конструктор, через метод с помощью `[FromServices]`

```cs
private readonly IMyService _myService;

public HomeController(IMyService myService) {
  _myService = myService;
}
```

### Способы установки зависимости
