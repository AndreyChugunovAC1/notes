# Branches
Ветки в git - это способ распределения работы.

С точки зрения программиста, на это удобно смотреть как на дерево, которое хранится так:

```d
interface AbstractNode {}

class Node : AbstractNode {
  YourWork data;
  AbstractNode parent;
}

class MergeNode : AbstractNode {
  YourWork data;
  AbstractNode mama, papa;
}

AbstractNode[] branches;
// Про HEAD - позже
```

DeepSeek также предложил следующую структуру, с большим числом подробностей:
```d
// Коммит (Commit)
struct Commit {
  string hash;          // "a1b2c3d..."
  Commit*[] parents;     // Обычно 1, у merge — 2
  string message;
  Tree tree;            // Состояние файлов
}

// Ветка (Branch) — просто указатель на коммит
struct Branch {
  string name;          // "main", "bugFix"
  Commit* target;       // Куда указывает
}

// HEAD — текущая ветка или коммит
union Head {
  // Могут быть null
  Branch* branch;       // Если на ветке
  Commit* detached;     // Если в detached HEAD
}
```

Основной способ работы с ветками выглядит примерно так:
![](images/branch-merge.png)

Основная ветка называется (по традиции) либо `main` либо `master`.

## Создание ветки
В месте где мы сейчас находимся, фактически просто создает новый указатель в то же место. Пока что вообще ничего больше не произошло.
```sh
git branch BranchName
```

Потом, можно взять себе этот указатель:
```sh
git checkout BranchName
```

Можно две предыдущие комманды объединить в одну:
```sh
git checkout -b BranchName
```

После этих двух комманд можно сказать, что мы *"находимся на ветке `BranchName`"*.

Теперь все изменения, которые мы будем делать будут происходить на этой ветке, покуда мы не переключимся на другую.

Чтобы посмотреть историю, можно воспользоваться коммандой:
```sh
git log --oneline --graph --all
```