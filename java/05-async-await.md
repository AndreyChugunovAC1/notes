# Асинхроннность, неблокирующий ввод-вывод

## Проблема C10K (сейчас C10M)
Проблема огромного количества соединений. Есть те, которые ничего не делают.

Потоки сами по себе становятся дорогостоящим ресурсом

### `Buffer`
```java
buffer.capacity();
buffer.limit(); // like .size()
buffer.position();
buffer.remaining();

buffer.get()
buffer.get((T[])arr)
buffer.get((T[])arr, offset, length)

buffer.put((T)elem)
buffer.put((T[])arr)
...
```

```java
ByteBuffer, IntegerBuffer, DoubleBuffer...
ByteBuffer.asTBuffer(); // T = Integer, Long, ...
TBuffer.allocate(size); // fabric method

byteBuffer.order(byteOrder); // little, big, ... endians
```

```java
TBuffer.wrap(T[])
TBuffer.wrap(T[], offset, length)
buffer.hasArray() // сделан ли буффер из массива

buffer.array()
buffer.arrayOffset()
```

