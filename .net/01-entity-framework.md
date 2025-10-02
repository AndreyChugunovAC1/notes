# Entity Framework

Рассмотрим несколько примеров

## 1
Можно вообще руками не создавать базу данных:

```bash
dotnet tool install --global dotnet-ef
```

```cs
public class User {
  public int Id { get; set; }
  public string? Name { get; set; }
  public int Age { get; set; }
}

public class ApplicationContext : DbContext {
  public DbSet<User> Users => Set<User>();
  public ApplicationContext() => DatabaseEnsureCreated();

  protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder) {
    optionsBuilder.UseSqlite("DataSource=helloapp.db");
  }
}
```

Тогда при работе с бд она создастся автоматически:

```cs
using (var db = new ApplicationContext()) {
  // на этом этапе уже проверено, есть ли бд, если нет, она создается

  db.Users.Add(new { Name = "Andrey", Ade = 19 });
  db.SaveChanges();

  // ...

  var users = db.Users.ToList();
  foreach(var user in users) {
    Console.WriteLine($"{user.Id}: {user.Name}, {user.Age}");
  }
}
```

## 2
Если база данных уже создана, то ее можно добавить в код одним взмахом ёршика:

```bash
dotnet add package Microsoft.EntityFrameworkCore.Design

dotnet ef dbcontext scaffold "строка подключения" провайдер_бд
```

В данном случае:
```bash
dotnet ef dbcontext scaffold "Data Source=R:\\path\\to\\database.db" Microsoft.EntityFrameworkCore.Sqlite
```

После этой комманды в коде появятся модели и класс `{DBName}Context` где все будет прописано.

## Свойство Database

В базовом классе `DbContext` есть свойство `Database`

`Database.EnsureCreated`, `Database.EnsureCreatedAsync` - проверяют наличие базы данных, если нет, создают ее.

`Database.EnsureDeleted`, `Database.EnsureDeletedAsync` - аналогично

`Database.CanConnect`, `Database.CanConnectAsync()` - возвращают `boolean`.

`db.SaveChanges()`, `db.SaveChangesAsync()`

`db.Users.Add()`, `db.Users.AddAsync()`, `db.Users.ToList()`, `db.Users.ToListAsync()`, ...

`db.Users.Update(user)` - если `user` был обновлен ранее, он (ссылка) больше не отслеживается `ApplicationContext`.

## Конфигурация БД

Есть 2 способа:
* в методе `OnConfiguring`
* в конструкторе

### Первый способ - OnConfiguring
```cs
protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
{
  optionsBuilder.UseSqlite("Data Source=mydb.db");
}
```

**Первый способ** + получение строки подключения извне: строку получить в конструкторе.

### Второй способ - в конутрукторе
```cs
public ApplicationContext(DbContextOptions<ApplicationContext> options)
            : base(options)
{
  Database.EnsureCreated();
}
```

Вот так замысловато тогда вызывался бы конструктор:

```cs
var optionsBuilder = new DbContextOptionsBuilder<ApplicationContext>();
 
var options = optionsBuilder.UseSqlite("Data Source=helloapp.db").Options;
 
using (ApplicationContext db = new ApplicationContext(options)) {...}
```
### Файл конфигурации

appsettings.json:

```json
{
  "ConnectionStrings": {
    "DefaultConnection": "Data Source=helloapp.db"
  }
}
```

Вот так тогда получить строку подключения:
```cs
using Microsoft.Extensions.Configuration;

var builder = new ConfigurationBuilder();
// установка пути к текущему каталогу
builder.SetBasePath(Directory.GetCurrentDirectory());
// получаем конфигурацию из файла appsettings.json
builder.AddJsonFile("appsettings.json");
// создаем конфигурацию
var config = builder.Build();
// получаем строку подключения
var connectionString = config.GetConnectionString("DefaultConnection");
```

* Во-первых: это можно сделать в одну строку 
* Во-вторых: все это можно проделать в `OnConfiguring`

### Логгирование

Чтобы логгировать операции есть следующий способ:
```cs
protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
{
  // ...
  optionsBuilder.LogTo(Console.WriteLine);
}
```

В LogTo можно передать любой делегат `Action<string>`.

Уточнение что логгировать:
```cs
optionsBuilder.LogTo(LogToFile, LogLevel.Information);
```

Уровни логгирования:
* `Trace` - подробное
* `Debug` - во время разработки
* `Information` - просто отследить поток выполнения
* `Warning` - неожиданные события
* `Error` - ошибки и исключения
* `Critical` - критические ошибки (переполнение памяти, ошибка ОС, потеря данных в БД)
* `None` - без логгирования

Каждое событие при логгировании имеет идентификатор (неполный список, скорее всего):
* `SqlServerEventId`
* `CoreEventId`
* `RelationalEventId`

Пример: только выполняемые неявно запросы SQL:
```cs
optionsBuilder.LogTo(Console.WriteLine, new[] { RelationalEventId.CommandExecuted });
```

Еще способ фильтрации - категории сообщения:
* `Database.Command` - категория для выполняемых команд, позволяет получить выполняемый код SQL
* `Database.Connection` - категория для операций подключения к БД
* `Database.Transaction` - категория для транзакций с бд
* `Migration` - категория для миграций
* `Model` - категория для действий, совершаемых при привязке модели
* `Query` - категория для запросов за исключением тех, что генерируют исполняемый код SQL
* `Scaffolding` - категория для действий, выполняемых в поцессе обратного инжениринга
* `Update` - категория для сообщений вызова DbContext.SaveChanges()
* `Infrastructure` - категория для всех остальных сообщений
```cs
optionsBuilder.LogTo(Console.WriteLine, new[] { DbLoggerCategory.Database.Command.Name });
```

### Миграции

- управление схемой БД (изменение структуры столбцов)

Пример: в модель User добавляем поле `strnig? Position` - должность. Тогда при работе с БД возникнет ошибка (нет такого столбца).

Есть 2 способа завершить изменение БД:
1) изменить руками БД через внешние инструменты
2) использовать миграции








