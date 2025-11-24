# Асинхронность в C#, .NET
Статья: https://habr.com/ru/articles/727850/

##  Общая идея
... понятно: CPU, IO bounded задачи

## Раньше
Рассмотрим код:
```cs
class Handler
{
    public int DoStuff(string arg);
}
```

Он синхронный, чтобы его сделать асинхронным, можно сделать так:
```cs
class Handler
{
    public int DoStuff(string arg);

    public IAsyncResult BeginDoStuff(string arg, AsyncCallback? callback, object? state);
    public int EndDoStuff(IAsyncResult asyncResult);
}
```

BeginDoStuff - отвечает за начало асинхронной операции и, по возможности, передачу управления обратному вызову.

Пример как изменится вызов кода:

Было:
```cs
int i = handler.DoStuff(arg); 
Use(i);
```

Стало (это нарочито упрощенный код, в котором опущены целых 2 try-catch блока):
```cs
handler.BeginDoStuff(arg, iar =>
{
    Handler handler = (Handler)iar.AsyncState!;
    int i = handler.EndDoStuff(iar);
    Use(i);
}, handler);
```




