# Библиотеки
## Статические библиотеки
Тут все понятно, кусок другого исполняемого файла линкуется с нашим исполняемым файлом.

Пара нужных флагов помогают разобраться с тем что поиск ведется по имени и пр.

__Пример компиляции:__
```cpp
// sum.cpp
int sum(int a, int b) {
  return a + b;
}

// main.cpp
#include <iostream>

int sum(int a, int b);

int main() {
    std::cout << sum(2, 3) << "\n";
    std::cout << sum(5, 7) << "\n";
    return 0;
}
```
1) Скомпилируем sum.cpp в объектный файл:
`clang++ -c sum.cpp -o sum.o`
2) Создадим статическую библиотеку из sum.o:
`ar rcs libsum.a sum.o`
3) clang++ -c main.cpp -o main.o
`clang++ -c main.cpp -o main.o`
4) Свяжем main.o со статической библиотекой: `clang++ main.o -L. -lsum -o main`

Тут все понятно, но ясное дело что пользоваться только статическими библиотеками - не лучший вариант. Если 10 исполняемый файлов захотят ей воспользоваться, то они может быть не влезут даже в оперативную память.

## Динамические библиотеки
Идейно понятно: библиотека загружается в память один раз и хоть сотня исполняемых файлов ей пользуется. 

Перед запуском программы, ищется дидамическиая библиотека с указанным заранее именем и лениво загружается в память.

__Пример компиляции:__
1) Скомпилируем sum.cpp в объектный файл: `clang++ -c -fpic sum.cpp -o sum.`
    * -fpic - генерирует позиционно-независимый код (Position Independent Code), необходимо для создания динамических библиотек.
2) Создадим динамическую библиотеку: `clang++ -shared sum.o -o libsum.so`
3) Компиляция main.cpp + указание что нужна динамическая библиотека:
`clang++ main.o -L. -lsum -o main`
4) Чтобы запустить нужно указать откуда ее брать:
    * `export LD_LIBRARY_PATH=.:$LD_LIBRARY_PATH` - одно из возможных решений. 
    * При компиляции main.cpp можно указать:
    `-Wl,-rpath=<path_to_lib>`, а в качестве `<path_to_lib>` можно указать `"$ORIGIN/mylibs/"`, где `$ORIGIN` - специальное имя, говорящее, что библиотека лежит с исполняемым файлом в одной директории.
    * Естественно, лучше использовать CMake/прочее.

Проблема: Динамические библиотеки загружаются в память процесса во время выполнения, и их точное расположение в памяти заранее неизвестно. Если код библиотеки зависит от фиксированных адресов, это вызовет проблемы, так как библиотека может быть загружена по разным адресам в разных процессах или даже в разных запусках одного процесса.

У Microsoft была такая идея: у библиотек есть _base-address_ - адрес куда она хочет загрузиться. С системными либами это должно было всегда прокатывать. Если же не получалось, То в специальной секции _.reloc_ хранился список адресов, которые надо исправить в библиотеке.

На линуксе же можно делать __относительные переходы__: в ассемблере можно написать несложный хак, который позволяет доставать адрес текущей инструкции, относительно которого уже понятно как сдвигаться. _-fpic_ именно этим и занимается. В 64-битном режиме это можно делать без хаков (_RIP-relative positioning_).

Окей, уже все готово. Библиотека должна быть в состоянии работать за одним исключением: она сама может захотеть обращаться к другим библиотекам. 

## GOT/IAT (global offset table), PLT (procedure linkage table)

Можно воспользоваться секцией реллокаций и вроде бы все заработает, но проблема в mmap: после загрузки библиотеки в память в ней образовались дырки. Поэтому заводят _GOT_ и все (мнемоники) _call_ обращаются к ней. В нее динамический загрузчик проставляет истинные адреса переходов.

__Рассмотрим пример кода:__
```cpp
void foo();

void bar() {
    foo();
}
```

Во время компиляции в bar будет пустой адрес до линковки. Если оказажется, что `foo` и вовсе из .dll/.so, что узнается на этапе линковки, то будет фигня. Поэтому компилятор создает прослойку/заглушку, которая является посредником для вызова `foo`. В линуксе это называется _PLT (procedure linkage table)_.

Еще замечание: _GOT_ рабобтает лениво. Если бы это было не так, то, например, LibreOffice грузил вы сразу все сотни динамических библиотек в память, чего не хочется делать.