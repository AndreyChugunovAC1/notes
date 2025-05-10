# Удаленные репозитории

```sh
git clone ...
```

```sh
git remote add upstream <url>
git remote rename upstream origin
git remote remove origin
```

Посмотреть список удаленных репозиториев:
```sh
git remote -v
```

## Удаленные ветки
В удаленном репозитории есть свои ветки, названия которых имеют формат origin/branch_name.

```sh
git log origin
git log origin/main
```

## fetch
Забирает изменения с удаленного репозитория, но ничего более не делает. То есть ветка origin/someBranch теперь такая же как и удаленная, но локальные ветки нет.
```sh
git fetch origin
# более общая версия:
git fetch origin main
```

Чтобы изменения вошли как бы в силу, нужно сделать merge или rebase:
```sh
git merge origin/someBranch
# or
git checkout origin/someBranch
git rebase main
```

## pull
Сокращенная версия fetch + merge

Можно сделать fetch + rebase:
```sh
git pull --rebase
```

## push
```sh
git push origin localBranch:remoteBranch
```




