# Аттрибуты для моделей, FluentAPI

```cs
protected override void OnModelCreating(ModelBuilder modelBuilder) {
  modelBuilder.Entity<User>(). /* ... */;
}
```

* Ключ:
  * `[Key]`/назвать поле `Id` либо `[ClassName]Id`
  * `.HasKey(u => u.Ident).HasName("ididid");`
  * Уточнения для ключа:
    * `[DatabaseGenerated(DatabaseGeneratedOptions.None)]` либо `Identity` - генеирровать ли автоматически индекс
    * `Property(b => b.Id).ValueGeneratedNever()`
* Исключение свойства:
  * `[NotMapped]`
  * `Ignore(u => u.FieldToIgnore)`
* Имя таблицы:
  * `[Table("TableName")]`
  * `ToTable("TableName", schema: "...")`
* Имя свойства:
  * `[Column("column_name")]`
  * `.Property(u => u.prop).HasColumnName("PrOp")`
* Обязательное поле (NOT NULL):
  * `[Required]`/не указывать `?` у типа
  * `.Property(u => u.prop).IsRequired()`
* Альтернативные ключи (тоже уникальные, но не первичные):
  * `.HasAlternateKey(u => u.Passport)`
  * `.HasAlternateKey(u => new { u.Seria, u.Number })`
* Индексы БД (указывается над таблицей):
  * `[Index("PropName")]`
  * `[Index("PropName", IsUnique = true, Name ="prop_name_index")]`
  * `[Index("Seria", "Number")]`
  * `HasIndex(u => u.prop)`
  * `HasIndex(u => new { u.prop1, u.prop2 })`
  * `HasIndex(...).HasDatabaseName("index1")`
  * `HasIndex(...).HasFilter("SQL expression")` - для фильтров индексов БД (об этом потом почитать отдельно)
* Ограничения:
  * `.ToTable(t => t.HasCheckConstraint("ValidAge", "Age > 0 AND Age < 120"))`
  * По длинне (для массивов `byte[]` и прочие, либо для строк):
    * `[MaxLength(100)]` `[MinLength(1)]`
    * `HasMaxLength`

Вынесение настройки в классы кнфигурации:

Было:
```cs
protected override void OnModelCreating(ModelBuilder modelBuilder) { /*  */ }
```

Стало:
```cs
protected override void OnModelCreating(ModelBuilder modelBuilder) {
  modelBuilder.ApplyConfiguration(new UserConfiguration());
  /*  */
}

public class UserConfiguration : IEntityTypeConfiguration<User> {
  public void Configure(EntityTypeBuilder<User> builder) { /*  */ }
}
```

Даже еще лучше:
```cs
public class UserConfiguration : IEntityTypeConfiguration<User> {
  public void Configure(EntityTypeBuilder<User> builder) {
    /*  */
  }
}

[EntityTypeConfiguration(typeof(UserConfiguration))]
public class User { /*  */ }
```

## Инициализация БД начальными значениями
Id здесь указать надо:
```cs
protected override void OnModelCreating(ModelBuilder modelBuilder) {
  modelBuilder.Entity<User>().HasData(
    new User { Id = 1, Name = "Tom", Age = 23 },
    new User { Id = 2, Name = "Tom", Age = 23 }
  );
}
```


