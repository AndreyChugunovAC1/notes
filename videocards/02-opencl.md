# OpenCL

Есть версия 3.0 - сужение 2.0. 2.0 не поддерживается *NVidia*.

Больльшинство фич *NVidia* 

## Термины
* Плаформа - реализацию *OpenCL*.
* Девайс - то же, что в *NVidia*. Системно независимая интеграция кода с библиотекой.

## Начало
### Перечисление и выбор платформы
*clGetPlatformIDs* -> *clGetPlatformInfo*

*clGetDeviceIDs* -> *clGetDeviceInfo*
### Выбор девайса
### Создание контекста

|CUDA|OpenCL|
|-|-|
|local|private|
|shared|local|
|global|global|


