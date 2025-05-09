# Господи

__Линеаризация__ - процесс переупорядочивания действийствий над нашей структурой не изменяя её инварианта (то есть структура данных продолжает работать корректно). Надо только найти хотя бы одно последовательное исполнение.

* _lock-freedom_ - свойство многопоточных алгоритмов, которое гарантирует, что хотя бы один поток будет прогрессировать (выполнять полезную работу) за конечное время, даже если другие потоки могут быть заблокированы или завершены аварийно
* _wait-freedom_ - каждый поток гарантированно прогрессирует
* _obstruction-freedom_ где поток прогрессирует, если другие потоки не мешают

---

* Поток __блокируется__, если он не может прогрессировать из-за ожидания ресурса или другого потока.

## Treiber stack
... - _lock-free_ структура данных на списке.

## Проблема с shared_ptr
Если один поток удаляет последний 
shared_ptr , считая, что объект больше не используется, а другой поток в это время увеличивает счётчик ссылок, может произойти _double-free_.

Идея: дождаться, когда все операции выполнятся до какого-то момента и потом GC:

## Epoch-based reclamation
Разделяем время выполнения программы на эпохи (epochs). Каждый поток работает в
определённой эпохе, и память, удалённая в одной эпохе, освобождается только после
того, как все потоки перейдут в следующую эпоху. 

* Каждый поток поддерживает локальный список памяти, которую нужно освободить.
* Существует глобальная переменная, которая указывает текущую эпоху.
* Когда поток хочет освободить память, он добавляет её в локальный список текущей эпохи.
* Когда все потоки переходят в следующую эпоху, память из предыдущей эпохи может быть безопасно освобождена.
* GC останавливает все потоки.

## ABA problem 
* Первый поток хотел удалить А стека.
* Второй поток удаляет А из стека
* Второй поток удаляет В из стека
* Второй поток вставляет А в стек
* Операция первого потока завершается
* Третий поток извлекает В из стека

Решение: _tagged pointer_ - каждый указатель дополняется версией (тегом), которая увеличивается при каждом изменении указателя.

_CAS_ операция проверяет как указатель, так и версию, что предотвращает успешное выполнение _CAS_, если значение изменилось (сложная инструкция сравнения 128 бит информации атомарно).