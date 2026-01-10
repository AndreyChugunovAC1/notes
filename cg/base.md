# Че-то про OpenGL

Версия 3.3 поддерживается почти везде

C-style подход к структурам:
```c
GLuint object_id = 0;
// Object - обозначение
glGenObject(1, &object_id);
glBindObject(object_id);
// Устанавливаем данные
glSetObjectStateDataEtc(...);
// Object - обозначение
// 0 - отвязать/привязать дефолтный
glBindObject(0); 
```