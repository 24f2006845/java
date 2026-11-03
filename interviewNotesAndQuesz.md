# Java Interview Notes and Questions

This guide covers the Java concepts and questions commonly tested in modern
software-engineering interviews. Company interviews differ by role and level, but
the same themes appear repeatedly: language fundamentals, object-oriented design,
collections, concurrency, SQL, APIs, system design, coding, debugging, and
communication.

> **AI-era note:** Companies increasingly expect engineers to use AI tools
> productively, but they still test whether you can reason independently. Never
> claim that generated code is correct without reviewing, testing, profiling, and
> explaining it. Follow the interviewer's policy about AI assistance during an
> assessment.

## 1. How to answer an interview question

Use this structure for most technical answers:

1. Define the concept in one sentence.
2. Explain how it works internally.
3. Give a small example.
4. State trade-offs, limitations, and complexity.
5. Mention a production use case or failure mode.

For coding questions:

1. Clarify input constraints, null behavior, duplicates, ordering, and expected output.
2. State a simple approach first.
3. Improve it using the right data structure or algorithm.
4. Explain time and space complexity.
5. Test empty, minimum, maximum, duplicate, and invalid cases.

Do not memorize only definitions. Interviewers often ask **why**, **when not to use
it**, and **what happens internally**.

## 2. Java fundamentals

### Q1. Why is Java platform independent?

Java source is compiled into bytecode (`.class` files). A Java Virtual Machine (JVM)
for each operating system executes that same bytecode. The JVM provides the platform
abstraction; the JDK provides tools such as `javac`, and the JRE conceptually
contains the runtime libraries and JVM.

```text
Hello.java --javac--> Hello.class --JVM--> machine-specific instructions
```

Java is not completely independent of its environment: native libraries, file paths,
default character sets, time zones, CPU architecture, and JVM versions can still
affect behavior.

### Q2. JDK, JVM, and JRE: what is the difference?

- **JVM:** Runs Java bytecode and manages execution, memory, JIT compilation, and GC.
- **JRE:** JVM plus runtime libraries needed to run applications.
- **JDK:** JRE/runtime plus development tools such as `javac`, `javadoc`, `jdb`, and
  monitoring tools.

Modern JDK distributions are normally used both to run and build applications.

### Q3. Is Java pass-by-reference?

No. Java is always **pass-by-value**. For an object, the copied value is the object
reference:

```java
static void rename(Student student) {
    student.name = "Mina";       // changes the shared object
    student = new Student("Raj", 21); // changes only the local copy
}
```

The caller sees `"Mina"` but its reference does not change to `"Raj"`.

### Q4. Primitive versus wrapper types

Primitives (`int`, `long`, `boolean`) store values directly. Wrappers (`Integer`,
`Long`, `Boolean`) are objects and can be used in generics and collections.

```java
int count = 10;
Integer boxed = count; // autoboxing
int value = boxed;     // unboxing
```

Wrappers can be `null`; unboxing `null` throws `NullPointerException`. Repeated
boxing in hot loops creates unnecessary allocations, so use primitives where a
nullable value is not needed.

### Q5. `==` versus `equals()`

- `==` compares primitive values or object references.
- `equals()` compares logical content when the class overrides it.

```java
String a = new String("java");
String b = new String("java");
System.out.println(a == b);      // false: different objects
System.out.println(a.equals(b)); // true: same content
```

Use `Objects.equals(a, b)` when either value may be null.

### Q6. Why is `String` immutable?

Once created, a `String` cannot change. Immutability makes strings safe to share,
allows string-pool reuse, provides stable hash codes for map keys, and improves
thread safety.

```java
String text = "Java";
text.concat(" interview");
System.out.println(text); // Java
```

The concatenation returns a new string. Use `StringBuilder` for repeated mutation in
a single thread and `StringBuffer` only when its synchronized operations are actually
needed.

### Q7. `final`, `finally`, and `finalize`

- `final` prevents reassignment, overriding, or inheritance depending on usage.
- `finally` is a cleanup block used with exception handling.
- `finalize()` was deprecated and should not be used; use `AutoCloseable` and
  try-with-resources.

```java
try (BufferedReader reader = Files.newBufferedReader(path)) {
    return reader.readLine();
}
```

The resource is closed even when reading throws an exception.

### Q8. What is the contract of `equals()` and `hashCode()`?

`equals()` should be reflexive, symmetric, transitive, consistent, and false for
null. If two objects are equal, they **must** have the same hash code. Unequal
objects may share a hash code.

```java
record UserId(String value) {}
```

Records generate value-based `equals()` and `hashCode()` for their components. Never
use mutable fields in a key while that key is stored in a `HashMap`; mutation can
make the entry unreachable.

## 3. Object-oriented programming and design

### Q9. Explain encapsulation, inheritance, polymorphism, and abstraction

- **Encapsulation:** Keep state private and expose controlled operations.
- **Inheritance:** Reuse or specialize behavior through an `is-a` relationship.
- **Polymorphism:** A parent/interface reference can invoke the actual object's
  overridden implementation.
- **Abstraction:** Expose what a component does while hiding how it does it.

Prefer composition when behavior can be assembled without a rigid hierarchy:

```java
interface PaymentMethod {
    void pay(BigDecimal amount);
}

final class CheckoutService {
    private final PaymentMethod paymentMethod;

    CheckoutService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    void checkout(BigDecimal amount) {
        paymentMethod.pay(amount);
    }
}
```

`CheckoutService` can use a card, wallet, or test payment method without changing
its code. This also follows dependency inversion and improves testing.

### Q10. Interface versus abstract class

Use an interface for a capability or contract that unrelated classes can implement.
Use an abstract class when subclasses share state, protected behavior, or a common
invariant. A class can implement multiple interfaces but extend only one class.

Modern interfaces can contain `default`, `static`, and private methods, but they
should not be used as a replacement for every abstract class.

### Q11. Overloading versus overriding

- **Overloading:** Same method name, different parameter list; resolved mainly at
  compile time.
- **Overriding:** Subclass supplies a compatible implementation; selected at runtime
  through dynamic dispatch.

Return type alone cannot overload a method. Use `@Override` so the compiler catches
signature mistakes.

### Q12. SOLID principles

- **S:** One reason to change.
- **O:** Open for extension, closed for modification.
- **L:** Subtypes must honor the parent contract.
- **I:** Prefer small, focused interfaces.
- **D:** Depend on abstractions, not concrete details.

Apply these pragmatically. Over-abstraction for a two-line class can make a system
harder to understand.

### Q13. What makes a class immutable?

Make the class final (or otherwise prevent unsafe subclassing), keep fields private
and final, initialize them in the constructor, provide no setters, and defensively
copy mutable input and output:

```java
public final class Account {
    private final String id;
    private final List<String> tags;

    public Account(String id, List<String> tags) {
        this.id = Objects.requireNonNull(id);
        this.tags = List.copyOf(tags);
    }

    public List<String> tags() {
        return tags;
    }
}
```

Immutability simplifies concurrency, caching, and reasoning about state.

## 4. Collections and generics

### Q14. Which collection should you choose?

| Requirement | Usual choice | Reason |
|---|---|---|
| Ordered, index-based access | `ArrayList` | Fast access, compact storage |
| Frequent insert/remove at both ends | `ArrayDeque` | Efficient deque operations |
| Unique values, no order needed | `HashSet` | Average constant-time lookup |
| Unique values with insertion order | `LinkedHashSet` | Preserves insertion order |
| Sorted values | `TreeSet` | `O(log n)` ordered operations |
| Key-value lookup | `HashMap` | Average constant-time lookup |
| Sorted keys | `TreeMap` | `O(log n)` ordered operations |
| Concurrent key-value access | `ConcurrentHashMap` | Scalable concurrent operations |

Do not choose `LinkedList` automatically for insertions. Finding the insertion
position can still be `O(n)`, and its node allocation hurts locality.

### Q15. How does `HashMap` work?

`HashMap` computes a hash, spreads it, selects a bucket, and compares keys using
`equals()` when collisions occur. Modern JDKs can treeify heavily-colliding buckets.
Average `get` and `put` are `O(1)`, but poor hashes or pathological collisions can
degrade performance.

Important details:

- It permits one null key and multiple null values.
- It is not thread-safe for concurrent mutation.
- Capacity and load factor affect resizing.
- Keys should be immutable with stable `equals()` and `hashCode()`.

### Q16. `HashMap`, `ConcurrentHashMap`, and `Collections.synchronizedMap`

`HashMap` is for single-threaded or externally synchronized use. A synchronized map
serializes access through one lock. `ConcurrentHashMap` supports concurrent reads and
updates more efficiently, but compound operations still require atomic methods:

```java
counts.merge(word, 1, Integer::sum);
```

Do not write `if (!map.containsKey(k)) map.put(k, v)` when another thread can update
the map; use `putIfAbsent`, `computeIfAbsent`, or `merge`.

### Q17. What are generics and type erasure?

Generics provide compile-time type safety:

```java
List<String> names = new ArrayList<>();
names.add("Asha");
```

Most generic type information is erased from runtime bytecode. Consequently, you
cannot use `new T()`, `List<String>.class`, or reliably test `value instanceof
List<String>`. Use bounded type parameters and wildcard variance:

- `? extends T`: producer; read as `T`.
- `? super T`: consumer; safely write `T`.

This is the PECS rule: **Producer Extends, Consumer Super**.

### Q18. Fail-fast versus weakly consistent iteration

Many non-concurrent collections detect structural modification and may throw
`ConcurrentModificationException`. This is a bug-detection aid, not a thread-safety
guarantee. Concurrent collections usually provide weakly consistent iterators that
can proceed while updates happen but do not represent one immutable snapshot.

## 5. Exceptions, streams, and modern Java

### Q19. Checked versus unchecked exceptions

Checked exceptions must be caught or declared and are appropriate when callers can
reasonably recover, such as a file not being available. Unchecked exceptions
(`RuntimeException`) commonly indicate programming errors or invalid arguments.

Do not catch `Exception` broadly and silently continue. Preserve the cause:

```java
throw new OrderNotFoundException("Order " + id + " was not found", cause);
```

At service boundaries, translate exceptions into stable API errors and log enough
context without exposing secrets or personal data.

### Q20. What is try-with-resources?

It closes every `AutoCloseable` resource in reverse declaration order, even when an
exception occurs, and preserves suppressed exceptions:

```java
try (InputStream input = Files.newInputStream(file);
     OutputStream output = Files.newOutputStream(target)) {
    input.transferTo(output);
}
```

### Q21. Stream API versus collection

A collection stores data; a stream describes a pipeline for processing data. Stream
operations are lazy until a terminal operation runs:

```java
Map<String, Long> counts = orders.stream()
    .filter(Order::isPaid)
    .collect(Collectors.groupingBy(
        Order::customerId,
        Collectors.counting()));
```

Avoid side effects inside `map` and `filter`. Streams are not automatically faster,
and parallel streams can be harmful for small workloads, blocking operations, shared
state, or request threads.

### Q22. `map`, `flatMap`, `filter`, and `reduce`

- `filter` keeps matching elements.
- `map` transforms one element into one result.
- `flatMap` transforms one element into multiple elements and flattens them.
- `reduce` combines elements into one result.

Use `collect` for mutable result containers and ensure a parallel reduction has an
associative, identity-safe operation.

### Q23. What are `Optional` best practices?

Use `Optional` mainly as a return type when absence is expected. Avoid using it for
fields, method parameters, serialization models, or `get()` without checking:

```java
String name = repository.findName(id)
    .map(String::trim)
    .filter(value -> !value.isEmpty())
    .orElse("Unknown");
```

## 6. Concurrency and multithreading

### Q24. Process versus thread

A process has its own address space and resources. Threads share a process's heap
and other resources but have separate stacks and execution state. Sharing makes
threads efficient but introduces visibility, atomicity, and ordering problems.

### Q25. Race condition, visibility, and atomicity

A race condition occurs when the result depends on timing. `volatile` provides
visibility and ordering for a variable, but it does not make compound operations
atomic:

```java
volatile int count;
count++; // read, add, write: still not atomic
```

Use `AtomicInteger` for a simple atomic counter or a lock for a compound invariant.

### Q26. `synchronized` versus `Lock`

`synchronized` is simpler and automatically releases its monitor when leaving the
block. `ReentrantLock` supports timed and interruptible acquisition, fairness options,
and multiple conditions, but must be unlocked in `finally`:

```java
lock.lock();
try {
    update();
} finally {
    lock.unlock();
}
```

Lock the smallest necessary region and always define a consistent lock order to avoid
deadlocks.

### Q27. `wait`, `notify`, and `notifyAll`

They operate on an object's monitor and must be called while holding that monitor.
Always wait in a loop because of spurious wakeups and condition changes:

```java
synchronized (queue) {
    while (queue.isEmpty()) {
        queue.wait();
    }
    return queue.remove();
}
```

In new code, prefer `BlockingQueue`, `CountDownLatch`, `Semaphore`, or
`CompletableFuture` when they express the intent more clearly.

### Q28. ExecutorService and thread pools

Creating a thread per task is expensive and can exhaust resources. An executor
controls worker count, task queuing, and shutdown:

```java
ExecutorService executor = Executors.newFixedThreadPool(4);
try {
    Future<Integer> result = executor.submit(() -> calculate());
    System.out.println(result.get());
} finally {
    executor.shutdown();
}
```

In production, configure bounded queues, rejection behavior, thread names, and
timeouts. Unbounded queues can hide overload until memory is exhausted.

### Q29. `CompletableFuture`

It composes asynchronous stages without blocking every step:

```java
CompletableFuture<User> user = getUser(id);
CompletableFuture<Order> order = user.thenCompose(this::getLatestOrder);
```

Handle failures with `exceptionally`, `handle`, or `whenComplete`. Avoid calling
blocking operations on an executor intended for CPU work.

### Q30. Deadlock, livelock, and starvation

- **Deadlock:** Threads wait forever for each other's locks.
- **Livelock:** Threads keep reacting but make no progress.
- **Starvation:** A thread rarely gets CPU time or a required resource.

Prevent them with lock ordering, timeouts, small critical sections, fair scheduling
where needed, and thread-dump analysis.

## 7. JVM memory and garbage collection

### Q31. Stack versus heap

Each thread has stack frames containing method-local execution state. Objects and
arrays are generally allocated on the heap and reclaimed when unreachable. The JVM
may optimize allocations through escape analysis, so the conceptual model explains
ownership and lifetime rather than guaranteeing a physical location.

Errors:

- `StackOverflowError`: usually excessive recursion or deep calls.
- `OutOfMemoryError: Java heap space`: live allocations exceed available heap.
- Metaspace or native-memory errors: another JVM/process memory area is exhausted.

### Q32. What is garbage collection?

GC finds unreachable objects and reclaims their memory. Generational collectors
exploit the fact that most objects die young. GC does not close files or database
connections, and an object retained in a static cache is still reachable and cannot
be reclaimed.

Diagnose instead of guessing:

```bash
java -Xms256m -Xmx1g -Xlog:gc* Application
```

Use heap dumps, Java Flight Recorder, `jcmd`, or a profiler to find retained
objects and allocation hotspots.

### Q33. What is a memory leak in Java?

A leak is unintended retention of objects, not failure to manually free memory:
static collections, unbounded caches, listeners, `ThreadLocal` values, and class
loaders are common causes. Fix ownership and lifecycle; increasing `-Xmx` only
delays the failure.

## 8. JVM internals and performance

### Q34. Interpreter, JIT, and warm-up

The JVM can interpret bytecode initially and compile frequently executed code into
native instructions with the JIT compiler. Performance measurements must include
warm-up and use realistic data. Avoid optimizing from a single timing sample.

### Q35. What is class loading?

Class loading typically involves loading bytecode, linking it (verification,
preparation, and resolution), and initializing static state. Class loaders use
parent delegation in common JVM implementations. Class-loader leaks can retain
classes and their static data, especially in applications that reload plugins.

### Q36. How would you investigate slow Java code?

1. Reproduce with representative input and define a measurable target.
2. Check algorithmic complexity and database/network latency.
3. Profile CPU, allocation, locks, and GC rather than guessing.
4. Inspect logs, metrics, thread dumps, and traces.
5. Change one factor, benchmark, and compare before/after.
6. Confirm the change does not damage correctness or tail latency.

Do not use `System.gc()`, arbitrary synchronization removal, or a larger heap as a
performance strategy without evidence.

## 9. Coding questions commonly asked

### Q37. Two Sum

**Question:** Return indices of two numbers whose sum equals a target.

```java
static int[] twoSum(int[] values, int target) {
    Map<Integer, Integer> seen = new HashMap<>();
    for (int i = 0; i < values.length; i++) {
        int needed = target - values[i];
        if (seen.containsKey(needed)) {
            return new int[] {seen.get(needed), i};
        }
        seen.put(values[i], i);
    }
    return new int[0];
}
```

The map stores earlier values and their indices. Time is `O(n)` and extra space is
`O(n)`. Clarify whether a solution is guaranteed and whether the same element may
be used twice.

### Q38. First non-repeating character

Count characters in insertion order, then return the first count of one:

```java
static Optional<Character> firstUnique(String text) {
    Map<Character, Integer> counts = new LinkedHashMap<>();
    for (char c : text.toCharArray()) {
        counts.merge(c, 1, Integer::sum);
    }
    return counts.entrySet().stream()
        .filter(entry -> entry.getValue() == 1)
        .map(Map.Entry::getKey)
        .findFirst();
}
```

This is `O(n)` time and `O(k)` space, where `k` is the number of distinct
characters. Ask whether Unicode code points, rather than UTF-16 `char` values,
must be supported.

### Q39. Valid parentheses

Use a stack of expected closing brackets:

```java
static boolean validBrackets(String text) {
    Deque<Character> expected = new ArrayDeque<>();
    for (char c : text.toCharArray()) {
        if (c == '(') expected.push(')');
        else if (c == '[') expected.push(']');
        else if (c == '{') expected.push('}');
        else if (c == ')' || c == ']' || c == '}') {
            if (expected.isEmpty() || expected.pop() != c) return false;
        }
    }
    return expected.isEmpty();
}
```

Time is `O(n)` and space is `O(n)`. `ArrayDeque` is preferred over the legacy
`Stack` class.

### Q40. LRU cache design

An LRU cache needs `O(1)` lookup and eviction. Combine a `HashMap` with a doubly
linked list, or use `LinkedHashMap` in access-order mode:

```java
final class LruCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    LruCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}
```

This example is not thread-safe and does not provide TTL, metrics, or distributed
coordination. Mention those limitations in a production design.

### Q41. Merge intervals

Sort by start time, then merge when the next interval overlaps the current one.
Sorting makes the complexity `O(n log n)`; the merge pass is `O(n)`.

### Q42. Top K frequent values

Count with a map and use a min-heap of size `k`, giving `O(n log k)` after counting.
If constraints permit, bucket sorting can reduce the expected work. Explain why the
chosen method fits the input limits.

### Q43. Binary tree and graph questions

Know DFS recursively and iteratively, BFS with a queue, cycle detection, topological
sort, shortest path for unweighted graphs, and Dijkstra for non-negative weighted
edges. State whether the graph is directed, whether it can be disconnected, and
whether edge weights can be negative.

## 10. SQL and persistence questions

### Q44. `INNER JOIN` versus `LEFT JOIN`

`INNER JOIN` returns only matching rows. `LEFT JOIN` returns every left row and
nulls for missing right-side data. A filter on the right table in `WHERE` can
accidentally turn a left join into an inner join; put the condition in `ON` when
unmatched left rows must remain.

### Q45. Indexes and transactions

An index accelerates reads by maintaining an additional data structure, but it costs
storage and slows writes. Index columns used in selective filters, joins, and
ordering, and verify with an execution plan.

Transactions provide atomicity, consistency, isolation, and durability. Isolation
levels trade concurrency against phenomena such as dirty reads, non-repeatable reads,
and phantom reads. Keep transactions short and avoid holding database transactions
while calling slow remote services.

### Q46. N+1 query problem

Loading one list and then querying once per item creates `1 + N` queries. Fix it with
a join, batch query, fetch strategy, or carefully designed data loader. Confirm the
result with SQL logs and query counts, not assumptions.

## 11. Spring and backend questions

### Q47. What is dependency injection?

The container constructs and supplies dependencies instead of a class creating them
itself. Constructor injection makes required dependencies explicit and supports
simple unit tests:

```java
@Service
final class OrderService {
    private final OrderRepository repository;

    OrderService(OrderRepository repository) {
        this.repository = repository;
    }
}
```

Avoid field injection for required dependencies because it hides coupling and makes
plain construction harder.

### Q48. What happens in an HTTP request?

A client sends a method, URL, headers, and possibly a body. The server routes it,
authenticates and validates it, invokes business logic, accesses dependencies, and
returns a status, headers, and body. Good APIs define idempotency, timeout, retry,
pagination, validation, and error formats.

### Q49. REST status codes

- `200 OK`: successful read or update with a response.
- `201 Created`: resource created; often include its location.
- `204 No Content`: successful operation without a response body.
- `400 Bad Request`: invalid request syntax or validation.
- `401 Unauthorized`: authentication is missing or invalid.
- `403 Forbidden`: authenticated but not allowed.
- `404 Not Found`: resource does not exist or is intentionally hidden.
- `409 Conflict`: state conflict, such as duplicate creation.
- `429 Too Many Requests`: rate limit exceeded.
- `5xx`: server or dependency failure.

### Q50. Authentication versus authorization

Authentication answers “who are you?” Authorization answers “what may you do?”
Validate tokens, enforce authorization at the server, use least privilege, protect
secrets, and never trust a role supplied only by the client.

## 12. System design topics

For a design question, cover:

1. Requirements and success metrics.
2. Scale: requests per second, data volume, read/write ratio, and latency target.
3. API and data model.
4. High-level components and request flow.
5. Consistency, availability, and failure handling.
6. Caching, queues, partitioning, and indexing.
7. Security, observability, deployment, and cost.
8. Bottlenecks and the next scaling step.

### Common company-style design prompts

- Design a URL shortener.
- Design a rate limiter.
- Design a notification service.
- Design a file upload service.
- Design a food-delivery or ride-matching backend.
- Design an order and payment workflow.
- Design a distributed cache.
- Design a logging or metrics pipeline.

### Example: rate limiter

A token-bucket limiter stores a token count and refill time per user or API key.
Each request consumes a token; when no token is available, return `429`. In a
distributed deployment, use an atomic operation in Redis or another shared store.
Discuss clock precision, fail-open versus fail-closed behavior, hot keys, TTL,
memory limits, and metrics.

## 13. AI-era interview expectations

### Q51. How do you use AI coding tools responsibly?

A strong answer:

> I use AI to explore alternatives, generate boilerplate, explain unfamiliar APIs,
> and create test ideas. I keep sensitive code and credentials out of unapproved
> tools, verify licenses and dependencies, review every line, run tests and static
> analysis, check security and performance, and remain accountable for the final
> change. For an assessment, I follow the stated policy and disclose assistance when
> required.

### Q52. How do you review AI-generated Java code?

Check:

- Correctness against requirements and edge cases.
- Null handling, input validation, and exception behavior.
- Complexity and allocation behavior.
- Thread safety and resource ownership.
- SQL injection, SSRF, authorization, secrets, and unsafe deserialization.
- Dependency provenance, license, and version compatibility.
- Tests that fail before the fix and pass after it.
- Whether the code matches repository conventions and is explainable.

### Q53. How would you build a reliable RAG/LLM-backed Java service?

Discuss ingestion, chunking, embeddings, vector search, metadata filters, prompt
construction, model timeouts, retries with backoff, rate limits, output validation,
prompt-injection defenses, access control, PII handling, caching, tracing, and
evaluation. Treat model output as untrusted input. Use structured output schemas
where possible and keep deterministic business rules outside the model.

### Q54. What is the difference between a model error and an application error?

A model can hallucinate, misunderstand a request, or return malformed output. The
application must validate output, enforce permissions and business rules, handle
timeouts and provider failures, and return a safe fallback or explicit error. Never
let a generated answer directly execute SQL, code, payments, or privileged actions
without constrained tools and validation.

## 14. Behavioral and project questions

Use the STAR structure: **Situation, Task, Action, Result**.

Prepare concise answers for:

- Tell me about yourself.
- Explain your most important project.
- Describe a production incident and your debugging process.
- Tell me about a disagreement with a teammate.
- Describe a time you improved performance.
- Describe a time you received difficult feedback.
- Why this company and this role?
- What trade-off did you make and what would you change now?
- How do you review code?
- How do you learn an unfamiliar technology?

For every project, know the architecture, your exact contribution, alternatives
rejected, scale, metrics, testing strategy, failure modes, and one improvement you
would make today. Do not claim ownership of work you cannot explain.

## 15. Company and level preparation

These are patterns, not guaranteed questions from any specific company:

| Interview style | Typical focus |
|---|---|
| Product/startup | End-to-end ownership, practical coding, APIs, debugging, speed with sound trade-offs |
| Large service company | Java fundamentals, OOP, collections, SQL, Spring, project scenarios |
| Big-tech-style | Data structures, algorithms, system design, behavioral depth, complexity |
| Fintech | Concurrency, transactions, idempotency, security, correctness, auditability |
| Platform/cloud | Distributed systems, observability, reliability, containers, performance |
| AI-focused team | Evaluation, data pipelines, retrieval, model safety, tool integration, backend fundamentals |

Adjust depth to the job description. A senior interview expects design trade-offs,
operational ownership, mentoring, and incident reasoning in addition to syntax.

## 16. Fast revision checklist

- Explain JVM, JDK, stack, heap, GC, JIT, and class loading.
- Implement `equals()`/`hashCode()` correctly and explain immutability.
- Select collections from requirements and give complexity.
- Explain generics, type erasure, streams, and `Optional`.
- Solve arrays, strings, hash maps, stacks, queues, trees, graphs, and intervals.
- Explain race conditions, `volatile`, locks, atomics, executors, and deadlocks.
- Explain SQL joins, indexes, transactions, isolation, and N+1 queries.
- Design an API with validation, auth, idempotency, timeouts, retries, and pagination.
- Diagnose heap, stack, GC, CPU, lock, and dependency failures.
- Review AI-generated code for correctness, security, licensing, and tests.
- Explain one project with measurable results and honest trade-offs.
