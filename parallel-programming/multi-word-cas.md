## Пример
Удаление из середины связного списка

```kt
fun remove(n: Node<E>) = while (true) {
  prev := n.prev; next := n.next;
  if CAS2(&next.prev, n, prev,
          &prev.next, n, next): return
}
```
