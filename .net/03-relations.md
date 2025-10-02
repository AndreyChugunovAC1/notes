# Отношения между моделями

## Одна модель ссылается на другую
Самое простое отношение:

```cs
public class Company {
    public int Id { get; set; }
    public string? Name { get; set; }

    public List<User> Users { get; set; } = new();
}
 
public class User {
    public int Id { get; set; }
    public string? Name { get; set; }
 
    public int CompanyId { get; set; }
    public Company? Company { get; set; }
}
```

Вот так можно добавлять `User` и `Company`:
```cs
// 1
Company company1 = new Company { Name = "Google" };
User user1 = new User { Name = "Tom", Company = company1 };

db.Companies.AddRange(company1);
db.Users.AddRange(user1);
db.SaveChanges();

// 2
Company company2 = new Company { Name = "Microsoft" };
db.Companies.AddRange(company2);  // нужно, так как ключ генерируется БД
db.SaveChanges();

User user2 = new User { Name = "Bob", CompanyId = company2.Id };
db.Users.AddRange(user2);
db.SaveChanges();
```

В контексте примера выше: `User` - зависимая сущность, `Company` - основная.

Как это понять: зависимая сущность зависит от основной сущности (зависимость - как просто в случае с классами: есть ссылка)

Можно устанавливать зависимости через основную сущность:
```cs
User user1 = new { Name = "Object I" };
Company company1 = new { Name = "SUN Microsystems", Users = {user1} };
// ...
```

### Нюансы
1) Если не прописывать внешний ключ в классе `User`, он все равно будет создан неявно, лучше определять.
2) Можно вообще не определять даже свойство `Company`: если в классе `Company` уже есть свойство `Users`, все что выше будет создано автоматически.

В случае неуказаний не будет каскадного удаления (при удалении основной сущности, удаляется зависимая)

## Внешний ключ через аннотации и Fluent API
Если без аннотаций, то название внешнего ключа должно следовать соглашению (одному из двух) на примере:
1) навигационное свойство `Company` + название индекса основной модели - `Id` = `CompanyID`
2) имя класса основной модели `Company` + название индекса основной модели `Id` = `CompanyID`

Аннотация:
```cs
  public int? CompanyInfoKey { get; set; }
  [ForeignKey("CompanyInfoKey")]
  public Company? Company { get; set; }
```

Fluent API:





