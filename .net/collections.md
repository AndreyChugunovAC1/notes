# Коллекции в C# и интерфейсы

## Похожие на списки
1) `List<T>`
0) `LinkedList<T>`
0) `Collection<T>` - похож на `AbstractList<T>` в java - нужен для определения собственной списко-подобной СД.
0) `ObservableCollection<T>`
0) `ReadOnlyList<T>`, `ReadOnlyCollection<T>`
0) (устаревший) `ArrayList<T>`

## Похожие на множества
1) `HashSet<T>`
0) `SortedSet<T>`
0) `ImmutableHashSet<T>`
0) `ImmutableSortedSet<T>`
0) `FrozenSet<T>`

## Похожие на словари
1) `Dictionary<K, V>`
0) `SortedDictionary<K, V>`
0) `SortedList<K, V>`
0) `ImmutableDictionary<K, V>`
0) `ImmutableSortedDictionary<K, V>`
0) `FrozenDictionary<K, V>`

## Интерфейсы
1) `IList<T>`, `IReadOnlyList<T>`
0) `ISet<T>`, `IReadOnlySet<T>`
0) `IDictionary<K, V>`, `IReadOnlyDictionary<K, V>`
0) `ICollection<T>`
0) `IReadOnlyCollection<T>`
0) `IEnumerable<T>`

## Из более крутого:
1) `Span<T>`
0) `ReadOnlySpan<T>`
0) `Memory<T>`
0) `ReadOnlyMemory<T>`
0) `ArraySegment<T>`
0) `ReadOnlyArraySegment<T>`

В рамках задачи был реализован механизм передачи файлов от фронтенда к бэкенду через FileStorage v2 — сервис, построенный на основе TECM.

Как работает:

Фронтенд загружает файл в FileStorage v2, используя JWT-токен пользователя (для бухгалтеров требуется соответствующая роль).
Сервис возвращает UUID файла.
Бэкенд использует этот UUID для прямого обращения к TECM, предварительно получив собственный JWT-токен.
Для доступа к файлам бэкенд должен быть зарегистрирован в TECM с правами на работу с нужным типом файлов.
Процесс решения:

Изучена документация, проведены консультации с коллегами.
Рассмотрены и отвергнуты альтернативы: передача base64 через TWork.Proxy (высокая нагрузка), использование S3aaS и других решений.
Основная сложность — слабая и запутанная документация по FileStorage v2 и TECM.
Результаты:

Разработан и зафиксирован общий принцип передачи файлов, который может использоваться не только нашей командой.
Создана внутренняя инструкция по оформлению технической документации, направленная на улучшение будущих интеграций.

