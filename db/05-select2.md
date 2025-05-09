# Выборка продолжение

## JOIN
- `INNER`
- `OUTER` (`RIGHT`, `LEFT`, `FULL`)

**Примеры:**
```sql
select member_name /* from FamilyMembers */, amount * unit_price as price /* from Payments */ from Payments
inner join FamilyMembers
    on Payments.family_member = FamilyMembers.member_id
```

```sql
select FamilyMembers.* from Payments
inner join FamilyMembers
    on Payments.family_member = FamilyMembers.member_id
```

**Псевдонимы для таблиц:**
```sql
select FamilyMembers.* from Payments as payms
-- ...
inner join Payments as payms
```



