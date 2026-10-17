# Java Collections API

The Java Collections API is a group of interfaces and classes in
`java.util` used to store, retrieve, and process groups of objects.
Collections use generics so that the compiler can check the element type.

```java
import java.util.ArrayList;
import java.util.List;

List<String> names = new ArrayList<>();
names.add("Asha");
names.add("Ravi");
```

## 1. Main hierarchy

`Collection<E>` is the root interface for groups of elements. Its main
subinterfaces are:

| Interface | Purpose | Common implementations |
| --- | --- | --- |
| `List<E>` | Ordered elements; duplicates are allowed | `ArrayList`, `LinkedList` |
| `Set<E>` | Unique elements; duplicate values are ignored | `HashSet`, `LinkedHashSet`, `TreeSet` |
| `Queue<E>` | Elements waiting to be processed | `PriorityQueue`, `LinkedList` |
| `Deque<E>` | A double-ended queue; supports both ends | `ArrayDeque`, `LinkedList` |

`Map<K, V>` is part of the Collections API, but it does **not** extend
`Collection`. It stores key-value mappings.

| Map implementation | Important behavior |
| --- | --- |
| `HashMap` | Fast average lookup; no ordering guarantee |
| `LinkedHashMap` | Maintains insertion order |
| `TreeMap` | Keeps keys sorted |
| `ConcurrentHashMap` | Thread-safe map for concurrent access |

## 2. Choosing an implementation

### `ArrayList`

Use `ArrayList` as the default `List` implementation. It provides fast
index-based reads and is efficient when elements are mostly added at the end.
Inserting or removing near the beginning requires shifting later elements.

### `LinkedList`

`LinkedList` implements both `List` and `Deque`. It can be useful when
operations happen at the ends, but it usually performs worse than
`ArrayList` for ordinary list access because indexed lookup is linear.

### `HashSet`

Use `HashSet` when uniqueness is important and ordering is not. Correct
behavior depends on the element's `equals` and `hashCode` methods.

### `LinkedHashSet`

Use `LinkedHashSet` when values must be unique and iteration should follow
insertion order.

### `TreeSet`

Use `TreeSet` when unique values must remain sorted. Elements must implement
`Comparable`, or a `Comparator` must be supplied.

### `ArrayDeque`

Use `ArrayDeque` for an efficient stack or queue. It does not allow `null`
elements.

## 3. Common `Collection` methods

```java
List<Integer> numbers = new ArrayList<>(List.of(10, 20, 30));

numbers.add(40);                 // Add one element
numbers.addAll(List.of(50, 60)); // Add several elements
numbers.remove(Integer.valueOf(20)); // Remove by value
numbers.contains(30);            // Check for a value
numbers.size();                  // Number of elements
numbers.isEmpty();               // Check whether it has no elements
numbers.clear();                 // Remove all elements
```

`remove(20)` on a `List<Integer>` removes the element at index `20`.
Use `remove(Integer.valueOf(20))` when the intention is to remove the value
20.

## 4. Lists

Lists preserve insertion order and provide index-based operations.

```java
List<String> languages = new ArrayList<>();
languages.add("Java");
languages.add("Python");
languages.add(1, "Kotlin");

String first = languages.get(0);
languages.set(0, "Java SE");
languages.remove(1);

for (String language : languages) {
    System.out.println(language);
}
```

Use `List.of(...)` for a concise unmodifiable list:

```java
List<String> fixed = List.of("red", "green", "blue");
```

`List.of` does not allow `null` values, and attempts to add, remove, or
replace an element throw `UnsupportedOperationException`.

## 5. Sets

Sets do not store duplicate values.

```java
Set<String> roles = new HashSet<>();
roles.add("ADMIN");
roles.add("USER");
roles.add("ADMIN"); // No second ADMIN is stored
```

Set operations can be expressed with the collection methods:

```java
Set<Integer> a = new HashSet<>(Set.of(1, 2, 3));
Set<Integer> b = Set.of(3, 4, 5);

a.addAll(b);    // Union
a.retainAll(b); // Intersection
a.removeAll(b); // Difference
```

## 6. Queues and deques

Prefer the methods that clearly describe failure behavior:

| Operation | Throws on failure | Returns a special value |
| --- | --- | --- |
| Insert | `add`, `push` | `offer` |
| Remove | `remove`, `pop` | `poll` |
| Inspect | `element`, `peek` | `peek` |

```java
Deque<String> tasks = new ArrayDeque<>();
tasks.addLast("compile");
tasks.addLast("test");
tasks.addFirst("clean");

String next = tasks.removeFirst();
String last = tasks.peekLast();
```

`Deque` can replace the legacy `Stack` class:

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(10);
stack.push(20);
int top = stack.pop(); // 20
```

## 7. Maps

Maps associate each key with one value. Keys are unique; putting a new value
for an existing key replaces the old value.

```java
Map<String, Integer> scores = new HashMap<>();
scores.put("Asha", 90);
scores.put("Ravi", 85);
scores.put("Asha", 95); // Replaces 90

int score = scores.get("Asha");
int missing = scores.getOrDefault("Mina", 0);

for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

Useful map methods include `put`, `putIfAbsent`, `get`, `getOrDefault`,
`containsKey`, `remove`, `replace`, and `computeIfAbsent`.

```java
Map<String, List<String>> groups = new HashMap<>();
groups.computeIfAbsent("backend", key -> new ArrayList<>()).add("Java");
```

## 8. Iteration and modification

The enhanced `for` loop works for collections:

```java
for (String item : names) {
    System.out.println(item);
}
```

Use an `Iterator` when elements must be removed during iteration:

```java
Iterator<String> iterator = names.iterator();
while (iterator.hasNext()) {
    if (iterator.next().isBlank()) {
        iterator.remove();
    }
}
```

Calling `collection.remove(...)` directly inside an enhanced `for` loop can
cause `ConcurrentModificationException`. The iterator's `remove` method is
the supported approach for a single-threaded iteration.

## 9. Sorting and useful algorithms

`Collections` contains utility methods for existing collections:

```java
List<Integer> values = new ArrayList<>(List.of(30, 10, 20));

Collections.sort(values);
int position = Collections.binarySearch(values, 20);
Collections.reverse(values);
```

The list must already be sorted before using `binarySearch`. In modern Java,
`list.sort(comparator)` is often clearer:

```java
names.sort(Comparator.comparing(String::length));
```

Other useful utilities include `min`, `max`, `frequency`, `fill`, `copy`,
`rotate`, and `unmodifiableList`.

## 10. Generics and type safety

Declare the interface type on the left and the implementation on the right:

```java
List<String> items = new ArrayList<>();
Map<Integer, String> students = new HashMap<>();
```

Avoid raw types such as `List items`; they disable important compiler checks.
Use `? extends T` when reading from a producer and `? super T` when writing
to a consumer (the PECS rule).

## 11. Thread safety

Most standard collection implementations are not synchronized. For
concurrent code, choose a collection designed for that use case, such as
`ConcurrentHashMap`, `CopyOnWriteArrayList`, or `BlockingQueue`
implementations.

```java
Map<String, Integer> counts = new ConcurrentHashMap<>();
counts.merge("Java", 1, Integer::sum);
```

Synchronizing a collection wrapper does not automatically make a compound
sequence of operations atomic. For such operations, use the appropriate
concurrent collection or explicit synchronization.

## 12. Quick reference

| Requirement | Recommended choice |
| --- | --- |
| Ordered, index-based data | `ArrayList` |
| Unique data without ordering | `HashSet` |
| Unique data in insertion order | `LinkedHashSet` |
| Unique sorted data | `TreeSet` |
| FIFO queue | `ArrayDeque` |
| Stack behavior | `ArrayDeque` |
| Key-value lookup | `HashMap` |
| Key-value lookup in insertion order | `LinkedHashMap` |
| Sorted keys | `TreeMap` |
| Concurrent key-value access | `ConcurrentHashMap` |
