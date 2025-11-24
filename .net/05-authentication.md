## Заметка про аутентификацию
Чтобы добавить независимые схемы аутентификации, необходимо сделать примерно так.
Да и вобще лучше исользовать везде где можно pattern builder:

```cs
var authBuilder = services.AddAuthorizationBuilder();
authBuilder.AddPolicy("Policy1", policy => ...)
authBuilder.AddPolicy(""Policy2", policy => ...)
```

То же самое для аутентификации:
```cs
var authenticationBuilder = services.AddAuthentication("DefaultScheme");
authenticationBuilder.AddCookie("Cookie", options => ...)
authenticationBuilder.AddJwtBearer("Bearer", options => ...)
```

Важный момент:
* **Про авторизацию** - при написании `services.AddAuthorizationBuilder()`
  каждый раз возвращается тот же самый builder (singletone) - поэтому можно много раз вызывать
* **Про аутентификацию** - при написании `services.AddAuthorization("Default Scheme Name")`
  каждый раз зачищаются зачищаются прошлые настройки и новый builder создается (неточная инфа, но тем не менее
  лучше всегда использовать pattern builder в явном виде чтобы он один был. К тому же прописывать
  дефолтную схему каждый раз странно, надо один
