# Java Multithreading Notes

Multithreading allows a program to perform more than one task concurrently.
For example, an online store can process several customer orders while also
updating inventory and sending notifications.

## 1. Process vs Thread

- A **process** is a running application with its own memory.
- A **thread** is a smaller execution unit inside a process.
- Threads in the same process share memory, which makes communication fast.
- Shared memory also creates risks such as race conditions.

## 2. Creating a Thread

### Extending `Thread`

```java
class ReportThread extends Thread {
    @Override
    public void run() {
        System.out.println("Generating report");
    }
}

new ReportThread().start();
```

### Implementing `Runnable`

`Runnable` is usually preferred because the task is separated from the thread
that runs it, and Java classes can extend another class.

```java
Runnable task = () -> System.out.println("Sending email");
new Thread(task).start();
```

Always call `start()` to create a new thread. Calling `run()` directly executes
the method on the current thread.

## 3. Thread Lifecycle

A thread commonly moves through these states:

1. **NEW** - created but not started.
2. **RUNNABLE** - ready to run or currently running.
3. **BLOCKED** - waiting to acquire a lock.
4. **WAITING** - waiting indefinitely for another thread.
5. **TIMED_WAITING** - waiting for a specified time.
6. **TERMINATED** - finished execution.

Useful methods:

```java
thread.start();   // Start a new thread
thread.join();    // Wait for it to finish
thread.sleep(500); // Pause the current thread for 500 milliseconds
```

`sleep()` pauses the current thread; it does not release a lock that the
thread already owns.

## 4. Real-World Example

The accompanying [multiThread.java](/Users/haiderkhan/Desktop/java/Basics/multiThread.java)
simulates an online store processing five orders:

- A fixed pool contains three worker threads.
- Three orders can be processed at the same time.
- `AtomicInteger` safely counts completed orders.
- `shutdown()` prevents new tasks from being submitted.
- `awaitTermination()` waits for submitted tasks to finish.

This is better than manually creating one thread per order because a thread pool
reuses a limited number of threads and avoids excessive resource usage.

## 5. Race Conditions

A race condition happens when multiple threads access shared data and the result
depends on the timing of their execution.

This operation is not automatically safe:

```java
counter++; // read, add, and write are separate steps
```

Possible solutions include:

- `AtomicInteger` for simple atomic values
- `synchronized` for critical sections
- `Lock` implementations for more control
- Concurrent collections for shared collections

## 6. Synchronization

Use `synchronized` when only one thread should execute a critical section at a
time:

```java
public synchronized void withdraw(int amount) {
    if (balance >= amount) {
        balance -= amount;
    }
}
```

Synchronization provides mutual exclusion and visibility, but excessive locking
can reduce performance.

## 7. Locks

`ReentrantLock` provides features such as timed lock attempts and explicit
unlocking:

```java
Lock lock = new ReentrantLock();

lock.lock();
try {
    // Critical section
} finally {
    lock.unlock();
}
```

Always unlock in a `finally` block. `tryLock()` can be useful when a thread
should avoid waiting forever.

## 8. `volatile`

`volatile` ensures that updates to a variable are visible to other threads:

```java
private volatile boolean running = true;
```

It does **not** make compound operations such as `count++` atomic. Use an
`AtomicInteger` or a lock when an operation must be indivisible.

## 9. Executor Services

Executor services manage thread creation and task execution:

```java
ExecutorService executor = Executors.newFixedThreadPool(4);

executor.submit(() -> processOrder());
executor.shutdown();
```

Common executors:

- `newFixedThreadPool(n)` - a fixed number of worker threads.
- `newSingleThreadExecutor()` - one worker, useful for ordered tasks.
- `newScheduledThreadPool(n)` - delayed or repeated tasks.
- `newVirtualThreadPerTaskExecutor()` - lightweight virtual threads in modern
  Java versions, useful for many blocking tasks.

For server applications, choose pool sizes based on workload rather than
creating unlimited platform threads.

## 10. `Callable` and `Future`

`Runnable` does not return a result. `Callable` can return a value and throw an
exception:

```java
ExecutorService executor = Executors.newSingleThreadExecutor();

Future<Integer> future = executor.submit(() -> 2 + 3);
System.out.println(future.get()); // 5; waits if necessary

executor.shutdown();
```

`future.get()` can block, so use `get(timeout, unit)` when indefinite waiting
would be unsafe.

## 11. CompletableFuture

`CompletableFuture` supports asynchronous pipelines:

```java
CompletableFuture
        .supplyAsync(() -> loadCustomer())
        .thenApply(customer -> createInvoice(customer))
        .thenAccept(invoice -> sendInvoice(invoice))
        .exceptionally(error -> {
            System.out.println("Order failed: " + error.getMessage());
            return null;
        });
```

It is useful when an operation has several dependent asynchronous stages.

## 12. Thread-Safe Collections

Use concurrent collections instead of manually synchronizing ordinary
collections:

- `ConcurrentHashMap`
- `CopyOnWriteArrayList`
- `BlockingQueue`
- `ConcurrentLinkedQueue`

Example producer-consumer queue:

```java
BlockingQueue<String> orders = new LinkedBlockingQueue<>();
orders.put("Order-101"); // Producer
String order = orders.take(); // Consumer; waits if empty
```

## 13. Coordination Tools

- `CountDownLatch` waits until a fixed number of operations complete.
- `CyclicBarrier` makes threads wait at a shared checkpoint.
- `Semaphore` limits access to a resource, such as database connections.
- `Phaser` coordinates multiple phases with a changing number of parties.

## 14. Deadlock and Starvation

### Deadlock

Deadlock occurs when threads wait forever for locks held by one another.

Reduce the risk by:

- Always acquiring multiple locks in the same order.
- Keeping critical sections small.
- Using timed `tryLock()`.
- Avoiding unnecessary nested locks.

### Starvation

Starvation occurs when a thread never receives enough CPU time or access to a
lock because other threads continually get priority.

## 15. Interrupts and Cancellation

An interrupt is a cooperative request for a thread to stop or reconsider its
work. It is not a forced kill.

```java
try {
    Thread.sleep(1000);
} catch (InterruptedException exception) {
    Thread.currentThread().interrupt(); // Preserve the interrupt status
    return;
}
```

Tasks should check interruption and finish cleanup before returning.

## 16. Best Practices

1. Prefer `ExecutorService` over manually creating many threads.
2. Keep shared mutable state to a minimum.
3. Use atomic classes for simple counters and flags.
4. Use concurrent collections for shared data structures.
5. Shut down executors when they are no longer needed.
6. Handle `InterruptedException` without silently ignoring it.
7. Use timeouts for external operations and blocking calls.
8. Keep lock scopes small and acquire locks consistently.
9. Name threads or use a custom `ThreadFactory` in production systems.
10. Measure performance; more threads do not always mean faster execution.

## 17. CPU-Bound vs I/O-Bound Tasks

- **CPU-bound:** encryption, image processing, and calculations. Too many
  threads can cause context-switching overhead; a pool near the number of CPU
  cores is often appropriate.
- **I/O-bound:** database calls, network requests, and file operations. Threads
  spend time waiting, so a larger pool or virtual threads may be appropriate.

The correct choice depends on measurements, task duration, memory limits, and
the external systems being called.
