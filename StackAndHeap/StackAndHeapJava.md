# Stack and Heap in Java

Java uses memory mainly through the **stack** and the **heap**. Understanding both helps explain how method calls, local variables, objects, and returned values work.

## 1. Stack memory

The stack stores information for method execution. Each time a method is called, Java creates a **stack frame** for that method.

A stack frame can contain:
- local primitive variables, such as `int number`
- method parameters
- references to objects stored in the heap
- the place where execution should continue after the method returns

When the method finishes, its stack frame is removed automatically. The stack follows **LIFO** order: last in, first out.

Example call order:
```text
main() calls calculateTotal()
calculateTotal() calls createStudent()
createStudent() finishes and is removed
calculateTotal() finishes and is removed
main() finishes and is removed
```

Each method call creates a new frame on the same thread's stack. A normal method call does not create a completely separate stack; it adds another frame to the current stack.

## 2. Heap memory

The heap stores objects and arrays created with `new`.

Example:
```java
Student student = new Student("Asha", 20);
```

What happens:
1. `new Student(...)` creates a `Student` object in the heap.
2. The object stores its fields, such as `name` and `age`, in the heap.
3. The local variable `student` is a reference stored in the current stack frame.
4. The reference points to the `Student` object in the heap.

The reference is not the object itself. It is a way to find the object.

## 3. How a method calls another method

Consider this call:
```java
int total = calculateTotal();
```

The process is:
1. Java creates a stack frame for `calculateTotal()`.
2. `calculateTotal()` calls another method, so Java creates another stack frame for that method.
3. The called method calculates a value and uses `return` to send the value back.
4. Its stack frame is removed.
5. The calling method receives the returned value and stores it in `total`.

Primitive values such as `int` are returned as values. If a method returns an object, it returns a reference to an object in the heap.

## 4. Complete example

The matching file is `StackAndHeapsInJava.java`.

```java
class Student {
	String name;
	int age;

	Student(String name, int age) {
		this.name = name;
		this.age = age;
	}
}

public class StackAndHeapsInJava {
	public static void main(String[] args) {
		// The main() method gets the first stack frame for this program.
		int marks = 85;

		// new creates a Student object in the heap.
		// student is a local reference stored in the main() stack frame.
		Student student = createStudent("Asha", 20);

		// calculateTotal() creates another stack frame.
		// Its returned int value is copied into total in main().
		int total = calculateTotal(marks, 10);

		System.out.println(student.name + " has " + total + " marks.");
	}

	private static Student createStudent(String name, int age) {
		// This method gets its own stack frame for name and age.
		// The Student object itself is created in the heap.
		Student newStudent = new Student(name, age);

		// The reference to the heap object is returned to main().
		// After this return, this method's stack frame is removed.
		return newStudent;
	}

	private static int calculateTotal(int firstMark, int secondMark) {
		// firstMark and secondMark are local values in this stack frame.
		int result = firstMark + secondMark;

		// The int value is returned to main().
		// After this return, calculateTotal()'s stack frame is removed.
		return result;
	}
}
```

Output:
```text
Asha has 95 marks.
```

## 5. Memory picture during `createStudent()`

While `createStudent()` is running, memory can be understood like this:
```text
STACK                                      HEAP
-----                                      ----
createStudent() frame                     Student object
name -> "Asha"                             name = "Asha"
age = 20                                   age = 20
newStudent ------------------------------> object address
                                           
main() frame                               
student ---------------------------------> same object
```

The `student` reference in `main()` and the `newStudent` reference in `createStudent()` can point to the same heap object. Once `createStudent()` returns, `newStudent` disappears with its stack frame, but the object remains reachable through `student`.

## 6. What happens after a method returns?

- The method's stack frame is removed.
- Its local variables are no longer directly available.
- A returned primitive value is received by the caller.
- A returned object reference can be stored by the caller.
- An unreachable heap object can later be removed by Java's garbage collector.

Example:
```java
Student student = createStudent("Asha", 20);
```

The `Student` object remains available because `student` still points to it. If no reference points to that object anymore, the object becomes eligible for garbage collection.

## 7. Stack versus heap

| Stack | Heap |
|---|---|
| Stores method frames and local variables | Stores objects and arrays |
| Works per thread | Shared by threads in the Java process |
| Frames are removed when methods return | Objects remain until unreachable and collected |
| Usually faster to manage | Used for dynamically created data |
| Limited in size | Usually larger than the stack |

## 8. Common mistakes

### Mistake 1: Thinking the reference is the object
```java
Student student = new Student("Asha", 20);
```

`student` is a reference in the stack frame. The actual `Student` object is in the heap.

### Mistake 2: Thinking every method gets a new stack

Each method call gets a new **stack frame** on the current thread's stack. A new stack is normally associated with a new thread, not with every method call.

### Mistake 3: Expecting local variables to work after the method returns
```java
private static int getNumber() {
	int number = 10;
	return number;
}
```

`number` belongs to `getNumber()`'s stack frame. The variable disappears when the method returns, but the value `10` is sent back to the caller.

### Mistake 4: Assuming Java immediately deletes objects

When no references point to an object, it becomes eligible for garbage collection. Java decides when garbage collection runs; the object is not necessarily deleted immediately.

## 9. Summary

- A method call creates a stack frame.
- A method call can call another method by creating another frame above it.
- `return` sends a value or object reference back to the caller.
- `new` creates objects and arrays in the heap.
- A local object variable usually stores a heap reference in the stack frame.
- Stack frames are removed when methods return.
- Heap objects are removed later when they are no longer reachable and garbage collection reclaims them.

## 10. Basic mental model: values and references

Java is always **pass-by-value**. When the value is an object, the value being copied is
the reference, not the object itself.

```java
Student first = new Student("Asha", 20);
Student second = first;       // copies the reference
second.name = "Mina";

System.out.println(first.name); // Mina
```

`first` and `second` are two stack variables that point to the same heap object. Assigning
`second` does not copy the `Student`; changing the object through either reference changes
the same object. To create a separate object, construct one explicitly or provide a copy
constructor.

For a primitive parameter, the primitive value is copied:

```java
static void addTen(int value) {
	value += 10;
}

int score = 80;
addTen(score);
// score is still 80
```

## 11. Intermediate level: object lifetime and reachability

Garbage collection is based on **reachability**, not on the variable's scope alone. The
garbage collector starts from GC roots, such as:

- local references in active thread stack frames
- static fields
- running threads
- JNI references

It follows references from those roots. An object that cannot be reached is eligible for
collection.

```java
Student student = new Student("Asha", 20);
student = null;
```

After the assignment, the `Student` object has no reference from this code and may be
collected. `System.gc()` is only a request and must not be used as a correctness mechanism.
Use `try-with-resources` to release files, sockets, and database connections; garbage
collection does not reliably release those external resources.

An application can still have a **memory leak** in a garbage-collected language by
accidentally keeping references:

```java
static final List<Student> cache = new ArrayList<>();

void remember(Student student) {
	cache.add(student); // the cache keeps every Student reachable
}
```

Use bounded caches, expiration, and explicit removal when data should no longer be kept.

## 12. Advanced JVM memory areas

The simple stack/heap model is useful, but a JVM process has more memory areas:

| Area | Purpose |
|---|---|
| Java thread stack | Frames, parameters, local variables, and operand-stack data for one thread |
| Heap | Objects and arrays managed by the garbage collector |
| Metaspace | Class metadata; native memory rather than the Java heap |
| Code cache | JIT-compiled machine code |
| Direct/native memory | Buffers and libraries allocated outside the Java heap |

The exact layout depends on the JVM implementation and version. The Java language
specification does not require every object or primitive to be physically stored in one
particular place. For example, a JIT compiler may use **escape analysis** to keep a
short-lived object on the stack or remove an allocation entirely when it cannot escape a
method. Therefore, use the stack/heap model to understand ownership and lifetime, not as
a promise about every machine address.

`static` fields are associated with a class, not with one method call. A static field can
keep a heap object reachable for as long as its class remains loaded:

```java
class Configuration {
	static final Map<String, String> VALUES = new HashMap<>();
}
```

## 13. Garbage collection and heap generations

Most objects are short-lived. Modern collectors commonly organize the heap into
generational regions:

1. New objects begin in a young area.
2. Frequent young collections reclaim objects that die quickly.
3. Objects that survive collections may be promoted or moved to an older area.
4. Older objects are collected less frequently, depending on the collector.

The exact names and behavior differ between collectors such as G1, Parallel GC, and ZGC.
Garbage collection pauses are affected by allocation rate, live data, heap size, object
graph shape, and collector configuration. A larger heap is not automatically faster:
it can reduce collection frequency but may increase memory use or collection work.

Useful diagnostic options vary by JDK, but common starting points include:

```bash
java -Xms256m -Xmx1g -Xlog:gc* MyApplication
```

- `-Xms` sets the initial heap size.
- `-Xmx` sets the maximum Java heap size.
- `-Xlog:gc*` enables unified GC logging on modern JDKs.

Tune only after measuring. Do not select a collector or heap size solely from a
stack-versus-heap diagram.

## 14. Common memory failures

### `StackOverflowError`

Usually caused by unbounded recursion or very deep call nesting:

```java
static void recurse() {
	recurse();
}
```

Each call needs another stack frame. Fix the termination condition or replace recursion
with an iterative algorithm where appropriate.

### `OutOfMemoryError: Java heap space`

The JVM cannot allocate an object in the Java heap. Common causes include an unbounded
collection, loading too much data at once, or a cache without an eviction policy.

### `OutOfMemoryError: Metaspace`

Class metadata cannot be allocated. This can occur with excessive dynamic class
generation or class-loader leaks.

### Native/direct-memory exhaustion

Direct buffers and native libraries can consume process memory outside `-Xmx`. Always
consider total process memory, not only the Java heap, when deploying an application.

## 15. Real-world example: processing an online order

Imagine an e-commerce API handling this request:

```java
Order order = orderService.createOrder(customerId, cartItems);
```

During the request:

```text
THREAD STACK                                  HEAP
------------                                  ----
createOrder() frame                           Order object
customerId = 42                               customerId = 42
cartItems -> [references]                     items -> [OrderItem objects]
order -> -----------------------------------> status = CREATED
                                              total = 95.00
```

1. The web-server thread receives the request and gets a stack frame for
   `createOrder()`.
2. `customerId`, method parameters, and local references are stored in that frame.
3. The order, its item list, and item objects are created in the heap.
4. Helper calls such as `calculateTotal()` add temporary stack frames.
5. When the method returns, its stack frame is removed and the returned `Order` reference
   is handed to the caller.
6. If the application stores the order in a repository, cache, or response object, it
   remains reachable. Otherwise, after the response is complete and no other references
   remain, it becomes eligible for garbage collection.

This distinction explains two production problems:

- A large order list increases heap pressure because many objects remain reachable.
- A request that recursively calls itself can exhaust the thread stack even if the heap
  has plenty of free space.

For a real service, avoid keeping request objects in static collections, paginate large
database results, close external resources, and measure allocation and GC behavior under
representative load.

## 16. How to investigate memory issues

When diagnosing a problem, identify which area is exhausted instead of immediately
increasing `-Xmx`:

1. Check whether the error is stack, heap, metaspace, or native-memory related.
2. Inspect GC logs, heap usage, thread counts, and allocation rate.
3. Capture a heap dump for a suspected heap leak and inspect the largest retained
   objects.
4. Look for long-lived roots such as static fields, caches, listeners, and thread locals.
5. For stack problems, inspect the stack trace for recursion or unexpectedly deep calls.
6. Re-test after changing one factor at a time.

Tools such as Java Flight Recorder, `jcmd`, VisualVM, and Eclipse MAT can help, but
production diagnostics should follow the application's security and data-handling rules.

## 17. Quick interview and review checklist

- Is this a primitive value or an object reference?
- Which method frame owns this local variable?
- What references keep this object reachable?
- Could a static field, cache, listener, or `ThreadLocal` retain it?
- Is recursion bounded?
- Are external resources closed independently of garbage collection?
- Is the observed problem heap, stack, metaspace, or native memory?
- Have GC and allocation changes been measured rather than guessed?
