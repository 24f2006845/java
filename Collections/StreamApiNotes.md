# Java Stream API

## 1. What problem does the Stream API solve?

Before Streams, collection processing often required several loops and
temporary variables. For example, finding completed orders, sorting them, and
creating a new list could require a loop for each step. That approach works,
but it mixes **what** the program wants with the loop mechanics:

```java
for (Order order : orders) {
    if (order.status().equals("COMPLETED")) {
	completedOrders.add(order);
    }
}
completedOrders.sort(Comparator.comparingDouble(Order::amount).reversed());
```

The Stream API lets us describe the data-processing pipeline directly:

```java
List<Order> completedOrders = orders.stream()
	.filter(order -> order.status().equals("COMPLETED"))
	.sorted(Comparator.comparingDouble(Order::amount).reversed())
	.toList();
```

Streams are useful when we need to perform a sequence of operations such as:

- filtering unwanted values;
- transforming values into another form;
- sorting or removing duplicates;
- calculating a result such as a sum or count;
- grouping data into a map.

The Stream API improves readability by separating the data source from the
operations performed on that data. It also provides library support for
parallel processing, although parallel streams should be used only after
measuring that they improve the real workload.

## 2. Important idea: a stream is not a collection

A collection stores data. A stream is a one-time pipeline for processing data.
Creating a stream does not modify the original collection, and a stream cannot
normally be reused after a terminal operation.

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5);

long count = numbers.stream()
	.filter(number -> number % 2 == 0)
	.count();

// numbers is still available and unchanged.
```

## 3. Stream pipeline stages

```text
Source -> Intermediate operations -> Terminal operation
```

### Source

The source is usually a collection, but it can also be an array, a file, or
another stream:

```java
Stream<String> stream = List.of("Java", "SQL").stream();
```

### Intermediate operations

Intermediate operations return another Stream, so they can be chained. They
are lazy: they do not run until a terminal operation is called.

| Operation | Purpose |
| --- | --- |
| `filter` | Keeps values matching a condition |
| `map` | Converts every value to another value |
| `flatMap` | Flattens nested streams |
| `distinct` | Removes duplicates |
| `sorted` | Sorts values |
| `limit` | Keeps only the first values |
| `peek` | Observes values while debugging; avoid using it for business logic |

### Terminal operations

Terminal operations produce a result or a side effect and start the pipeline.

| Operation | Purpose |
| --- | --- |
| `collect` / `toList` | Creates a collection or another result |
| `forEach` | Performs an action for each value |
| `count` | Counts values |
| `reduce` | Combines values into one result |
| `min` / `max` | Finds an extreme value |
| `anyMatch` | Checks whether at least one value matches |
| `allMatch` | Checks whether every value matches |
| `findFirst` | Returns the first matching value |

## 4. Real example

`StreamApi.java` contains a complete online-store example. Its first pipeline:

```java
List<String> completedOrderSummary = orders.stream()
	.filter(order -> order.status().equals("COMPLETED"))
	.sorted(Comparator.comparingDouble(Order::amount).reversed())
	.map(order -> order.id() + " - " + order.customer())
	.collect(Collectors.toList());
```

The pipeline performs four steps:

1. `stream()` creates a processing pipeline from the order list.
2. `filter()` excludes pending and cancelled orders.
3. `sorted()` puts the most expensive completed order first.
4. `map()` changes each order into display text, and `collect()` creates a list.

The second pipeline uses `groupingBy()` and `summingDouble()` to answer a
business question: how much completed revenue came from each customer?

```java
Map<String, Double> revenueByCustomer = orders.stream()
	.filter(order -> order.status().equals("COMPLETED"))
	.collect(Collectors.groupingBy(
		Order::customer,
		Collectors.summingDouble(Order::amount)));
```

## 5. Useful rules and cautions

- Prefer a stream when the operation is a clear data transformation or query.
- Use an ordinary loop when the logic has complex control flow, needs early
  mutation, or is less readable as a pipeline.
- Intermediate operations are lazy; no work happens until a terminal operation.
- A stream is single-use. Create a new stream if the data needs another query.
- Avoid changing the source collection while a stream is processing it.
- Avoid side effects inside `map` and `filter`; these operations should describe
  transformations and conditions.
- `parallelStream()` is not automatically faster. Test it with realistic data.
- Use `Optional` results from operations such as `findFirst`, `min`, and `max`
  safely instead of assuming a value always exists.
