# Атомарные операции
... - опреация, выполняемая как единое целое (чтение, запись, ...)

**Важно:** инкремент и декремент - не атомарные, так как это композиции трех операций: чтения, изменения и записи.

В java:
```java
v.get();
v.set(newValue);
v.lazySet(newValue); // без барьера записи: не факт, что остальные будут знать об обновлении
v.getAndSet();
boolean changed = v.compareAndSet(expectedOldValue, newValue);
boolean changed = v.weakCompareAndSet(expectedOldValue, newValue);
```

## CAS
compare-and-set. Она работает так: если значение `v` равно `expectedOldValue`, то значение `v` меняется на `newValue` и возвращается `true`. Иначе, `v` не меняется, а функция вернет `false`.

Идиоматическое использование:
```java
do {
  old = v.get();
  newValue = process(old);
} while (!v.compareAndSet(old, newValue));
```

Тут стоит отметить, что не даром в C++ `CAS` называется compare-and-swap и `old` передается по ссылке и меняется.

Нюансы:
* Это активное ожидание
* Обычно цикл длится совсем недолго

Решение задачи доступа к ресурсу:
```java
// Почти аналогично synchromized(comeClass):
while (!v.compareAndSet(0, 1));
// do stuff
v.set(0);
```

### weakCompareAndSet
Иногда может "ошибиться": вернуть false, когда можно было бы вернуть true. То есть инфариант не меняется: проблема лишь в том, что может ошибиться, но это не страшно как бы.

Специально его использовать не нужно, но иногда он реализован на железе.

## Пре- и пост- операции
```java
getAndIncrement()
getAndDecrement()
getAndAdd(delta)  // use -delta for sub

incrementAndGet()
decrementAndGet()
addAndGet(delta) // use -delta for sub
```

Эти операции просто реализованы через `CAS` - они не являются атомарными, просто написаны аккуратно.

## Классы с атомарными операциями
```java
AtomicBoolean
AtomicInteger
AtomicLong
AtomicReference
```

Для JVM необходимо наличие атомарных операций с 32битными значениями, иначе 16 бит не хватит даже для старта JVM.

### Массивы
```java
AtomicIntegerArray
AtomicLongArray
AtomicReferenceArray
```
У них дополнительно есть операция `.length()`.

### Атомарный доступ к полям
```java
AtomicIntegerFieldUpdater
AtomicLongFieldUpdater
AtomicReferenceFieldUpdater
```

Типы полей должны быть `volatile` и не могут быть `static`.

Операции как обычно, но нужно указыать объект.

Создание: `newUpdater(class, fieldName)`

## Сбор статистики

```java
LongAdder
DoubleAdder
```
Более предпочтительный способ увеличения значения чем `AtomicLong` и `AtomicDouble`.

Более общие классы (работают с моноидами):
```java
LongAccumulator(op, zero)
DoubleAccumulator(op, zero)
```

## ABA problem
см. конспект по C++.

Решение - хранить версию СД, чтобы уметь отличать состояния.

Пример: стек, параллельно исполняются операции:
* `push(B)`; `pop()`
* `push(C)`

И push(C) должен отслеживать версию, иначе белеберда, которая может нарушить инвариант.

Общее решение в java:
```java
AtomicMarkableReference // помечена флажком одноразовая ссылка
AatomicStampedReference // помеченная числом многоразовая ссылка
```

Методы:
```java
getReference()
isMarked() / getStamp()
V get(X[] holder) // массив, чтобы аллокации делать не атомарно, это иначе дорого

// writing:
set(valueY, valueX) // изменить пометку и значение
attemptX(expectedV, valueX)

// conditional writing:
compareAndSet(oldV, newV, oldX, newX)
```

## Примитивы синхронизации
"Блокировка" `Lock`.
```java
lock()
unlock()
tryLock() // более низкоуровневая блокировка
```

`interface Lock`:
```java
lock()
lockInterruptibly()
tryLock(time?, unit?)
unlock()
```

Новое условие:
`newCondition()`

`interface Condition`:
```java
await(time?, unit?) // ждать условия
awaitUntil(deadline)
awaitUninterruptibly()
signal() // подать сигнал ~ notify()
signalAll() // подать сигнал всем
```

Пример кода с этим всем:
```java
void set(Object data) throws InterruptedException {
  lock.lock();
  try {
    while (this.data != null) {
      notFull.await();
    }
    this.data = data;
    notEmpty.signal();
  } finally {
    lock.unlock();
  }
}
```

## Разделенные блокировки (нет в java)
Но есть библиотеки
```java
// производитель
empty.lock()
full.unlock()

// потребитель
full.lock()
empty.unlock()
```

`ReentantBlock` - честная блокировка (выдает unlock тому, кто первый просил ее после блокировки). Большинство его методов - **для дебага**.

`ReadWriteLock` - блокировка для читателей-писателей. 
```java
readLock() // для читателей
writeLock() // для писателей
```

## Semaphore
Обобщение блокировки - им владеть может не более n потоков

```java
Semaphore s = new Semaphore(100);
s.aquire()
s.release()
```





