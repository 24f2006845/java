# Advanced Java: Production Concepts, Notes, and Real-World Examples

This guide covers advanced Java topics that matter in senior interviews and
production systems. For every topic, ask four questions:

1. **What is it?**
2. **Why do we use it?**
3. **What problem does it solve?**
4. **What trade-off or failure mode must we manage?**

Examples use modern Java syntax where useful. Check the Java version supported by
your project before using a newer API.

## 1. Java Memory Model (JMM)

### What it is

The Java Memory Model defines how threads interact through memory. It explains
visibility, ordering, atomicity, and the **happens-before** relationship.

### Why it is used

Modern servers execute many requests concurrently. Without a memory model, one
thread could see stale values, reordered operations, or partially visible state.

### The problem it solves

It gives the JVM and developers rules for safely publishing data and coordinating
threads. Synchronization, `volatile`, thread start/join, and concurrent utilities
create happens-before relationships.

```java
final class Service {
    private volatile boolean running = true;

    void stop() {
        running = false;
    }

    void runLoop() {
        while (running) {
            processBatch();
        }
    }

    private void processBatch() {
        // Work performed by the service.
    }
}
```

`volatile` ensures a thread sees the latest value of `running`, but it does not
make `count++` atomic. For compound state, use a lock or atomic data structure.

### Safe publication

An object must be safely published before another thread uses it. Common safe
mechanisms include:

- assigning a `final` field in a constructor and not leaking `this`
- storing it in a `volatile` field
- publishing it under a lock
- putting it into a thread-safe collection
- completing a `Future`

```java
final class Configuration {
    private final String region;

    Configuration(String region) {
        this.region = region;
    }

    String region() {
        return region;
    }
}
```

Immutable configuration objects are easier to publish and share than mutable
objects with setters.

## 2. Advanced concurrency

### 2.1 Atomicity, visibility, and ordering

- **Atomicity:** An operation happens as one indivisible unit.
- **Visibility:** A write by one thread becomes observable by another.
- **Ordering:** Operations appear in a valid order under the memory model.

`AtomicInteger.incrementAndGet()` is atomic for one counter, but it does not protect
an invariant involving multiple fields:

```java
final class Account {
    private final Lock lock = new ReentrantLock();
    private long balance;

    void transferTo(Account target, long amount) {
        Account first = this;
        Account second = target;
        if (System.identityHashCode(first) > System.identityHashCode(second)) {
            first = target;
            second = this;
        }
        first.lock.lock();
        second.lock.lock();
        try {
            if (balance < amount) {
                throw new IllegalStateException("Insufficient funds");
            }
            balance -= amount;
            target.balance += amount;
        } finally {
            second.lock.unlock();
            first.lock.unlock();
        }
    }
}
```

In real payment systems, prefer a database transaction or ledger rather than
trying to coordinate money solely with JVM locks. The example demonstrates a
consistent lock order to reduce deadlock risk; equal identity-hash collisions
still need a tie-breaker in a reusable implementation.

### 2.2 `LongAdder` and `AtomicLong`

`AtomicLong` is useful when every update must be observed as one atomic value.
`LongAdder` spreads contention across cells and is often better for highly
concurrent metrics:

```java
private final LongAdder requests = new LongAdder();

void recordRequest() {
    requests.increment();
}

long requestCount() {
    return requests.sum();
}
```

`LongAdder.sum()` is not a single linearizable snapshot. Use an atomic counter when
exact read semantics are required.

### 2.3 `ThreadLocal`

`ThreadLocal` stores a value per thread. It can avoid passing request context
through every method, but pooled threads can retain values across requests:

```java
try {
    requestId.set(id);
    handleRequest();
} finally {
    requestId.remove();
}
```

Always remove request-specific data. Prefer explicit context or framework-supported
context propagation when asynchronous execution crosses threads.

### 2.4 Fork/Join and work stealing

The fork/join framework uses worker deques and work stealing. A worker processes
its own tasks and steals work from another worker when idle. It is useful for
CPU-bound divide-and-conquer tasks:

```java
class SumTask extends RecursiveTask<Long> {
    private final long[] values;
    private final int start;
    private final int end;

    SumTask(long[] values, int start, int end) {
        this.values = values;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= 1_000) {
            long total = 0;
            for (int i = start; i < end; i++) total += values[i];
            return total;
        }
        int middle = (start + end) / 2;
        SumTask left = new SumTask(values, start, middle);
        SumTask right = new SumTask(values, middle, end);
        left.fork();
        long rightResult = right.compute();
        return left.join() + rightResult;
    }
}
```

Do not submit blocking database or network work to the common fork/join pool.
Blocking can starve unrelated tasks.

### 2.5 Virtual threads

Virtual threads are lightweight JVM-managed threads designed for high-concurrency
blocking I/O. They solve the mismatch between one platform thread per request and
large numbers of waiting requests.

```java
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    Future<String> result = executor.submit(() -> callRemoteService());
    System.out.println(result.get());
}
```

Virtual threads are not faster CPU cores. They improve scalability for tasks that
spend time waiting on network or file I/O. They do not make a CPU-bound algorithm
faster. Audit synchronized blocks, native calls, thread-local usage, connection
pool limits, and libraries that pin carrier threads.

### 2.6 Structured concurrency

Structured concurrency treats related concurrent tasks as one bounded operation:
their lifetime, cancellation, and errors stay within a scope. APIs and preview
status depend on the JDK version, so verify availability before adoption.

The design principle is important even when using another library:

- cancel sibling work when the request is cancelled
- do not leave background tasks running after a request finishes
- propagate the first meaningful failure
- bound time with a deadline

Real-world example: a product page may fetch price, inventory, and reviews in
parallel, but should cancel remaining work when the request deadline expires.

## 3. Modern Java language features

### 3.1 Records

Records are concise immutable data carriers:

```java
public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }
}
```

They generate accessors, `equals`, `hashCode`, and `toString`. Use records for
validated commands, API responses, and value objects. They are not a replacement
for mutable persistence entities or classes with complex identity and lifecycle.

### 3.2 Sealed classes and pattern matching

Sealed types restrict permitted implementations and make domain states explicit:

```java
sealed interface PaymentResult
        permits PaymentApproved, PaymentDeclined {}

record PaymentApproved(String transactionId) implements PaymentResult {}
record PaymentDeclined(String reason) implements PaymentResult {}

static String message(PaymentResult result) {
    return switch (result) {
        case PaymentApproved approved -> "Approved: " + approved.transactionId();
        case PaymentDeclined declined -> "Declined: " + declined.reason();
    };
}
```

This solves incomplete handling of a closed set of business outcomes. Adding a new
permitted result forces compiler-guided updates. Avoid sealed hierarchies when
third-party extension is a requirement.

### 3.3 Switch expressions and pattern matching

Switch expressions return values and reduce accidental fall-through. Pattern
matching makes type checks and casts more readable. Use them to express domain
rules clearly, not to hide complex logic in one large expression.

### 3.4 Modules

The Java Platform Module System (JPMS) lets a project declare dependencies and
export only intended packages:

```text
module com.example.orders {
    requires java.sql;
    exports com.example.orders.api;
}
```

Modules can improve strong encapsulation, startup, and dependency clarity. Migration
can be difficult for libraries that rely on reflection or split packages. Many
applications still use the classpath, so understand both models.

### 3.5 Annotations and annotation processing

Annotations attach metadata to code. Runtime frameworks can inspect them with
reflection; compile-time processors can generate code or diagnostics without
runtime reflection.

Use annotations for declarative concerns such as validation, mapping, or routing.
Do not put critical business behavior in invisible magic that developers cannot
debug. Define retention, target, and documentation requirements deliberately.

## 4. Reflection, method handles, and dynamic proxies

Reflection discovers classes, fields, and methods at runtime. Frameworks use it for
dependency injection, serialization, and ORM mapping:

```java
Class<?> type = Class.forName("com.example.PaymentService");
Object instance = type.getDeclaredConstructor().newInstance();
```

Reflection can reduce compile-time safety, complicate native-image builds, and
create security or performance problems. Prefer direct calls when the type is known.

Dynamic proxies intercept interface calls and are useful for transactions,
logging, authorization, and metrics. Keep proxy behavior visible and test the
actual runtime wiring; self-invocation may bypass a proxy in common frameworks.

## 5. Serialization and data compatibility

### 5.1 Why Java native serialization is risky

`ObjectInputStream` can deserialize dangerous object graphs and creates brittle
coupling to class details. Do not deserialize untrusted data with native Java
serialization. Prefer JSON, Protocol Buffers, Avro, or another controlled format
with schema validation.

### 5.2 JSON API design

Define explicit DTOs rather than exposing internal entities:

```java
public record CreateOrderRequest(
        @NotEmpty List<OrderLineRequest> lines) {}

public record OrderResponse(
        UUID id,
        String status,
        BigDecimal total) {}
```

Validate input at the boundary, enforce authorization in business logic, and avoid
leaking stack traces, database identifiers, or sensitive fields.

### 5.3 Schema evolution

Backward-compatible changes usually add optional fields, preserve old meanings, and
avoid changing the type of an existing field. Consumers should tolerate unknown
fields. For events, version contracts, support replay, and test old and new
producers/consumers together.

Real-world example: adding `preferredLanguage` to an order event is safer than
renaming `customerId` or changing its type. Deploy consumers that understand both
versions before producing the new field.

## 6. NIO.2, networking, and asynchronous I/O

### 6.1 NIO.2

`Path`, `Files`, channels, buffers, and selectors provide scalable file and network
I/O:

```java
try (var channel = FileChannel.open(
        Path.of("orders.log"), StandardOpenOption.READ)) {
    ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
    while (channel.read(buffer) != -1) {
        buffer.flip();
        // Consume bytes.
        buffer.clear();
    }
}
```

Use buffered streaming for large files instead of loading the entire file into
memory. Handle partial reads, character encoding, file permissions, and cleanup.

### 6.2 HTTP clients

Set connect, request, and response timeouts. Bound retries and retry only operations
that are safe or idempotent. Include correlation IDs and metrics:

```java
HttpClient client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(2))
        .build();

HttpRequest request = HttpRequest.newBuilder(uri)
        .timeout(Duration.ofSeconds(3))
        .header("Accept", "application/json")
        .GET()
        .build();

HttpResponse<String> response =
        client.send(request, HttpResponse.BodyHandlers.ofString());
```

Never retry indefinitely. A retry storm can overload a failing dependency.

## 7. Reactive programming and backpressure

Reactive systems represent asynchronous sequences and allow a consumer to signal
how much data it can handle. **Backpressure** prevents a fast producer from
overwhelming a slow consumer.

Use reactive programming when the whole path benefits from non-blocking I/O and
high concurrency. Do not mix blocking JDBC calls into a reactive event loop;
offload them to a bounded scheduler or use a non-blocking driver.

Real-world example: a streaming export reads database pages, transforms records,
and writes them to a client. Backpressure pauses database reads while the client
is slow, limiting memory.

## 8. Persistence and transaction design

### 8.1 Transaction boundaries

A transaction should protect one business invariant and be short enough to avoid
lock contention. Do not hold a database transaction while waiting for a payment
provider or shipping API.

```java
@Transactional
public void reserveInventory(OrderId orderId) {
    Inventory inventory = repository.lockForUpdate(orderId);
    inventory.reserve();
}
```

The annotation only works when the framework actually intercepts the call; a
self-invocation can bypass a proxy. Verify transaction boundaries with tests and
logs.

### 8.2 Optimistic versus pessimistic locking

- **Optimistic locking:** Store a version and reject updates when it changed.
  Good when conflicts are uncommon.
- **Pessimistic locking:** Lock the database row during the transaction. Good when
  conflicts are frequent or overselling is unacceptable, but it reduces concurrency.

```java
UPDATE inventory
SET available = available - 1, version = version + 1
WHERE product_id = ? AND available > 0 AND version = ?
```

Check affected rows; zero rows means the reservation must be retried or rejected.

### 8.3 Connection pools

A connection pool avoids connection setup cost but is a finite resource. Set pool
size based on database capacity, transaction duration, and application concurrency.
An oversized pool can make the database slower. Track wait time, active connections,
leaks, and timeout errors.

### 8.4 Caching

Caching reduces latency and load but introduces staleness and invalidation problems:

- **Cache-aside:** Application reads cache, loads on miss, then stores.
- **Write-through:** Write goes through the cache to the source.
- **Write-behind:** Cache writes later to the source; faster but riskier.

Use TTL, size limits, negative-cache carefully, and protect against cache stampedes
with request coalescing or a distributed lock. Never cache authorization decisions
without including the correct identity and policy version in the cache key.

## 9. Distributed systems

### 9.1 Idempotency

An operation is idempotent if repeating the same request has the same business
effect. Network retries make this essential for payments and order creation.

```java
public OrderResponse createOrder(String idempotencyKey, CreateOrderRequest request) {
    return idempotencyStore.computeIfAbsent(
            idempotencyKey,
            key -> actuallyCreateOrder(request));
}
```

The real implementation must persist the key, request fingerprint, result, status,
and expiry atomically. Reject reuse of one key with a different request.

### 9.2 Timeouts, retries, and circuit breakers

- **Timeout:** Stop waiting for a dependency.
- **Retry:** Try a transient failure again with exponential backoff and jitter.
- **Circuit breaker:** Temporarily stop calls to a failing dependency.
- **Bulkhead:** Isolate resources so one dependency cannot consume all threads.

Retries should be bounded and should not amplify non-idempotent operations.

### 9.3 Outbox pattern

The outbox pattern solves the dual-write problem: a service must update its
database and publish an event without losing one side.

```text
transaction:
  update orders
  insert order_events(outbox)

background publisher:
  read unpublished outbox rows
  publish event
  mark row published
```

Consumers must be idempotent because the publisher may deliver an event more than
once. Include event IDs and monitor stuck outbox rows.

### 9.4 Saga pattern

A saga coordinates a business workflow across services using local transactions
and compensating actions. For an order: reserve inventory, authorize payment, and
schedule shipping. If payment fails, release inventory.

A compensation is not a time machine: an email cannot truly be unsent, and a
refund may take time. Design visible states such as `PAYMENT_PENDING` and provide
reconciliation.

### 9.5 Event sourcing and CQRS

- **Event sourcing:** Store state changes as an append-only event history.
- **CQRS:** Separate command/write models from query/read models.

They solve auditability, replay, and read-model scaling, but add schema evolution,
eventual consistency, projections, and operational complexity. Use them when those
benefits outweigh the cost, not as a default CRUD replacement.

### 9.6 Consistency models

Strong consistency makes a read reflect the latest successful write. Eventual
consistency allows replicas to converge later and often improves availability and
latency. State which operations require which guarantee.

Example: a bank balance may require strong transactional consistency, while a
product recommendation count can tolerate eventual consistency.

### 9.7 Distributed locks and leases

A distributed lock needs ownership, expiry, renewal, and safe release. A process
must not delete a lock acquired by another process after its lease expired. Use a
fencing token checked by the resource when correctness matters.

Often a database uniqueness constraint, queue, or idempotency key is safer than a
distributed lock.

## 10. Messaging and streaming

### Queue versus event stream

A work queue distributes tasks among consumers. An event stream retains an ordered
log that multiple consumer groups can replay independently.

Important production concerns:

- at-least-once delivery means duplicates are normal
- ordering is usually guaranteed only within a partition/key
- consumer lag is an operational metric
- poison messages need a retry policy and dead-letter handling
- message payloads need schema versioning

Real-world example: partition order events by `orderId` to preserve the order of
state transitions for one order, while allowing different orders to process in
parallel.

## 11. Observability

### Logs, metrics, and traces

- **Logs:** Detailed discrete events.
- **Metrics:** Aggregated numerical signals over time.
- **Traces:** Request path across services.

Use structured logs with correlation and trace IDs:

```java
logger.info("order_created orderId={} customerId={} durationMs={}",
        orderId, customerId, duration.toMillis());
```

Do not log passwords, tokens, full payment data, or unnecessary personal data.

Track:

- request rate, error rate, and latency percentiles
- dependency latency and timeout counts
- queue lag
- JVM heap, GC pauses, threads, and class loading
- database pool wait time

### SLI, SLO, and alerting

- **SLI:** Measured indicator, such as successful requests.
- **SLO:** Target, such as 99.9% successful requests.
- **SLA:** External contractual commitment.

Alert on user impact and error-budget burn, not every harmless log line.

## 12. Performance engineering

### 12.1 Big-O is necessary but not sufficient

Two `O(n)` algorithms can differ because of allocation, cache locality, I/O,
contention, or constant factors. Start with algorithmic complexity, then measure
the real bottleneck.

### 12.2 Allocation and object lifetime

Excessive temporary objects increase allocation and GC pressure. Do not manually
avoid every object; optimize only after profiling. Use primitive structures or
batching when a measured hot path requires them.

### 12.3 JMH benchmarking

Use JMH rather than a single `System.nanoTime()` loop. JMH handles warm-up,
forking, dead-code elimination, and measurement iterations:

```java
@Benchmark
public int parseOrder() {
    return parser.parse(input).size();
}
```

Benchmark realistic inputs and report variance. A microbenchmark cannot prove that
an end-to-end service will improve.

### 12.4 False sharing

False sharing occurs when independent frequently updated variables occupy the same
CPU cache line. It can hurt counters in high-throughput code. Use proven concurrent
structures and measure before padding or using specialized layouts.

### 12.5 Garbage collector selection

Choose a collector based on latency, throughput, heap size, and workload. G1 is a
general-purpose choice on modern JDKs; low-latency collectors may suit large heaps
with strict pause targets. Verify with GC logs and load tests rather than folklore.

## 13. Security essentials

### Input and output

Validate type, size, range, encoding, and allowed values at the boundary. Use
parameterized SQL:

```java
PreparedStatement statement = connection.prepareStatement(
        "SELECT id, status FROM orders WHERE customer_id = ?");
statement.setLong(1, customerId);
```

Escape output for its target context: HTML, SQL, shell, JSON, and logs have
different rules.

### Authentication and authorization

Authentication identifies a caller; authorization checks permission. Enforce
authorization on the server for every protected operation. Check object ownership,
not only broad roles.

### Secrets and cryptography

Store secrets in a managed secret store, not source code or logs. Use standard
cryptographic libraries and authenticated encryption. Never invent encryption,
password hashing, token formats, or random-number generation.

### Common Java-specific risks

- unsafe deserialization
- server-side request forgery through unrestricted URLs
- SQL injection
- path traversal
- XML external entity processing
- insecure temporary files
- leaking stack traces or credentials
- dependency vulnerabilities

Security is a design property, not only a final scan.

## 14. Testing advanced systems

### Test pyramid

- **Unit tests:** Fast isolated business rules.
- **Integration tests:** Real database, messaging, or framework boundaries.
- **Contract tests:** Verify API/event compatibility between services.
- **End-to-end tests:** Validate critical user journeys.

Avoid a test suite that mocks every dependency but never verifies the real SQL,
serialization, transaction, or security behavior.

### Property-based and mutation testing

Property-based tests generate many inputs to verify invariants such as “sorting
preserves the same elements.” Mutation testing changes code to check whether tests
actually fail. Both expose weak examples-based coverage.

### Concurrency testing

Use barriers, latches, repeated runs, stress tests, and tools that detect races.
A test that passes once does not prove thread safety. Test timeouts, cancellation,
duplicate messages, and partial failures.

### Testability design

Inject clocks, random sources, HTTP clients, and repositories. Avoid global state.
Use a controllable clock for expiry logic:

```java
Clock clock = Clock.fixed(instant, ZoneOffset.UTC);
Instant now = clock.instant();
```

## 15. Containers and deployment

### Container principles

A container packages an application and its dependencies, but it does not remove
the need for JVM tuning. Set memory-aware heap limits, graceful shutdown, health
checks, and non-root execution.

Use separate:

- **liveness:** should the process be restarted?
- **readiness:** can it receive traffic?
- **startup:** has initialization completed?

Do not mark a service ready before required dependencies and migrations are safe.

### Graceful shutdown

Stop accepting new traffic, allow in-flight work to finish within a deadline,
stop consumers, close pools, and flush important telemetry. Make shutdown
idempotent because orchestration systems may send repeated signals.

### Deployment strategies

- **Rolling:** Replace instances gradually.
- **Blue-green:** Switch traffic between two environments.
- **Canary:** Send a small percentage of traffic to the new version.
- **Feature flag:** Deploy code separately from enabling behavior.

Use backward-compatible database migrations: expand schema, deploy compatible
code, migrate data, then contract old columns.

## 16. Architecture and design patterns

### Hexagonal architecture

Keep domain logic independent of databases, HTTP, and frameworks. Ports define
interfaces; adapters implement them. This improves testing and allows infrastructure
replacement.

### Clean architecture

Dependencies point inward toward business rules. It solves framework coupling but
can become ceremony if every simple feature has many layers.

### Strategy pattern

Select an algorithm at runtime:

```java
interface ShippingCost {
    Money calculate(Order order);
}

final class Checkout {
    private final ShippingCost shippingCost;

    Checkout(ShippingCost shippingCost) {
        this.shippingCost = shippingCost;
    }
}
```

Useful for payment providers, shipping rules, pricing policies, and feature
rollouts. Keep selection rules outside each strategy.

### Factory and builder

Factories centralize creation and validation. Builders make complex optional
configuration readable and can enforce required values before construction.

### Adapter and decorator

An adapter translates one interface into another. A decorator adds behavior such
as metrics, retries, caching, or authorization without changing the wrapped class.
Be careful that decorators preserve error and timeout semantics.

## 17. Advanced interview questions and model answers

### Q1. Why can a thread-safe class still be unsafe when used by multiple threads?

Thread safety of individual methods does not guarantee that a sequence of methods is
atomic. `containsKey` followed by `put` is a check-then-act race. Use an atomic
compound operation such as `putIfAbsent` or protect the whole sequence with a lock.

### Q2. Why does adding more threads sometimes make a service slower?

Threads contend for CPU, locks, database connections, and memory. Context switching
and queueing increase latency. Measure CPU saturation, lock contention, pool wait,
and downstream capacity; then choose bounded concurrency.

### Q3. How do you prevent duplicate payment after a timeout?

Use an idempotency key stored durably with the request and result. On retry, return
the existing result. Combine it with provider-supported idempotency and reconciliation
for the case where the provider succeeded but the response was lost.

### Q4. How do you make an event consumer reliable?

Validate schema, process within a local transaction where possible, make handling
idempotent using an event ID, commit the offset only after successful processing,
retry transient failures with limits, route poison messages to a dead-letter flow,
and monitor lag.

### Q5. When would you choose a modular monolith over microservices?

Choose a modular monolith when the team or domain is small, deployment independence
is not yet needed, and strong transactional consistency matters. Enforce module
boundaries and separate domain responsibilities so extraction remains possible.
Microservices add network failures, operational cost, data consistency challenges,
and independent deployment; they are not automatically more scalable.

### Q6. How would you debug a production memory leak?

Confirm the symptom with heap and GC metrics. Capture a heap dump near the problem,
compare retained-size histograms, inspect GC roots, and identify static collections,
caches, listeners, thread locals, or class loaders retaining objects. Reproduce
with a load test, fix lifecycle ownership, and verify after redeployment.

### Q7. How do you investigate high latency?

Break the request into application CPU, queue wait, database, network, external
dependencies, locks, and GC. Use traces, percentiles rather than averages, thread
dumps, profiles, and database plans. Test the suspected bottleneck and compare
before and after under representative load.

### Q8. Why is “exactly once” difficult in distributed systems?

Failures can occur after a side effect succeeds but before an acknowledgment reaches
the caller. Retries then create duplicates. Most systems provide at-least-once
delivery and achieve effectively-once business behavior through idempotency,
deduplication, transactions, and reconciliation.

### Q9. How do you design a rate limiter?

Choose a limit and scope, such as 100 requests per minute per API key. A token
bucket permits bursts up to bucket capacity while refilling at a fixed rate. Use an
atomic shared store for multiple instances, return `429` and retry metadata, add
TTL and memory limits, and decide fail-open versus fail-closed behavior.

### Q10. How should an AI-generated code change be reviewed?

First verify requirements and edge cases. Then inspect security, authorization,
resource cleanup, concurrency, complexity, dependency licenses, compatibility, and
observability. Add a failing test for the bug or requirement, run focused and
regression tests, and review the diff as if written by an unknown contributor.
The developer remains responsible for the result.

## 18. Production readiness checklist

Before calling an advanced Java service ready, verify:

- requirements, failure behavior, and ownership are explicit
- timeouts exist for every remote call
- retries are bounded, jittered, and safe for the operation
- idempotency exists where clients or messages can retry
- database transactions are short and correctly scoped
- connection and thread pools are bounded and monitored
- logs contain correlation IDs but no secrets or sensitive data
- metrics cover traffic, errors, latency, saturation, and business outcomes
- traces identify slow dependencies
- health checks distinguish readiness from liveness
- graceful shutdown is tested
- input, authorization, and output validation are enforced
- schemas and migrations are backward compatible
- tests cover duplicates, timeouts, cancellation, and partial failure
- JVM heap, GC, threads, and native memory are observable
- AI-generated code has been reviewed, tested, and approved under policy

## 19. Final learning order

1. Master collections, generics, exceptions, streams, and OOP.
2. Learn the Java Memory Model, locks, atomics, executors, and virtual threads.
3. Understand JVM memory, GC, JIT, profiling, and JMH.
4. Build reliable HTTP services with validation, transactions, and observability.
5. Learn caching, messaging, idempotency, outbox, sagas, and consistency.
6. Practice security, testing, deployment, and incident debugging.
7. Design one complete system and explain every trade-off.
8. Use AI tools to accelerate work while retaining independent engineering judgment.
