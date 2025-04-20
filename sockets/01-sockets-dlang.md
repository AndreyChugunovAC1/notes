# Сокеты в DLang

Рассмотрим как устроены сокеты на примере языка *Dlang*.

## Создание простейшего сокета
```d
import std.socket;

auto server = new Socket(AddressFamily.INET, SocketType.STREAM);
```

`AddressFamily.INET` - для IPv4, `AddressFamily.INET6` - для IPv6

`SocketType.STREAM` - для *TCP*, `SocketType.DGRAM` (от DataGram) - для *UDP*.

```d
auto address = new InternetAddress("0.0.0.0", 8888);
```

Чтобы сказать серверу "находись на этом порту":
```d
server.bind(adress)
```


