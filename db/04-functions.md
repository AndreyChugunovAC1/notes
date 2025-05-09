# Встроенные функции и их применение

```sql
/* for strings: */
select upper("VaLaMi") as Result /* VALAMI */
select length("VaLaMi") as Result /* 6 */
select lower("VaLaMi") as Result /* valami */
select instr("string base with pattern", "pattern") as index_of_pattern /* 18 */
select upper(left('sql-trololo', 3)) as str; /* SQL */
select upper(right('sql-trololo', 3)) as str; /* TROLOLO */

/* for dates: */
select year('2029-12-02') as Year /* 2029 */
select month('2029-12-02') as Year /* 12 */
select day('2029-12-02') as Year /* 2 */

/* other */
select * from SomeTable where some_column is null
select * from SomeTable where some_column is not null
select * from SomeTable where some_column between 100 and 1000
select * from SomeTable where some_column in ('valaki', 'valami')
```



